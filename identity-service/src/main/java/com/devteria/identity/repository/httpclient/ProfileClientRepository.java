package com.devteria.identity.repository.httpclient;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import com.devteria.identity.dto.request.UserProfileRequest;

@FeignClient(name = "profile-service", url = "http://localhost:8081/profile/user")
public interface ProfileClientRepository {

    @PostMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    Object createProfile(@RequestBody UserProfileRequest userProfileRequest);
}
