package thesis.data.position;

import org.springframework.data.jpa.repository.JpaRepository;
import thesis.data.position.model.Position;

import java.util.UUID;

public interface PositionRepository extends JpaRepository<Position, UUID> {

    Position findByName(String name);
}
