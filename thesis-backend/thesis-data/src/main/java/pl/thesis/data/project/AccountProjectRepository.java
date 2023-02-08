package pl.thesis.data.project;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;
import pl.thesis.data.project.model.AccountProject;
import pl.thesis.data.project.model.ProjectAccountStatus;

import java.util.Optional;
import java.util.UUID;

public interface AccountProjectRepository extends JpaRepository<AccountProject, UUID> {

    @Transactional
    Optional<Page<AccountProject>> findAllByAccountId(UUID id, Pageable pageable);

    @Transactional
    Optional<Page<AccountProject>> findAllByProjectIdAndStatus(UUID id, ProjectAccountStatus status, Pageable pageable);

    @Transactional
    Optional<AccountProject> findByAccountId(UUID id);

    @Transactional
    Optional<AccountProject> findByAccountIdAndProjectId(UUID accountId, UUID projectId);
}
