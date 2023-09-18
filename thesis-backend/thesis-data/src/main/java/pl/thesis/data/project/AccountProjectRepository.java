package pl.thesis.data.project;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import pl.thesis.data.project.model.AccountProject;
import pl.thesis.data.project.model.ProjectAccountStatus;

import java.util.Optional;

public interface AccountProjectRepository extends JpaRepository<AccountProject, Long> {

    Optional<Page<AccountProject>> findAllByAccountId(Long id, Pageable pageable);

    Optional<Page<AccountProject>> findAllByProjectIdAndStatus(Long id, ProjectAccountStatus status, Pageable pageable);

    Optional<AccountProject> findByAccountId(Long id);

    Optional<AccountProject> findByAccountIdAndProjectId(Long accountId, Long projectId);
}
