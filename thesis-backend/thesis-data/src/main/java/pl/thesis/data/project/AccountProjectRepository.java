package pl.thesis.data.project;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;
import pl.thesis.data.project.model.AccountProject;
import pl.thesis.data.project.model.ProjectAccountStatus;

import java.util.Optional;

public interface AccountProjectRepository extends JpaRepository<AccountProject, Long> {

    @Transactional
    Optional<Page<AccountProject>> findAllByAccountId(Long id, Pageable pageable);

    @Transactional
    Optional<Page<AccountProject>> findAllByProjectIdAndStatus(Long id, ProjectAccountStatus status, Pageable pageable);

    @Transactional
    Optional<AccountProject> findByAccountId(Long id);

    @Transactional
    Optional<AccountProject> findByAccountIdAndProjectId(Long accountId, Long projectId);
}
