package com.eventiq.identity.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity
public class BillingDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String userId;

    private String updatedDate;

    private String nextBillingDate;

    private String plan;

    private Long eventLimit;

    private Long eventsUsed;

    private Long projectLimit;

    private Long projectsUsed;
}
