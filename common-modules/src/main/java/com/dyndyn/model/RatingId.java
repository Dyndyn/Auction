package com.dyndyn.model;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.io.Serializable;

@Embeddable
public class RatingId implements Serializable {

    @Column(name = "lot_id")
    private int lotId;

    @Column(name = "user_id")
    private int userId;

    public RatingId(){}

    public int getLotId() {
        return lotId;
    }

    public void setLotId(int lotId) {
        this.lotId = lotId;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        RatingId ratingId = (RatingId) o;

        if (lotId != ratingId.lotId) return false;
        return userId == ratingId.userId;
    }

    @Override
    public int hashCode() {
        int result = lotId;
        result = 31 * result + userId;
        return result;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("RatingId{");
        sb.append("lotId=").append(lotId);
        sb.append(", userId=").append(userId);
        sb.append('}');
        return sb.toString();
    }
}
