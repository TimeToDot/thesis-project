package pl.thesis.data.account;

import org.springframework.data.jpa.repository.JpaRepository;
import pl.thesis.data.account.model.AccountRole;

public interface AccountRoleRepository extends JpaRepository<AccountRole, Long> {

}
