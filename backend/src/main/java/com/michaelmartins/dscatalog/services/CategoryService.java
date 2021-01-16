package com.michaelmartins.dscatalog.services;

import com.michaelmartins.dscatalog.domain.entities.Category;
import com.michaelmartins.dscatalog.dto.CategoryDTO;
import com.michaelmartins.dscatalog.exceptions.DatabaseException;
import com.michaelmartins.dscatalog.exceptions.ResourceEntityNotFoundException;
import com.michaelmartins.dscatalog.repositories.CategoryRepository;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityNotFoundException;
import java.util.List;
import java.util.stream.Collectors;

import static java.lang.String.format;

@Service
public class CategoryService {

    private final CategoryRepository repository;

    public CategoryService(CategoryRepository repository) {
        this.repository = repository;
    }

    @Transactional(readOnly = true)
    public List<CategoryDTO> findAll() {
        return repository.findAll().stream().map(CategoryDTO::new).collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public CategoryDTO findById(Long id) {
        return repository.findById(id)
                .map(CategoryDTO::new)
                .orElseThrow(() -> new ResourceEntityNotFoundException(format("Categoria de id '%s' não existe.", id)));
    }

    @Transactional
    public CategoryDTO create(CategoryDTO dto) {
        Category category = new Category(dto);
        return new CategoryDTO(repository.save(category));
    }

    @Transactional
    public CategoryDTO update(Long id, CategoryDTO dto) {
        try {
            Category category = repository.getOne(id);
            category.setName(dto.getName());
            return new CategoryDTO(repository.save(category));
        } catch (EntityNotFoundException e) {
            throw new ResourceEntityNotFoundException(format("Categoria de id '%s' não existe.", id));
        }
    }

    public void delete(Long id) {
        try {
            repository.deleteById(id);
        } catch (EmptyResultDataAccessException emptyResultDataAccessException) {
            throw new ResourceEntityNotFoundException(format("Categoria de id '%s' não existe.", id));
        } catch (DataIntegrityViolationException dataIntegrityViolationException) {
            throw new DatabaseException("Database Integrity");
        }
    }
}
