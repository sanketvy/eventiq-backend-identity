package com.eventiq.identity.model;

import com.eventiq.identity.constants.StatusEnum;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Data;

import java.time.LocalDateTime;

@Entity
@Data
public class Payment {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String clientSecret;

    private String userId;

    private String plan;

    private Long amount;

    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;

    private String status;
}
