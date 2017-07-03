package com.dyndyn.restservice.service;

import com.dyndyn.model.Category;
import com.dyndyn.restservice.repository.CategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * The CategoryService class is used to hold business
 * logic for working with the CategoryRepository.
 *
 * @author Roman Dyndyn
 */
@Service
public class CategoryService {

    private CategoryRepository categoryRepository;

    @Autowired

    public CategoryService(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }

    public void add(Category category) {
        categoryRepository.add(category);
    }

    public Category getById(int id) {
        return categoryRepository.getById(id);
    }

    public List<Category> getAll() {
        return categoryRepository.getAll();
    }

    public void update(Category category) {
        categoryRepository.update(category);
    }

    public void remove(int categoryId) {
        categoryRepository.remove(categoryId);
    }
}
