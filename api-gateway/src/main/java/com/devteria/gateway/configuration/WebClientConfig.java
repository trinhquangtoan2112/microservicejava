package com.devteria.gateway.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.support.WebClientAdapter;
import org.springframework.web.service.invoker.HttpServiceProxyFactory;

import com.devteria.gateway.repository.httpclient.IdentityServiceRepository;

@Configuration

public class WebClientConfig {

    @Bean
    public WebClient webclient() {
        return WebClient.builder()
                .baseUrl("http://localhost:8080/identity")
                .build();
    }

    @Bean
    public IdentityServiceRepository identityServiceRepository(WebClient webclient) {
        HttpServiceProxyFactory httpServiceProxyFactory = HttpServiceProxyFactory.builderFor(WebClientAdapter.create(webclient))
                .build();
        return httpServiceProxyFactory.createClient(IdentityServiceRepository.class);

    }
}
