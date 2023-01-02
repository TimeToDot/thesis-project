package thesis.data.project;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;
import thesis.data.project.model.ProjectAccountRole;

import java.util.Optional;
import java.util.UUID;

public interface ProjectAccountRoleRepository extends JpaRepository<ProjectAccountRole, UUID> {

    @Transactional
    Optional<Page<ProjectAccountRole>> findAllByAccount_Id(UUID id, Pageable pageable);
}
