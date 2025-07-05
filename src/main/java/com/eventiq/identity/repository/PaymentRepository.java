package com.eventiq.identity.repository;

import com.eventiq.identity.model.Payment;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface PaymentRepository extends JpaRepository<Payment, Long> {

    Optional<Payment> findByUserIdAndClientSecretEqualsIgnoreCase(String email, String clientSecret);
}
