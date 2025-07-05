package com.eventiq.identity.service.impl;

import com.eventiq.identity.dto.BillingDetailsResponse;
import com.eventiq.identity.model.BillingDetails;
import com.eventiq.identity.repository.BillingDetailsRepository;
import com.eventiq.identity.service.BillingService;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class BillingServiceImpl implements BillingService {

    BillingDetailsRepository billingDetailsRepository;

    public BillingServiceImpl(BillingDetailsRepository billingDetailsRepository){
        this.billingDetailsRepository = billingDetailsRepository;
    }

    @Override
    public BillingDetailsResponse getBillingDetails(String email) {
        Optional<BillingDetails> details = billingDetailsRepository.findByUserId(email);
        if(details.isPresent()){
            BillingDetailsResponse billingDetailsResponse = new BillingDetailsResponse();

            billingDetailsResponse.setNextBillingDate(details.get().getNextBillingDate());
            billingDetailsResponse.setProjectLimit(details.get().getProjectLimit());
            billingDetailsResponse.setProjectsUsed(details.get().getProjectsUsed());
            billingDetailsResponse.setEventLimit(details.get().getEventLimit());
            billingDetailsResponse.setEventsUsed(details.get().getEventsUsed());
            billingDetailsResponse.setPlan(details.get().getPlan());
            return billingDetailsResponse;
        }else {
            throw new RuntimeException("No Billing Details Found");
        }
    }
}
