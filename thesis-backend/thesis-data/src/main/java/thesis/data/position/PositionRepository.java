package thesis.data.position;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import thesis.data.account.model.Account;
import thesis.data.position.model.Position;
import thesis.data.position.model.PositionType;

import java.util.Optional;
import java.util.UUID;

public interface PositionRepository extends JpaRepository<Position, UUID> {

    Optional<Position> findById(UUID id);

    Page<Position> findAllByStatus(PositionType positionType, Pageable pageable);

    boolean existsByName(String name);
    Optional<Position> findByName(String name);

    Optional<Position> findByAccounts(Account account);
}
