package pl.thesis.data.account;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import pl.thesis.data.account.model.Account;
import pl.thesis.data.account.model.StatusType;
import pl.thesis.data.role.model.Role;

import java.util.Optional;

@Repository
public interface AccountRepository extends JpaRepository<Account, Long> {

  Optional<Account> findById(Long id);

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