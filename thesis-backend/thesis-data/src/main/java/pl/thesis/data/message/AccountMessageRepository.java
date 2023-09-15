package pl.thesis.data.message;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pl.thesis.data.message.model.AccountMessage;

import java.util.List;

@Repository
public interface AccountMessageRepository extends JpaRepository<AccountMessage, Long> {
  List<AccountMessage> findByAccountFromId(Long accountFromId);
  List<AccountMessage> findByAccountFromIdOrderByData(Long accountFromId);
  List<AccountMessage> findByAccountFromIdAndAccountToId(Long accountFromId, Long accountToId);
}