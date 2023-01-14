package thesis.data.project;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;
import thesis.data.project.model.AccountProject;

import java.util.Optional;
import java.util.UUID;

public interface AccountProjectRepository extends JpaRepository<AccountProject, UUID> {

    @Transactional
    Optional<Page<AccountProject>> findAllByAccount_Id(UUID id, Pageable pageable);

    @Transactional
    Optional<AccountProject> findByAccountId(UUID id);
}
