package thesis.data.account;

import org.springframework.data.jpa.repository.JpaRepository;
import thesis.data.account.model.AccountRole;

import java.util.UUID;

public interface AccountRoleRepository extends JpaRepository<AccountRole, UUID> {
}
