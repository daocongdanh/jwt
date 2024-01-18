package com.example.learnjwt.services;

import com.example.learnjwt.exceptions.DataNotFoundException;
import com.example.learnjwt.models.Category;
import com.example.learnjwt.repositories.CategoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CategoryService implements ICategoryService{
    private final CategoryRepository categoryRepository;
    @Override
    public Category createCategory(Category category) {
        return categoryRepository.save(category);
    }

    @Override
    public Category getCategoryById(Long id) {
        return categoryRepository.findById(id).orElseThrow(() -> new DataNotFoundException("Category not found"));
    }

    @Override
    public List<Category> getAllCategories() {
        return categoryRepository.findAll();
    }

    @Override
    public Category updateCategory( Category category) {;
        return categoryRepository.save(category);
    }

    @Override
    public void deleteCategoryById(Long id) {
        if(categoryRepository.findById(id).isPresent()){
            categoryRepository.deleteById(id);
        }
        else throw new DataNotFoundException("Category not found");
    }
}
