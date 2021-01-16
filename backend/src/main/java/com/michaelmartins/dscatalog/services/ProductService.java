package com.michaelmartins.dscatalog.services;

import com.michaelmartins.dscatalog.domain.entities.Product;
import com.michaelmartins.dscatalog.dto.ProductDTO;
import com.michaelmartins.dscatalog.exceptions.DatabaseException;
import com.michaelmartins.dscatalog.exceptions.ResourceEntityNotFoundException;
import com.michaelmartins.dscatalog.repositories.ProductRepository;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityNotFoundException;

import static java.lang.String.format;

@Service
public class ProductService {

    private final ProductRepository repository;

    public ProductService(ProductRepository repository) {
        this.repository = repository;
    }

    @Transactional(readOnly = true)
    public Page<ProductDTO> findAllPaged(PageRequest pageRequest) {
        return repository.findAll(pageRequest).map(ProductDTO::new);
    }

    @Transactional(readOnly = true)
    public ProductDTO findById(Long id) {
        return repository.findById(id)
                .map(product -> new ProductDTO(product, product.getCategories()))
                .orElseThrow(() -> new ResourceEntityNotFoundException(format("Categoria de id '%s' não existe.", id)));
    }

    @Transactional
    public ProductDTO create(ProductDTO dto) {
        Product product = new Product(dto);
        return new ProductDTO(repository.save(product));
    }

    @Transactional
    public ProductDTO update(Long id, ProductDTO dto) {
        try {
            Product product = repository.getOne(id);
            product.setName(dto.getName());
            return new ProductDTO(repository.save(product));
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
