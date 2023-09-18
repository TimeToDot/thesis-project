package pl.thesis.data.account;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pl.thesis.data.account.model.Account;
import pl.thesis.data.account.model.AccountDetails;

import java.util.Optional;

@Repository
public interface AccountDetailsRepository extends JpaRepository<AccountDetails, Long> {

  Optional<AccountDetails> findById(Long id);
  Optional<AccountDetails> findByName(String name);

  Optional<AccountDetails> findByAccount(Account account);
}
