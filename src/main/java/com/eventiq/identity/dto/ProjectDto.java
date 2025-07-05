package com.eventiq.identity.dto;

import lombok.Data;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
public class ProjectDto {

    private String userId;

    private String projectName;

    private String projectId;

    private Boolean isActive;

    private LocalDateTime createdAt;
}
