package com.example.lab04.service;

import com.example.lab04.model.Category;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class CategoryService {
    private final List<Category> categories;
    
    public CategoryService() {
        categories = new ArrayList<>();
        categories.add(new Category(1, "Điện thoại"));
        categories.add(new Category(2, "Laptop"));
    }
    
    public List<Category> getAll() {
        return categories;
    }
    
    public Category getById(Integer id) {
        return categories.stream()
                .filter(c -> c.getId().equals(id))
                .findFirst()
                .orElse(null);
    }
    
    public Category get(Integer id) {
        return getById(id);
    }
}
