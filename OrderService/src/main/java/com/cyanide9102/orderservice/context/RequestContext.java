package com.cyanide9102.orderservice.context;

import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class RequestContext {

    public String userId() {

        return UserContext.getUserId();
    }

    public List<String> roles() {

        return UserContext.getUserRoles();
    }

    public boolean isAdmin() {

        return roles() != null && roles().contains("ROLE_ADMIN");
    }
}
