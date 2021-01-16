package com.michaelmartins.dscatalog.services;

import com.michaelmartins.dscatalog.dto.CategoryDTO;
import com.michaelmartins.dscatalog.exceptions.ResourceEntityNotFoundException;
import com.michaelmartins.dscatalog.repositories.CategoryRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
}
