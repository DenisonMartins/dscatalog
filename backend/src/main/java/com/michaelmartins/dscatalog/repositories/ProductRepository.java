package com.michaelmartins.dscatalog.repositories;

import com.michaelmartins.dscatalog.domain.entities.Product;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductRepository extends JpaRepository<Product, Long> {
}
