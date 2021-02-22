package com.michaelmartins.dscatalog.config;

import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered;
import org.springframework.core.env.Environment;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;
import org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configurers.ResourceServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.token.store.JwtTokenStore;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;

import java.util.Collections;

import static java.util.Arrays.asList;
import static org.springframework.http.HttpMethod.*;

@Configuration
@EnableResourceServer
public class ResourceServerConfiguration extends ResourceServerConfigurerAdapter {

    public static final String PROFILE_TEST = "test";
    private static final String[] PUBLIC = { "/oauth/login", "/h2-console/**" };
    private static final String[] OPERATOR_OR_ADMIN = { "/products/**", "/categories/**" };
    private static final String[] ADMIN = { "/users/**" };

    private final JwtTokenStore tokenStore;
    private final Environment env;

    public ResourceServerConfiguration(JwtTokenStore tokenStore,
                                       Environment env) {
        this.tokenStore = tokenStore;
        this.env = env;
    }

    @Override
    public void configure(ResourceServerSecurityConfigurer resources) {
        resources.tokenStore(tokenStore);
    }

    @Override
    public void configure(HttpSecurity http) throws Exception {

        verificaSeDeveLiberarBancoDadosLocalH2ParaProfileTest(http);

        http.authorizeRequests()
                .antMatchers(PUBLIC).permitAll()
                .antMatchers(GET, OPERATOR_OR_ADMIN).permitAll()
                .antMatchers(OPERATOR_OR_ADMIN).hasAnyRole("OPERATOR", "ADMIN")
                .antMatchers(ADMIN).hasRole("ADMIN")
                .anyRequest().authenticated();

        http.cors().configurationSource(corsConfigurationSource());
    }

    private void verificaSeDeveLiberarBancoDadosLocalH2ParaProfileTest(HttpSecurity http) throws Exception {
        if (asList(env.getActiveProfiles()).contains(PROFILE_TEST)) {
            http.headers().frameOptions().disable();
        }
    }

    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration corsConfiguration = new CorsConfiguration();
        corsConfiguration.setAllowedOriginPatterns(Collections.singletonList("*"));
        corsConfiguration.setAllowedMethods(asList(GET.name(), POST.name(), PUT.name(), PATCH.name(), DELETE.name()));
        corsConfiguration.setAllowCredentials(true);
        corsConfiguration.setAllowedHeaders(asList("Authorization","Content-Type"));

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", corsConfiguration);
        return source;
    }

    @Bean
    public FilterRegistrationBean<CorsFilter> corsFilter() {
        FilterRegistrationBean<CorsFilter> corsFilterFilterRegistrationBean =
                new FilterRegistrationBean<>(new CorsFilter(corsConfigurationSource()));
        corsFilterFilterRegistrationBean.setOrder(Ordered.HIGHEST_PRECEDENCE);
        return corsFilterFilterRegistrationBean;
    }
}
