package com.eventiq.identity.controller;

import com.eventiq.identity.dto.PaymentRequest;
import com.eventiq.identity.service.PaymentService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/identity/payment")
public class PaymentController {

    PaymentService paymentService;

    public PaymentController(PaymentService paymentService){
        this.paymentService = paymentService;
    }

    @PostMapping("/intent")
    public ResponseEntity<String> getPaymentIntent(@AuthenticationPrincipal Jwt jwt, @RequestBody PaymentRequest request){
        return ResponseEntity.status(HttpStatus.OK).body(paymentService.createPaymentIntent(request, jwt.getClaimAsString("email")));
    }

    @PostMapping("/status")
    public ResponseEntity<String> updatePaymentStatus(@AuthenticationPrincipal Jwt jwt, @RequestBody PaymentRequest paymentRequest){
        paymentService.updatePaymentStatus(paymentRequest, jwt.getClaimAsString("email"));
        return ResponseEntity.status(HttpStatus.OK).body("Success");
    }
}
