package com.eventiq.identity.controller;

import com.eventiq.identity.dto.ProjectDto;
import com.eventiq.identity.dto.SuccessResponse;
import com.eventiq.identity.repository.ProjectRepository;
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

    private final ProjectRepository projectRepository;
    ProjectService projectService;

    public ProjectController(ProjectService projectService, ProjectRepository projectRepository){
        this.projectService = projectService;
        this.projectRepository = projectRepository;
    }

    @GetMapping("/project")
    public ResponseEntity<List<ProjectDto>> getAllProjectsByUserId(@AuthenticationPrincipal Jwt jwt){
        return ResponseEntity.status(HttpStatus.OK).body(projectService.getAllProjects(jwt.getClaimAsString("sub")));
    }

    @GetMapping("/project/status/{projectId}")
    public ResponseEntity<SuccessResponse> updateProjectStatus(@AuthenticationPrincipal Jwt jwt, @PathVariable String projectId){
        projectService.changeStatus(jwt.getClaimAsString("sub"), projectId);
        return ResponseEntity.status(HttpStatus.OK).body(SuccessResponse.builder().status("SUCCESS").build());
    }

    @PostMapping("/project")
    public ResponseEntity<ProjectDto> createProjectByUserId(@AuthenticationPrincipal Jwt jwt, @RequestBody ProjectDto projectDto){
        return ResponseEntity.status(HttpStatus.CREATED).body(projectService.createProject(jwt.getClaimAsString("sub"), projectDto));
    }

    @DeleteMapping("/project/{projectId}")
    public ResponseEntity<SuccessResponse> deleteProject(@AuthenticationPrincipal Jwt jwt, @PathVariable String projectId){
        projectService.deleteProject(jwt.getClaimAsString("sub"), projectId);
        return ResponseEntity.status(HttpStatus.OK).body(SuccessResponse.builder().status("SUCCESS").build());
    }
}
