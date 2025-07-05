package com.eventiq.identity.controller;

import com.eventiq.identity.dto.BillingDetailsResponse;
import com.eventiq.identity.service.BillingService;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/identity/billing")
public class BillingController {

    BillingService billingService;

    public BillingController(BillingService billingService){
        this.billingService = billingService;
    }

    @GetMapping
    public ResponseEntity<BillingDetailsResponse> getBillingDetails(@AuthenticationPrincipal Jwt jwt){
        return ResponseEntity.status(HttpStatus.OK).body(billingService.getBillingDetails(jwt.getClaimAsString("email")));
    }
}
