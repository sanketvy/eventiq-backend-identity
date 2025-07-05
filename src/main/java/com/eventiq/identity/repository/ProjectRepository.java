package com.eventiq.identity.repository;

import com.eventiq.identity.model.Project;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ProjectRepository extends JpaRepository<Project, Long> {

    Optional<List<Project>> findAllByUserId(String userId);

    Optional<Project> findByProjectId(String projectId);
}
