package thesis.data.project;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;
import thesis.data.project.model.ProjectAccount;

import java.util.Optional;
import java.util.UUID;

public interface ProjectAccountRepository extends JpaRepository<ProjectAccount, UUID> {

    @Transactional
    Optional<Page<ProjectAccount>> findAllByAccount_Id(UUID id, Pageable pageable);
}
