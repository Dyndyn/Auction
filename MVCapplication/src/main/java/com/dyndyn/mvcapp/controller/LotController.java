package com.dyndyn.mvcapp.controller;

import com.dyndyn.model.Category;
import com.dyndyn.model.Comment;
import com.dyndyn.model.Image;
import com.dyndyn.model.Lot;
import com.dyndyn.model.Rating;
import com.dyndyn.model.User;
import com.dyndyn.mvcapp.dto.LotAddingForm;
import com.dyndyn.mvcapp.service.CategoryService;
import com.dyndyn.mvcapp.service.CommentService;
import com.dyndyn.mvcapp.service.ImageService;
import com.dyndyn.mvcapp.service.LotService;
import com.dyndyn.mvcapp.service.UserService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.beans.propertyeditors.CustomCollectionEditor;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessageHeaderAccessor;
import org.springframework.messaging.simp.SimpMessagingTemplate;
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
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
    @Value("${application.broadcast.lot}")
    private String broadcastLot;

    private LotService lotService;
    private CategoryService categoryService;
    private UserService userService;
    private CommentService commentService;
    private SimpMessagingTemplate messaging;

    @Autowired
    public LotController(LotService lotService, CategoryService categoryService,
                         UserService userService, CommentService commentService,
                         SimpMessagingTemplate simpMessagingTemplate) {
        this.lotService = lotService;
        this.categoryService = categoryService;
        this.userService = userService;
        this.commentService = commentService;
        this.messaging = simpMessagingTemplate;
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

    @GetMapping("/myLots")
    public String getLotsByUser(final Model model) {
        List<Lot> lots = lotService.getLotsByUserId();
        model.addAttribute("lots", lots);
        model.addAttribute("enable", new Lot());
        return "userLots";
    }

    @GetMapping("/addLot")
    public String getAddLotPage(final Model model) {
        model.addAttribute("lotForm", new LotAddingForm());
        List<Category> categories = categoryService.getCategories();
        model.addAttribute("categories", categories);
        return "addLot";
    }

    @PostMapping("/lot/enable")
    public void addLot(@ModelAttribute("enable") Lot lot, final HttpServletResponse response) {
        lotService.enable(lot);
        response.setStatus(HttpServletResponse.SC_OK);
    }

    @PostMapping("/addLot")
    public String addLot(@Valid @ModelAttribute("lotForm") LotAddingForm lot, final BindingResult result, final Model model) {
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
        if (userService.getCurrentUserFromSession() == null) {
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

    @MessageMapping("/lot")
    public void handleTyping(String body, @Header("lotId") Integer lotId, SimpMessageHeaderAccessor headerAccessor) {
        if ("typing".equals(body)) {
            Map<String, Object> attrs = headerAccessor.getSessionAttributes();
            User user = (User) attrs.get("user");
            String msg = user.getName() + " is typing";
            Map<String, Object> headers = new HashMap<>();
            headers.put("userId", user.getId());
            messaging.convertAndSend(broadcastLot + lotId, msg, headers);
        }
    }

    @InitBinder("lotForm")
    public void customizeBinding(WebDataBinder binder) {

        binder.registerCustomEditor(List.class, "categories", new CustomCollectionEditor(List.class) {
            @Override
            protected Object convertElement(Object element) {
                if (element != null) {
                    Integer categoryId = Integer.parseInt(element.toString());
                    return new Category(categoryId);
                }
                return null;
            }
        });
    }
}
