package com.dyndyn.model;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import java.io.Serializable;

/**
 * Created by dyndyn on 25.06.2017.
 */

@Entity
@Table(name="ratings_lots")
public class Rating implements Serializable {

    @EmbeddedId
    private RatingId id;

    @Column
    @Min(value = 0, message = "error.rating.min")
    @Max(value = 5, message = "error.rating.max")
    private double rating;

    public Rating(){}

    public double getRating() {
        return rating;
    }

    public void setRating(double rating) {
        this.rating = rating;
    }

    public RatingId getId() {
        return id;
    }

    public void setId(RatingId id) {
        this.id = id;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("Rating{");
        sb.append("id=").append(id);
        sb.append(", rating=").append(rating);
        sb.append('}');
        return sb.toString();
    }
}

