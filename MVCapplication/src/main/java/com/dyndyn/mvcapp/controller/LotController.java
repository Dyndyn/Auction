package com.dyndyn.mvcapp.controller;

import com.dyndyn.model.Category;
import com.dyndyn.model.Comment;
import com.dyndyn.model.Image;
import com.dyndyn.model.Lot;
import com.dyndyn.model.Rating;
import com.dyndyn.mvcapp.service.CategoryService;
import com.dyndyn.mvcapp.service.CommentService;
import com.dyndyn.mvcapp.service.LotService;
import com.dyndyn.mvcapp.service.UserService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.beans.propertyeditors.CustomCollectionEditor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.io.IOException;
import java.util.List;

/**
 * Created by dyndyn on 20.06.2017.
 */
@Controller
public class LotController {

    private static final Logger LOGGER = LogManager.getLogger(LotController.class);
    @Value("${application.rest.lot.getById}")
    private String restLotByIdUrl;
    @Value("${application.rest.lot.getByCategoryId}")
    private String restLotsByCategoryIdUrl;
    @Value("${application.rest.lot}")
    private String restLotUrl;

    private LotService lotService;
    private CategoryService categoryService;
    private UserService userService;
    private CommentService commentService;

    @Autowired
    public LotController(LotService lotService, CategoryService categoryService,
                         UserService userService, CommentService commentService) {
        this.lotService = lotService;
        this.categoryService = categoryService;
        this.userService = userService;
        this.commentService = commentService;
    }

    @GetMapping("/lot")
    public String getLotById(final Model model, @RequestParam int id) {
        Lot lot = lotService.getLotById(id);
        model.addAttribute("lot", lot);
        model.addAttribute("comments", commentService.getByLotId(id));
        model.addAttribute("comment", new Comment());
        model.addAttribute("rating", new Rating());
        List<Category> categories = categoryService.getCategories();
        model.addAttribute("categories", categories);
        return "lot";
    }

    @GetMapping("/category")
    public String getLotsByCategory(final Model model, @RequestParam int id) {
        List<Lot> lots = lotService.getLotsByCategoryId(id);
        model.addAttribute("lots", lots);
        List<Category> categories = categoryService.getCategories();
        model.addAttribute("categories", categories);
        return "lotsByCategory";
    }

    @GetMapping("/addLot")
    public String getAddLotPage(final Model model) {
        Lot lot = new Lot();
        lot.setImage(new Image());
        model.addAttribute("lot", lot);
        List<Category> categories = categoryService.getCategories();
        model.addAttribute("categories", categories);
        return "addLot";
    }

    @PostMapping("/addLot")
    public String addLot(@Valid @ModelAttribute("lot") Lot lot, final BindingResult result, final Model model) {
        if (result.hasErrors()) {
            model.addAttribute("lot", lot);
            List<Category> categories = categoryService.getCategories();
            model.addAttribute("categories", categories);
            return "addLot";
        }
        lotService.addLot(lot);
        return "redirect:/home";
    }

    @PostMapping("/addRating")
    @ResponseBody
    public String addRating(@Valid @ModelAttribute("rating") Rating rating, final BindingResult result,
                            final HttpServletResponse response) {
        if(userService.getCurrentUserFromSession() == null){
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            return "You must be authorized";
        }
        if (result.hasErrors()) {
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            StringBuilder message = new StringBuilder();
            int i = 0;
            for (FieldError error : result.getFieldErrors()) {
                if (i++ > 0) {
                    message.append(",\r\n");
                }
                message.append(error.getDefaultMessage());
            }
            return message.toString();
        }
        lotService.addRating(rating);
        response.setStatus(HttpServletResponse.SC_OK);
        return "";
    }

    @InitBinder("lot")
    public void customizeBinding(WebDataBinder binder) {

        binder.registerCustomEditor(List.class, "categories", new CustomCollectionEditor(List.class) {
            @Override
            protected Object convertElement(Object element) {
                ObjectMapper mapper = new ObjectMapper();
                if (element instanceof String) {
                    try {
                        return mapper.readValue((String) element, Category.class);
                    } catch (IOException e) {
                        LOGGER.error(e.getMessage(), e);
                    }
                }
                return null;
            }
        });
    }
}
