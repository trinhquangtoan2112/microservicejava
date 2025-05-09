package com.devteria.gateway.service;

import org.springframework.stereotype.Service;

import com.devteria.gateway.dto.ApiResponse;
import com.devteria.gateway.dto.request.IntrospectRequest;
import com.devteria.gateway.dto.response.IntrospectResponse;
import com.devteria.gateway.repository.httpclient.IdentityServiceRepository;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Mono;

@Service
@RequiredArgsConstructor
@Slf4j(topic = "IdentityService")
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class IdentityService {

    IdentityServiceRepository identityServiceRepository;

    public Mono<ApiResponse<IntrospectResponse>> introspect(String token) {
        var result = identityServiceRepository.introspect(IntrospectRequest.builder().token(token).build());
        return result;

    }
}
