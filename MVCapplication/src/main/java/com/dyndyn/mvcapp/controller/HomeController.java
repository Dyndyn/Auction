package com.dyndyn.mvcapp.controller;

import com.dyndyn.model.Category;
import com.dyndyn.model.Lot;
import com.dyndyn.mvcapp.service.CategoryService;
import com.dyndyn.mvcapp.service.LotService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.client.RestTemplate;

import java.util.List;

/**
 * Created by dyndyn on 19.06.2017.
 */
@Controller
@RequestMapping("/home")
public class HomeController {

    @Value("${application.rest.category}")
    private String restCategoryUrl;

    @Value("${application.rest.lot}")
    private String restLotsUrl;

    private CategoryService categoryService;
    private LotService lotService;

    @Autowired
    public HomeController(CategoryService categoryService, LotService lotService) {
        this.categoryService = categoryService;
        this.lotService = lotService;
    }

    @GetMapping
    public String getHomePage(final Model model) {
        List<Lot> lots = lotService.getAllLots();
        model.addAttribute("lots", lots);
        List<Category> categories = categoryService.getCategories();
        model.addAttribute("categories", categories);
        return "home";
    }
}
