package com.eventiq.identity.service;

import com.eventiq.identity.dto.BillingDetailsResponse;

public interface BillingService {
    BillingDetailsResponse getBillingDetails(String email);
}
