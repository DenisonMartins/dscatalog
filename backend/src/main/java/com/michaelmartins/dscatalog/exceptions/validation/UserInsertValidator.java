package com.michaelmartins.dscatalog.exceptions.validation;

import com.michaelmartins.dscatalog.domain.entities.User;
import com.michaelmartins.dscatalog.dto.UserDTO;
import com.michaelmartins.dscatalog.exceptions.FieldMessage;
import com.michaelmartins.dscatalog.repositories.UserRepository;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class UserInsertValidator implements ConstraintValidator<UserInsertValid, UserDTO> {

    private final UserRepository repository;

    public UserInsertValidator(UserRepository repository) {
        this.repository = repository;
    }

    @Override
    public void initialize(UserInsertValid constraintAnnotation) {
    }

    @Override
    public boolean isValid(UserDTO dto, ConstraintValidatorContext context) {
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
        Optional<User> user = repository.findByEmail(dto.getEmail());
        if (user.isPresent()) {
            erros.add(new FieldMessage("email", "Email já existe."));
        }
    }
}
