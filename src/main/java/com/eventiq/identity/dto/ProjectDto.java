package com.eventiq.identity.dto;

import lombok.Data;

@Data
public class ProjectDto {

    private String userId;

    private String projectName;

    private String projectId;

    private Boolean isActive;
}
