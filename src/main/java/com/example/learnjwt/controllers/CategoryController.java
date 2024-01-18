package com.example.learnjwt.controllers;

import com.example.learnjwt.models.Category;
import com.example.learnjwt.services.ICategoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/categories")
@RequiredArgsConstructor
public class CategoryController {
    private final ICategoryService categoryService;

    @GetMapping("")
    public ResponseEntity<List<Category>> getAllCategories(){
        return ResponseEntity.ok(categoryService.getAllCategories());
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getCategoryById(@PathVariable("id") Long id){
        try {
            return ResponseEntity.ok(categoryService.getCategoryById(id));
        }
        catch (Exception e){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

    @PostMapping ("")
    public ResponseEntity<Category> categoryCategory(@RequestBody Category category){
        return ResponseEntity.ok(categoryService.createCategory(category));
    }
    @PutMapping("")
    public ResponseEntity<Category> updateCategory(@RequestBody Category category){
        return ResponseEntity.ok(categoryService.updateCategory(category));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deteleCategoryById(@PathVariable("id") Long id){
        try{
            categoryService.deleteCategoryById(id);
            return ResponseEntity.ok("Delete successfully");
        }
        catch (Exception e){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }
}
