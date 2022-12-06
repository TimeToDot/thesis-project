package thesis.data.position;

import org.springframework.data.jpa.repository.JpaRepository;
import thesis.data.position.model.Position;

import java.util.Optional;
import java.util.UUID;

public interface PositionRepository extends JpaRepository<Position, UUID> {

    Optional<Position> findByName(String name);
}
