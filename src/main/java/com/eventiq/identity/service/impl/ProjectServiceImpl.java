package com.eventiq.identity.service.impl;

import com.eventiq.identity.dto.ProjectDto;
import com.eventiq.identity.model.Project;
import com.eventiq.identity.repository.ProjectRepository;
import com.eventiq.identity.service.ProjectService;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class ProjectServiceImpl implements ProjectService {

    ProjectRepository projectRepository;

    public ProjectServiceImpl(ProjectRepository projectRepository){
        this.projectRepository = projectRepository;
    }

    @Override
    public List<ProjectDto> getAllProjects(String userId) {

        Optional<List<Project>> projectsList = projectRepository.findAllByUserId(userId);

        if(projectsList.isPresent()){
            List<ProjectDto> projectDtoList = new ArrayList<>();

            for(Project project : projectsList.get()){
                ProjectDto projectDto = new ProjectDto();

                projectDto.setProjectName(project.getProjectName());
                projectDto.setProjectId(project.getProjectId());
                projectDto.setIsActive(project.getIsActive());
                projectDto.setCreatedAt(project.getCreatedAt());
                projectDtoList.add(projectDto);
            }

            return projectDtoList;
        } else {
            return List.of();
        }
    }


    @Override
    public ProjectDto createProject(String userId, ProjectDto projectDto) {
        Project newProject = new Project();
        String projectId = generateProjectId(projectDto.getProjectName());

        newProject.setUserId(userId);
        newProject.setProjectName(projectDto.getProjectName());
        newProject.setIsActive(true);
        newProject.setCreatedAt(LocalDateTime.now());
        newProject.setProjectId(projectId);

        newProject = projectRepository.save(newProject);

        projectDto.setProjectId(projectId);
        projectDto.setIsActive(newProject.getIsActive());
        return projectDto;
    }

    @Override
    public void deleteProject(String userId, String projectId) {
        Optional<Project> project = projectRepository.findByProjectId(projectId);
        if(project.isPresent() && project.get().getUserId().equals(userId)){
            projectRepository.deleteById(project.get().getId());
        } else {
            throw new RuntimeException("Project not found.");
        }
    }

    @Override
    public void changeStatus(String userId, String projectId) {
        Optional<Project> project = projectRepository.findByProjectId(projectId);
        if(project.isPresent() && project.get().getUserId().equals(userId)){
            project.get().setIsActive(!project.get().getIsActive());
            projectRepository.save(project.get());
        } else {
            throw new RuntimeException("Project not found.");
        }

    }

    private static String generateProjectId(String projectName){
        return "IQ" + String.valueOf(System.currentTimeMillis());
    }
}
