package com.example.learnjwt.repositories;

import com.example.learnjwt.models.Category;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CategoryRepository extends JpaRepository<Category, Long> {
}
