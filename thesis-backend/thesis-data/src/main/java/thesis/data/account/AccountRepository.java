package thesis.data.account;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import thesis.data.account.model.Account;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface AccountRepository extends JpaRepository<Account, UUID> {
  Optional<Account> findByLogin(String login);

  Boolean existsByLogin(String login);

  Boolean existsByDetails_Email(String email);
}