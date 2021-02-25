package com.michaelmartins.dscatalog.services;

import com.michaelmartins.dscatalog.domain.entities.Category;
import com.michaelmartins.dscatalog.domain.entities.Product;
import com.michaelmartins.dscatalog.dto.CategoryDTO;
import com.michaelmartins.dscatalog.dto.ProductDTO;
import com.michaelmartins.dscatalog.exceptions.DatabaseException;
import com.michaelmartins.dscatalog.exceptions.ResourceEntityNotFoundException;
import com.michaelmartins.dscatalog.repositories.CategoryRepository;
import com.michaelmartins.dscatalog.repositories.ProductRepository;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityNotFoundException;
import java.util.List;

import static java.lang.String.format;
import static java.util.Collections.singletonList;

@Service
public class ProductService {

    private final ProductRepository repository;
    private final CategoryRepository categoryRepository;

    public ProductService(ProductRepository repository, CategoryRepository categoryRepository) {
        this.repository = repository;
        this.categoryRepository = categoryRepository;
    }

    @Transactional(readOnly = true)
    public Page<ProductDTO> findAllPaged(Long idCategory, String name, PageRequest pageRequest) {
        List<Category> categories = idCategory == 0 ? null : singletonList(categoryRepository.getOne(idCategory));
        Page<Product> products = repository.find(categories, name, pageRequest);
        return products.map(ProductDTO::new);
    }

    @Transactional(readOnly = true)
    public ProductDTO findById(Long id) {
        return repository.findById(id)
                .map(product -> new ProductDTO(product, product.getCategories()))
                .orElseThrow(() -> new ResourceEntityNotFoundException(format("Produto de id '%s' não existe.", id)));
    }

    @Transactional
    public ProductDTO create(ProductDTO dto) {
        Product product = new Product(dto);
        adicionarCategorias(product, dto);
        return new ProductDTO(repository.save(product));
    }

    @Transactional
    public ProductDTO update(Long id, ProductDTO dto) {
        try {
            Product product = new Product(dto);
            product.setId(repository.getOne(id).getId());
            adicionarCategorias(product, dto);
            return new ProductDTO(repository.save(product));
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

    private void adicionarCategorias(Product product, ProductDTO dto) {
        for (CategoryDTO categoryDTO : dto.getCategories()) {
            product.adicionar(categoryRepository.getOne(categoryDTO.getId()));
        }
    }
}
