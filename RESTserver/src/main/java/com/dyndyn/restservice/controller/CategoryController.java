package com.dyndyn.restservice.controller;

import com.dyndyn.model.Category;
import com.dyndyn.restservice.service.CategoryService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * The CategoryController class handles requests for category resources.
 *
 * @author Roman Dyndyn
 */
@RestController
public class CategoryController {

    private static final Logger LOGGER = LogManager.getLogger(CategoryController.class);
    private CategoryService categoryService;

    @Autowired
    public CategoryController(CategoryService categoryService) {
        this.categoryService = categoryService;
    }

    @GetMapping(value = "/api/category/{categoryId}")
    public Category getById(@PathVariable final int categoryId){
        Category category = categoryService.getById(categoryId);
        LOGGER.trace("category with id = {} was found", categoryId);
        return category;
    }

    @GetMapping("/category")
    public List<Category> getAll(){
        List<Category> categories = categoryService.getAll();
        LOGGER.trace("All categories were found");
        return categories;
    }

    @PostMapping("/api/category")
    public void insert(@RequestBody final Category category){
        categoryService.add(category);
        LOGGER.trace("category with name = {} was added", category.getName());
    }

    @PutMapping("/api/category")
    public void update(@RequestBody final Category category){
        categoryService.update(category);
        LOGGER.trace("category with id = {} was updated", category.getId());
    }

    @DeleteMapping("/api/category/{categoryId}")
    public void delete(@PathVariable int categoryId){
        categoryService.remove(categoryId);
        LOGGER.trace("category with id = {} was deleted", categoryId);
    }
}
