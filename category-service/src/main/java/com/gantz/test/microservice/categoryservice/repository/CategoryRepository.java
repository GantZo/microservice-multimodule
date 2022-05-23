package com.gantz.test.microservice.categoryservice.repository;

import com.gantz.test.microservice.categoryservice.domain.Category;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CategoryRepository extends JpaRepository<Category, Integer> {
}
