package pl.thesis.data.position;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import pl.thesis.data.position.model.PositionType;
import pl.thesis.data.account.model.Account;
import pl.thesis.data.position.model.Position;

import java.util.Optional;
import java.util.UUID;

public interface PositionRepository extends JpaRepository<Position, UUID> {

    Optional<Position> findById(UUID id);

    Page<Position> findAllByStatus(PositionType positionType, Pageable pageable);

    boolean existsByName(String name);
    Optional<Position> findByName(String name);

    Optional<Position> findByAccountsEquals(Account account);
}
