package com.eventiq.identity.controller;

import com.eventiq.identity.dto.ProjectDto;
import com.eventiq.identity.service.ProjectService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/identity")
public class ProjectController {

    ProjectService projectService;

    public ProjectController(ProjectService projectService){
        this.projectService = projectService;
    }

    @GetMapping("/project")
    public ResponseEntity<List<ProjectDto>> getAllProjectsByUserId(@AuthenticationPrincipal Jwt jwt){
        return ResponseEntity.status(HttpStatus.OK).body(projectService.getAllProjects(jwt.getClaimAsString("sub")));
    }

    @PostMapping("/project")
    public ResponseEntity<ProjectDto> createProjectByUserId(@AuthenticationPrincipal Jwt jwt, @RequestBody ProjectDto projectDto){
        return ResponseEntity.status(HttpStatus.CREATED).body(projectService.createProject(jwt.getClaimAsString("sub"), projectDto));
    }
}
