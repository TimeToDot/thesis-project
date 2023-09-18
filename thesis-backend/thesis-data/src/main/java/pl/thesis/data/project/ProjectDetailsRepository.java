package pl.thesis.data.project;

import org.springframework.data.jpa.repository.JpaRepository;
import pl.thesis.data.project.model.Project;
import pl.thesis.data.project.model.ProjectDetails;

import java.util.Optional;

public interface ProjectDetailsRepository extends JpaRepository<ProjectDetails, Long> {

    Optional<ProjectDetails> findByProject(Project project);
}
