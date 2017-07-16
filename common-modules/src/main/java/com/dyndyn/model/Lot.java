package com.dyndyn.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotBlank;

import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.validation.constraints.Min;
import java.io.Serializable;
import java.sql.Timestamp;
import java.util.List;

/**
 * Created by dyndyn on 13.06.2017.
 */
@Entity
@NamedQueries({
        @NamedQuery(name = "lot.getAll",
                query = "SELECT l, count(r.id.userId), avg(r.rating) FROM Lot l LEFT JOIN Rating r ON l.id = r.id.lotId GROUP BY l"),
        @NamedQuery(name = "lot.getByUserId",
                query = "SELECT l, count(r.id.userId), avg(r.rating) FROM Lot l LEFT JOIN Rating r ON l.id = r.id.lotId WHERE l.user.id = :userid GROUP BY l"),
        @NamedQuery(name = "lot.getByCategoryId",
                query = "SELECT l, count(r.id.userId), avg(r.rating) FROM Lot l LEFT JOIN Rating r ON l.id = r.id.lotId LEFT JOIN l.categories c where c.id = :categoryid GROUP BY l"),
        @NamedQuery(name = "lot.getById",
                query = "SELECT l, count(r.id.userId), avg(r.rating) FROM Lot l LEFT JOIN Rating r ON l.id = r.id.lotId WHERE l.id = :id")
})
@Table(name = "lot")
public class Lot implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @NotBlank(message = "{error.lotName.notblank}")
    @Length(min = 3, max = 64, message = "{error.lotName.length}")
    @Basic(optional = false)
    @Column
    private String name;

    @Length(max = 255, message = "{error.lotDescription.length}")
    @Column
    private String description;

    @Column(name = "image_id")
    private Integer imageId;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @Column(name = "is_enabled", insertable = false)
    private boolean enabled;

    @Min(value = 0, message = "error.lotPrice.min")
    @Basic(optional = false)
    @Column
    private double price;


    @Min(value = 0, message = "error.lotDiff.min")
    @Basic(optional = false)
    @Column
    private double diff;

    @Transient
    private double rating;

    @Transient
    private long reviews;

    @Column(insertable = false, updatable = false)
    private Timestamp date;

    @ManyToMany(fetch = FetchType.EAGER, cascade = CascadeType.MERGE)
    @JoinTable(
            name = "categories_lots",
            joinColumns = @JoinColumn(name = "lot_id"),
            inverseJoinColumns = @JoinColumn(name = "category_id")
    )
    @JsonIgnoreProperties(value = "lots", allowSetters = true)
    private List<Category> categories;

    public Lot() {

    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
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

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public boolean isEnabled() {
        return enabled;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
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

    public double getRating() {
        return rating;
    }

    public void setRating(double rating) {
        this.rating = rating;
    }

    public long getReviews() {
        return reviews;
    }

    public void setReviews(long reviews) {
        this.reviews = reviews;
    }

    public Timestamp getDate() {
        return date;
    }

    public void setDate(Timestamp date) {
        this.date = date;
    }

    public List<Category> getCategories() {
        return categories;
    }

    public void setCategories(List<Category> categories) {
        this.categories = categories;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("Lot{");
        sb.append("id=").append(id);
        sb.append(", name='").append(name).append('\'');
        sb.append(", description='").append(description).append('\'');
        sb.append(", imageId=").append(imageId);
        sb.append(", user=").append(user);
        sb.append(", isEnabled=").append(enabled);
        sb.append(", price=").append(price);
        sb.append(", diff=").append(diff);
        sb.append(", rating=").append(rating);
        sb.append(", reviews=").append(reviews);
        sb.append(", date=").append(date);
        sb.append(", categories=").append(categories);
        sb.append('}');
        return sb.toString();
    }
}
