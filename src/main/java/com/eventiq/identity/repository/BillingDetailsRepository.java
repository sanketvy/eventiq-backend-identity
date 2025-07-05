package com.eventiq.identity.repository;

import com.eventiq.identity.model.BillingDetails;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BillingDetailsRepository extends JpaRepository<BillingDetails, Long> {
}
