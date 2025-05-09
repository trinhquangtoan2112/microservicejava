package com.devteria.identity.repository.httpclient;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import com.devteria.identity.configuration.AuthenticatedRequestInterco;
import com.devteria.identity.dto.request.UserProfileRequest;

@FeignClient(
        name = "profile-service",
        url = "${app.services.profile}",
        configuration = {AuthenticatedRequestInterco.class})
public interface ProfileClientRepository {

    @PostMapping(value = "/internal/user", produces = MediaType.APPLICATION_JSON_VALUE)
    Object createProfile(@RequestBody UserProfileRequest userProfileRequest);
}
