package com.saki.gestion_stock.repository;

import java.util.Optional;

import com.saki.gestion_stock.model.Category;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CategoryRepository extends JpaRepository<Category, Integer> {

    Optional<Category> findCategoryByCode(String code);

}
