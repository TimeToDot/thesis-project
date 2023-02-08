package pl.thesis.data.account;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pl.thesis.data.account.model.Account;
import pl.thesis.data.account.model.AccountDetails;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface AccountDetailsRepository extends JpaRepository<AccountDetails, UUID> {

  Optional<AccountDetails> findById(UUID id);
  Optional<AccountDetails> findByName(String name);

  Optional<AccountDetails> findByAccount(Account account);
}
