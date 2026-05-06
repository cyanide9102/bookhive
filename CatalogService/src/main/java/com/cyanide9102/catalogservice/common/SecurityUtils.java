package com.cyanide9102.catalogservice.common;

import com.cyanide9102.catalogservice.common.exception.UnauthorizedException;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class SecurityUtils {

    public void guardAgainstNonAdmin(List<String> roles) {

        if (roles == null || !roles.contains("ROLE_ADMIN")) {

            throw new UnauthorizedException("Administrator access required!");
        }
    }
}
