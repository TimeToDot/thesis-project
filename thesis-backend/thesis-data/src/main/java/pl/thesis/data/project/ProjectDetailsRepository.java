package pl.thesis.data.project;

import org.springframework.data.jpa.repository.JpaRepository;
import pl.thesis.data.project.model.Project;
import pl.thesis.data.project.model.ProjectDetails;

import java.util.Optional;
import java.util.UUID;

public interface ProjectDetailsRepository extends JpaRepository<ProjectDetails, UUID> {

    Optional<ProjectDetails> findByProject(Project project);
}