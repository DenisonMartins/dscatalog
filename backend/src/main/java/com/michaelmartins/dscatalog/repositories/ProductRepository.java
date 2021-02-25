package com.michaelmartins.dscatalog.repositories;

import com.michaelmartins.dscatalog.domain.entities.Category;
import com.michaelmartins.dscatalog.domain.entities.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ProductRepository extends JpaRepository<Product, Long> {

    @Query("select distinct prod from Product prod" +
            " inner join prod.categories cats where (coalesce(:categories) is null or cats in :categories)" +
            " and (:name = '' or lower(prod.name) like lower(concat('%', :name, '%')))")
    Page<Product> find(List<Category> categories, String name, Pageable pageable);
}
