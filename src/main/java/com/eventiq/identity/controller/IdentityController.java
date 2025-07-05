package com.eventiq.identity.controller;

import com.eventiq.identity.dto.SuccessResponse;
import com.eventiq.identity.dto.UserProfile;
import com.eventiq.identity.service.IdentityService;
import org.springframework.http.HttpStatus;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/identity")
public class IdentityController {

    IdentityService identityService;


    public IdentityController(IdentityService identityService){
        this.identityService = identityService;
    }

    @GetMapping("/current-user")
    public ResponseEntity<UserProfile> getCurrentUser(@AuthenticationPrincipal Jwt jwt){
        identityService.getCurrentUser(jwt);
        return ResponseEntity.status(HttpStatus.OK).body(identityService.getCurrentUser(jwt));
    }

    @GetMapping("/user")
    public ResponseEntity<UserProfile> getUser(@AuthenticationPrincipal Jwt jwt){
        return ResponseEntity.status(HttpStatus.OK).body(identityService.getUser(jwt.getClaimAsString("sub")));
    }

    @PutMapping("/user")
    public ResponseEntity<SuccessResponse> updateUser(@AuthenticationPrincipal Jwt jwt, @RequestBody UserProfile userProfile){
        identityService.updateUser(jwt.getClaimAsString("sub"), userProfile);
        return ResponseEntity.status(HttpStatus.OK).body(SuccessResponse.builder().status("SUCCESS").build());
    }
}
