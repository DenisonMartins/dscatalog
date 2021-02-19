package com.michaelmartins.dscatalog.services;

import com.michaelmartins.dscatalog.domain.entities.User;
import com.michaelmartins.dscatalog.dto.RoleDTO;
import com.michaelmartins.dscatalog.dto.UserDTO;
import com.michaelmartins.dscatalog.exceptions.DatabaseException;
import com.michaelmartins.dscatalog.exceptions.ResourceEntityNotFoundException;
import com.michaelmartins.dscatalog.repositories.RoleRepository;
import com.michaelmartins.dscatalog.repositories.UserRepository;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityNotFoundException;

import static java.lang.String.format;

@Service
public class UserService implements UserDetailsService {

    private final UserRepository repository;
    private final RoleRepository roleRepository;
    private final BCryptPasswordEncoder passwordEncoder;

    public UserService(UserRepository repository,
                       RoleRepository roleRepository,
                       BCryptPasswordEncoder passwordEncoder) {
        this.repository = repository;
        this.roleRepository = roleRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Transactional(readOnly = true)
    public Page<UserDTO> findAllPaged(PageRequest pageRequest) {
        return repository.findAll(pageRequest).map(UserDTO::new);
    }

    @Transactional(readOnly = true)
    public UserDTO findById(Long id) {
        return repository.findById(id)
                .map(UserDTO::new)
                .orElseThrow(() -> new ResourceEntityNotFoundException(format("Produto de id '%s' não existe.", id)));
    }

    @Transactional
    public UserDTO create(UserDTO dto) {
        User user = new User(dto);
        adicionarRoles(user, dto);
        user.setPassword(passwordEncoder.encode(dto.getPassword()));
        return new UserDTO(repository.save(user));
    }

    @Transactional
    public UserDTO update(Long id, UserDTO dto) {
        try {
            User user = new User(dto);
            user.setId(repository.getOne(id).getId());
            adicionarRoles(user, dto);
            return new UserDTO(repository.save(user));
        } catch (EntityNotFoundException e) {
            throw new ResourceEntityNotFoundException(format("Produto de id '%s' não existe.", id));
        }
    }

    public void delete(Long id) {
        try {
            repository.deleteById(id);
        } catch (EmptyResultDataAccessException emptyResultDataAccessException) {
            throw new ResourceEntityNotFoundException(format("Produto de id '%s' não existe.", id));
        } catch (DataIntegrityViolationException dataIntegrityViolationException) {
            throw new DatabaseException("Database Integrity");
        }
    }

    private void adicionarRoles(User user, UserDTO dto) {
        for (RoleDTO roleDtos : dto.getRoles()) {
            user.adicionar(roleRepository.getOne(roleDtos.getId()));
        }
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return repository.findByEmail(username).orElseThrow(() -> new UsernameNotFoundException("Email not found"));
    }
}
