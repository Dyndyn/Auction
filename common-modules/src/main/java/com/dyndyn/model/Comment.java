package com.dyndyn.model;

import org.hibernate.validator.constraints.Length;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import java.io.Serializable;
import java.sql.Timestamp;

/**
 * Created by dyndyn on 13.06.2017.
 */
@Entity
@NamedQueries({
        @NamedQuery(name="comment.getByLotId",
                query="SELECT c FROM Comment c where c.lot.id = :lotid ORDER BY c.date DESC")
})
@Table(name = "comment")
public class Comment implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Basic(optional = false)
    @Column
    @Length(max = 255, message = "{error.comment.length}")
    private String data;

    @Column(insertable = false, updatable = false)
    private Timestamp date;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "lot_id", nullable = false)
    private Lot lot;

    public Comment(){}

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Lot getLot() {
        return lot;
    }

    public void setLot(Lot lot) {
        this.lot = lot;
    }

    public Timestamp getDate() {
        return date;
    }

    public void setDate(Timestamp date) {
        this.date = date;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("Comment{");
        sb.append("id=").append(id);
        sb.append(", data='").append(data).append('\'');
        sb.append(", date=").append(date);
        sb.append(", user=").append(user);
        sb.append(", lot=").append(lot);
        sb.append('}');
        return sb.toString();
    }
}
