package com.dyndyn.model;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import java.io.Serializable;

/**
 * Created by dyndyn on 13.06.2017.
 */
@Entity
@NamedQueries({
        @NamedQuery(name = "user.getAll",
                query = "SELECT u FROM User u"),
        @NamedQuery(name = "user.getByEmail",
                query = "SELECT u FROM User u WHERE u.email = :email")
})
@Table(name = "user")
public class User implements Serializable{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Basic(optional = false)
    @Column
    private String name;
    @Basic(optional = false)
    @Column
    private String email;
    @Basic(optional = false)
    @Column
    private String password;
    @Basic(optional = false)
    @Column(name="is_enabled")
    private boolean isEnabled;
    @Basic(optional = false)
    @Enumerated(EnumType.STRING)
    @Column
    private Role role;
    @Basic(optional = false)
    @Column
    private String address;

    public User(){

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

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public boolean isEnabled() {
        return isEnabled;
    }

    public void setEnabled(boolean enabled) {
        isEnabled = enabled;
    }

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", email='" + email + '\'' +
                ", password='" + password + '\'' +
                ", isEnabled=" + isEnabled +
                ", role=" + role +
                ", address='" + address + '\'' +
                '}';
    }
}
