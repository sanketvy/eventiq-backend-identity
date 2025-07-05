package com.eventiq.identity.service;

import com.eventiq.identity.dto.PaymentRequest;

public interface PaymentService {

    String createPaymentIntent(PaymentRequest request, String email);

    void updatePaymentStatus(PaymentRequest paymentRequest, String email);
}
