package com.eventiq.identity.controller;

import com.eventiq.identity.dto.UserProfile;
import org.springframework.http.HttpStatus;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/identity")
public class IdentityController {

    @GetMapping("/current-user")
    public ResponseEntity<UserProfile> getCurrentUser(@AuthenticationPrincipal Jwt jwt){
        return ResponseEntity.status(HttpStatus.OK).body(UserProfile.builder().firstName(jwt.getClaimAsString("given_name"))
                        .email(jwt.getClaimAsString("email"))
                        .lastName(jwt.getClaimAsString("family_name"))
                .build());
    }
}
