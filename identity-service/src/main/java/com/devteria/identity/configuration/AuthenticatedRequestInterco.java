package com.devteria.identity.configuration;

import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import feign.RequestInterceptor;
import feign.RequestTemplate;
import io.micrometer.common.util.StringUtils;

@Component
public class AuthenticatedRequestInterco implements RequestInterceptor {

    @Override
    public void apply(RequestTemplate requestTemplate) {
        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        if (attributes != null) {

            String authHeader = attributes.getRequest().getHeader("Authorization");

            if (StringUtils.isNotEmpty(authHeader)) {
                requestTemplate.header("Authorization", authHeader);
            }
        }
    }
}
