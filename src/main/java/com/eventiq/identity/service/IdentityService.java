package com.eventiq.identity.service;

import com.eventiq.identity.dto.UserProfile;
import org.springframework.security.oauth2.jwt.Jwt;

public interface IdentityService {
    UserProfile getUser(String sub);

    void updateUser(String sub, UserProfile userProfile);

    UserProfile getCurrentUser(Jwt jwt);
}
