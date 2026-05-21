package com.cyanide9102.orderservice.client.config;

import com.cyanide9102.common.context.RequestContext;
import feign.RequestInterceptor;
import feign.RequestTemplate;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class FeignClientInterceptor implements RequestInterceptor {

    private final RequestContext requestContext;

    @Override
    public void apply(RequestTemplate template) {

        String userId = requestContext.userId();
        if (userId != null) {
            template.header("X-User-Id", userId);
        }

        List<String> roles = requestContext.roles();
        if (roles != null && !roles.isEmpty()) {
            template.header("X-User-Roles", String.join(",", roles));
        }
    }
}
