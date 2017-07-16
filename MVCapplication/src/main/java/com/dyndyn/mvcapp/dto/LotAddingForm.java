package com.dyndyn.mvcapp.dto;

import com.dyndyn.model.Category;
import com.dyndyn.model.Image;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotBlank;
import org.hibernate.validator.constraints.NotEmpty;

import javax.validation.constraints.Min;
import java.util.List;

/**
 * A class that holds fields required for adding lot
 */
public class LotAddingForm {

    @NotBlank(message = "{error.lotName.notblank}")
    @Length(min = 3, max = 64, message = "{error.lotName.length}")
    private String name;

    @Length(max = 255, message = "{error.lotDescription.length}")
    private String description;

    private Integer imageId;

    @Min(value = 0, message = "{error.lotPrice.min}")
    private double price;


    @Min(value = 0, message = "{error.lotDiff.min}")
    private double diff;

    @NotEmpty(message = "{error.category.notEmpty}")
    private List<Category> categories;

    private Image image;

    public LotAddingForm() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Integer getImageId() {
        return imageId;
    }

    public void setImageId(Integer imageId) {
        this.imageId = imageId;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public double getDiff() {
        return diff;
    }

    public void setDiff(double diff) {
        this.diff = diff;
    }

    public List<Category> getCategories() {
        return categories;
    }

    public void setCategories(List<Category> categories) {
        this.categories = categories;
    }

    public Image getImage() {
        return image;
    }

    public void setImage(Image image) {
        this.image = image;
    }
}
