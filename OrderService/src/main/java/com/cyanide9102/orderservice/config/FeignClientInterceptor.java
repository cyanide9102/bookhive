package com.cyanide9102.orderservice.config;

import com.cyanide9102.orderservice.context.UserContext;
import feign.RequestInterceptor;
import feign.RequestTemplate;
import org.springframework.stereotype.Component;

@Component
public class FeignClientInterceptor implements RequestInterceptor {

    @Override
    public void apply(RequestTemplate template) {

        String userId = UserContext.getUserId();
        if (userId != null) {
            template.header("X-User-Id", userId);
        }
    }
}
