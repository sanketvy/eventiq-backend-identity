package com.eventiq.identity.repository;

import com.eventiq.identity.model.BillingDetails;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface BillingDetailsRepository extends JpaRepository<BillingDetails, Long> {
    Optional<BillingDetails> findByUserId(String userId);
}
