package thesis.data.project;

import org.springframework.data.jpa.repository.JpaRepository;
import thesis.data.project.model.ProjectAccountRole;

import java.util.UUID;

public interface ProjectAccountRoleRepository extends JpaRepository<ProjectAccountRole, UUID> {
}
