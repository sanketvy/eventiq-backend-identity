package com.eventiq.identity.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Data;

import java.time.LocalDate;

@Entity
@Data
public class BillingDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String userId;

    private LocalDate updatedDate;

    private LocalDate nextBillingDate;

    private String plan;

    private Long eventLimit;

    private Long eventsUsed;

    private Integer projectLimit;

    private Integer projectsUsed;
}
