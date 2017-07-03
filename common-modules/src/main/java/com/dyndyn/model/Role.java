package com.dyndyn.model;

import org.springframework.security.core.GrantedAuthority;
/**
 * Created by dyndyn on 13.06.2017.
 */
public enum Role implements GrantedAuthority{
    ROLE_USER("ROLE_USER"), ROLE_ADMIN("ROLE_ADMIN");

    private String authority;

    Role(String authority){
        this.authority = authority;
    }

    @Override
    public String getAuthority() {
        return null;
    }

    @Override
    public String toString() {
        return "Role{" +
                "authority='" + authority + '\'' +
                '}';
    }
}
