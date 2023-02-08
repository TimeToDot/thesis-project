package pl.thesis.data.message;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pl.thesis.data.message.model.AccountMessage;

import java.util.List;
import java.util.UUID;

@Repository
public interface AccountMessageRepository extends JpaRepository<AccountMessage, UUID> {
  List<AccountMessage> findByAccountFromId(UUID accountFromId);
  List<AccountMessage> findByAccountFromIdOrderByData(UUID accountFromId);
  List<AccountMessage> findByAccountFromIdAndAccountToId(UUID accountFromId, UUID accountToId);
}