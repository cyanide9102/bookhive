package com.cyanide9102.catalogservice.context;

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
}
