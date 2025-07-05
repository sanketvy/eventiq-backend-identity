package com.eventiq.identity.service.impl;

import com.eventiq.identity.constants.StatusEnum;
import com.eventiq.identity.dto.PaymentRequest;
import com.eventiq.identity.model.BillingDetails;
import com.eventiq.identity.model.Payment;
import com.eventiq.identity.repository.BillingDetailsRepository;
import com.eventiq.identity.repository.PaymentRepository;
import com.eventiq.identity.service.PaymentService;
import com.stripe.exception.StripeException;
import com.stripe.model.PaymentIntent;
import com.stripe.param.PaymentIntentCreateParams;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Optional;

@Service
public class PaymentServiceImpl implements PaymentService {

    PaymentRepository paymentRepository;

    BillingDetailsRepository billingDetailsRepository;

    public PaymentServiceImpl(PaymentRepository paymentRepository, BillingDetailsRepository billingDetailsRepository){
        this.paymentRepository = paymentRepository;
        this.billingDetailsRepository = billingDetailsRepository;
    }

    @Override
    public String createPaymentIntent(PaymentRequest request, String email) {
        try {
            PaymentIntentCreateParams params =
                    PaymentIntentCreateParams.builder()
                            .setAmount(getAmount(request.getPlan()))
                            .setCurrency("inr")
                            .setDescription(request.getPlan())
                            .setReceiptEmail(email)
                            .build();

            PaymentIntent intent = PaymentIntent.create(params);

            Payment payment = new Payment();
            payment.setClientSecret(intent.getClientSecret());
            payment.setCreatedAt(LocalDateTime.now());
            payment.setPlan(request.getPlan());
            payment.setUserId(email);
            payment.setAmount(getAmount(request.getPlan())/100);
            payment.setStatus(StatusEnum.IN_PROGRESS.getStatus());
            paymentRepository.save(payment);

            return intent.getClientSecret();
        } catch (StripeException e) {
            throw new RuntimeException(e.getMessage());
        }
    }

    @Override
    public void updatePaymentStatus(PaymentRequest paymentRequest, String email) {
        Optional<Payment> payment = paymentRepository.findByUserIdAndClientSecretEqualsIgnoreCase(email, paymentRequest.getClientSecret());

        if(payment.isPresent()){
            payment.get().setStatus(paymentRequest.getStatus());
            paymentRepository.save(payment.get());
            if(paymentRequest.getStatus().equals("SUCCESS")){
                Optional<BillingDetails> billingDetails = billingDetailsRepository.findByUserId(email);
                if(billingDetails.isPresent()){
                    billingDetails.get().setPlan(payment.get().getPlan());
                    billingDetails.get().setUpdatedDate(LocalDate.now());
                    billingDetails.get().setNextBillingDate(LocalDate.now().plusMonths(1));
                    if(payment.get().getPlan().equals("PRO")){
                        billingDetails.get().setEventLimit(100000L);
                        billingDetails.get().setProjectLimit(100);
                    } if(payment.get().getPlan().equals("ENTERPRISE")){
                        billingDetails.get().setEventLimit(10000000L);
                        billingDetails.get().setProjectLimit(1000);
                    }
                    billingDetailsRepository.save(billingDetails.get());
                }
            }
        } else {
            throw new RuntimeException("Invalid Payment Request");
        }
    }

    private Long getAmount(String plan) {
        if(plan.equals("PRO")){
            return 100000L;
        } else if (plan.equals("ENTERPRISE")){
            return 1000000L;
        } else {
            return 1L;
        }
    }
}
