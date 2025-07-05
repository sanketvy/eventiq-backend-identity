package com.eventiq.identity.dto;

import lombok.Data;

@Data
public class PaymentRequest {

    private String plan;

    private String status;

    private String clientSecret;

}
