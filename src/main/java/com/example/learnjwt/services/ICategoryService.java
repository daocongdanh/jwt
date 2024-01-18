package com.example.learnjwt.services;

import com.example.learnjwt.models.Category;

import java.util.List;

public interface ICategoryService {
    Category createCategory(Category category);
    Category getCategoryById(Long id);
    List<Category> getAllCategories();
    Category updateCategory(Category category);
    void deleteCategoryById(Long id);
}
