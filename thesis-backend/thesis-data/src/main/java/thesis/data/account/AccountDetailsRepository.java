package thesis.data.account;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import thesis.data.account.model.AccountDetails;

import java.util.Optional;

@Repository
public interface AccountDetailsRepository extends JpaRepository<AccountDetails, Long> {
  Optional<AccountDetails> findByAccountId(Long id);

  Optional<AccountDetails> findByEmail(String email);
}
