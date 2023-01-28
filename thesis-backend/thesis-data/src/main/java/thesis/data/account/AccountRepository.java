package thesis.data.account;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import thesis.data.account.model.Account;
import thesis.data.account.model.StatusType;
import thesis.data.role.model.Role;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface AccountRepository extends JpaRepository<Account, UUID> {

  Optional<Account> findById(UUID id);

  Page<Account> findAllByStatus(StatusType status, Pageable pageable);


  @Query("select a from Account a " +
          "inner join AccountRole ar on a.id = ar.account " +
          "inner join Role r on ar.role = r.id " +
          "where r = ?1 and a.status = ?2"
  )
  Page<Account> findAllByRoleAndStatus(Role role, StatusType status, Pageable pageable);

  Optional<Account> findByEmail(String email);

  Boolean existsByEmail(String email);
}