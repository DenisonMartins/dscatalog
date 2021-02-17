package com.michaelmartins.dscatalog.exceptions.validation;

import com.michaelmartins.dscatalog.domain.entities.User;
import com.michaelmartins.dscatalog.dto.UserDTO;
import com.michaelmartins.dscatalog.exceptions.FieldMessage;
import com.michaelmartins.dscatalog.repositories.UserRepository;
import org.springframework.web.servlet.HandlerMapping;

import javax.servlet.http.HttpServletRequest;
import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;

public class UserValidator implements ConstraintValidator<UserValid, UserDTO> {

    private final UserRepository repository;
    private final HttpServletRequest request;

    public UserValidator(UserRepository repository, HttpServletRequest request) {
        this.repository = repository;
        this.request = request;
    }

    @Override
    public void initialize(UserValid constraintAnnotation) {
    }

    @Override
    public boolean isValid(UserDTO dto, ConstraintValidatorContext context) {
        var uriParams = (Map<String, String>) request.getAttribute(HandlerMapping.URI_TEMPLATE_VARIABLES_ATTRIBUTE);

        if (uriParams.containsKey("id")) {
            dto.setId(Long.parseLong(uriParams.get("id")));
        }

        List<FieldMessage> erros = new ArrayList<>();

        verificaExistenciaDeEmail(dto, erros);

        erros.forEach(fieldMessage -> {
            context.disableDefaultConstraintViolation();
            context.buildConstraintViolationWithTemplate(fieldMessage.getMessage())
                   .addPropertyNode(fieldMessage.getFieldMessage())
                   .addConstraintViolation();
        });

        return erros.isEmpty();
    }

    private void verificaExistenciaDeEmail(UserDTO dto, List<FieldMessage> erros) {
        repository.findByEmail(dto.getEmail()).ifPresent(user -> verificaSeEhOMesmoUsuarioDaRequest(dto, erros, user));
    }

    private void verificaSeEhOMesmoUsuarioDaRequest(UserDTO dto, List<FieldMessage> erros, User user) {
        if (!Objects.equals(user.getId(), dto.getId())) {
            erros.add(new FieldMessage("email", "Email já existe."));
        }
    }
}
