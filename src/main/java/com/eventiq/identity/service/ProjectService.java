package com.eventiq.identity.service;

import com.eventiq.identity.dto.ProjectDto;
import org.springframework.stereotype.Service;

import java.util.List;

public interface ProjectService {


    List<ProjectDto> getAllProjects(String userId);

    ProjectDto createProject(String userId, ProjectDto projectDto);

    void deleteProject(String sub, String projectId);

    void changeStatus(String sub, String projectId);
}
