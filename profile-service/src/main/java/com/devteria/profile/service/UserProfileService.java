package com.devteria.profile.service;

import org.springframework.stereotype.Service;

import com.devteria.profile.dto.request.ProfileCreationRequest;
import com.devteria.profile.dto.response.UserProfileResponse;
import com.devteria.profile.entity.UserProfile;
import com.devteria.profile.mapper.UserProfileMapper;
import com.devteria.profile.repository.UserprofileRepository;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;

@Slf4j(topic = "profile-service")
@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class UserProfileService {

    UserprofileRepository userProfileRepository;
    UserProfileMapper userProfileMapper;

    public UserProfileResponse createProfile(ProfileCreationRequest request) {
        log.info("Creating user profile for userId: {}", request.getUserId());
        UserProfile save = userProfileMapper.toUserProfile(request);
        UserProfile savedProfile = userProfileRepository.save(save);
        return userProfileMapper.toUserProfileResponse(savedProfile);
    }

    public UserProfileResponse getProfile(String userId) {
        log.info("Fetching user profile for userId: {}", userId);
        UserProfile userProfile = userProfileRepository
                .findById(userId)
                .orElseThrow(() -> new RuntimeException("User profile not found"));
        return userProfileMapper.toUserProfileResponse(userProfile);
    }
}
