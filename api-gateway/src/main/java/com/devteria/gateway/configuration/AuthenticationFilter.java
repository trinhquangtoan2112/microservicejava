package com.devteria.gateway.configuration;

import java.util.List;

import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.util.CollectionUtils;
import org.springframework.web.server.ServerWebExchange;

import com.devteria.gateway.dto.ApiResponse;
import com.devteria.gateway.dto.response.IntrospectResponse;
import com.devteria.gateway.service.IdentityService;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.experimental.NonFinal;
import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Mono;

@Configuration
@Slf4j
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class AuthenticationFilter implements GlobalFilter, Ordered {

    IdentityService identityService;

    @NonFinal
    String[] publicEndPoint = {"/identity/auth/**", "/identity/user/register"};

    // dat do uu tien cang nho thi cang uu tien
    @Override
    public int getOrder() {
        return -1;
    }

    private boolean isPublicEndPoint(ServerHttpRequest request) {
        for (String endPoint : publicEndPoint) {
            if (request.getURI().getPath().matches(endPoint)) {
                return true;
            }
        }
        return false;
    }

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        System.out.println("AuthenticationFilter: " + exchange.getRequest().getPath());
        //get token from
        List<String> authHeader = exchange.getRequest().getHeaders().get(HttpHeaders.AUTHORIZATION);
        if (isPublicEndPoint(exchange.getRequest())) {
            return chain.filter(exchange);
        }
        if (CollectionUtils.isEmpty(authHeader)) {
            return unauthenticatiMono(exchange.getResponse());
        }
        String token = authHeader.getFirst().replace("Bearer ", "");
        System.out.println("token: " + token);
        return identityService.introspect(token).flatMap(introspectResponse -> {

            if (introspectResponse.getResult().isValid()) {
                return chain.filter(exchange);
            } else {
                return unauthenticatiMono(exchange.getResponse());
            }
        }).onErrorResume(throwable -> unauthenticatiMono(exchange.getResponse()));
    }

    Mono<Void> unauthenticatiMono(ServerHttpResponse response) {
        response.setStatusCode(HttpStatus.UNAUTHORIZED);
        String body = "Unauthorized";
        return response.writeWith(Mono.just(response.bufferFactory().wrap(body.getBytes())));
    }
}
