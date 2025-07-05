package com.eventiq.identity.constants;

import lombok.Getter;

@Getter
public enum StatusEnum {
    IN_PROGRESS("IN_PROGRESS"),
    SUCCESS("SUCCESS"),
    FAILED("FAILED");

    private final String status;

    StatusEnum(String status) {
        this.status = status;
    }

}
