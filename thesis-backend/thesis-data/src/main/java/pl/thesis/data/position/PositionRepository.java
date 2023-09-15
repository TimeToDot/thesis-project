package pl.thesis.data.position;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import pl.thesis.data.account.model.Account;
import pl.thesis.data.position.model.Position;
import pl.thesis.data.position.model.PositionType;

import java.util.Optional;

public interface PositionRepository extends JpaRepository<Position, Long> {

    Optional<Position> findById(Long id);

    Page<Position> findAllByStatus(PositionType positionType, Pageable pageable);

    boolean existsByName(String name);
    Optional<Position> findByName(String name);

    Optional<Position> findByAccountsEquals(Account account);
}
