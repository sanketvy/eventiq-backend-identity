package com.eventiq.identity.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class UserProfile {

    private String firstName;

    private String lastName;

    private String email;
}
