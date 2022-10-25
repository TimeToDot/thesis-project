package thesis.data.permission;

import org.springframework.data.jpa.repository.JpaRepository;
import thesis.data.permission.model.Privilege;

import java.util.UUID;

public interface PrivilegeRepository extends JpaRepository<Privilege, UUID> {
  Privilege findByName(String name);
}
