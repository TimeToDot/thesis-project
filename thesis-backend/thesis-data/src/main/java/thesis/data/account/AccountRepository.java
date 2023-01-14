package thesis.data.account;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import thesis.data.account.model.Account;
import thesis.data.account.model.StatusType;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface AccountRepository extends JpaRepository<Account, UUID> {

  Optional<Account> findById(UUID id);

  Page<Account> findAllByStatus(StatusType status, Pageable pageable);
  Optional<Account> findByLogin(String login);

  Boolean existsByLogin(String login);

  Boolean existsByEmail(String email);
}