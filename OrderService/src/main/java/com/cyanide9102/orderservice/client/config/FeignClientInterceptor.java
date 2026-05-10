package com.cyanide9102.orderservice.client.config;

import com.cyanide9102.common.context.UserContext;
import feign.RequestInterceptor;
import feign.RequestTemplate;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class FeignClientInterceptor implements RequestInterceptor {

    @Override
    public void apply(RequestTemplate template) {

        String userId = UserContext.getUserId();
        if (userId != null) {
            template.header("X-User-Id", userId);
        }

        List<String> roles = UserContext.getUserRoles();
        if (roles != null && !roles.isEmpty()) {
            template.header("X-User-Roles", String.join(",", roles));
        }
    }
}
