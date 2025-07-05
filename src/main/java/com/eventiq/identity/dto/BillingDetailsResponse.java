package com.eventiq.identity.dto;

import lombok.Data;

import java.time.LocalDate;

@Data
public class BillingDetailsResponse {

    private LocalDate nextBillingDate;

    private String plan;

    private Long eventLimit;

    private Long eventsUsed;

    private Integer projectLimit;

    private Integer projectsUsed;

}
