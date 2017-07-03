package com.dyndyn.mvcapp.service;

import com.dyndyn.model.Category;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpSession;
import java.util.List;

/**
 * Created by dyndyn on 21.06.2017.
 */
@Service
public class CategoryService {

    private static final Logger LOGGER = LogManager.getLogger(CategoryService.class);
    private final String sessionAttribute= "categories";
    @Value("${application.rest.category}")
    private String restCategoryUrl;

    @Value("${application.rest.secured.category}")
    private String restSecuredCategoryUrl;

    private RestTemplate restTemplate;

    @Autowired
    public CategoryService(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    public List<Category> getCategories(){
        ServletRequestAttributes attr = (ServletRequestAttributes) RequestContextHolder.currentRequestAttributes();
        HttpSession session = attr.getRequest().getSession();
        List<Category> categories = (List<Category>) session.getAttribute(sessionAttribute);
        if (categories == null) {
            categories = restTemplate.exchange(restCategoryUrl, HttpMethod.GET,
                    null, new ParameterizedTypeReference<List<Category>>() {
                    }).getBody();
            session.setAttribute(sessionAttribute, categories);
        }
        return categories;
    }

}
