package com.mxs.common.util;

import org.springframework.security.core.GrantedAuthority;

import java.io.Serializable;

public class RoleContext implements GrantedAuthority, Serializable {



    private String authority;

    @Override
    public String getAuthority() {
        return authority;
    }

    public void setAuthority(String authority) {
        this.authority = authority;
    }
}
