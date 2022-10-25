package thesis.data.role;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import thesis.data.role.model.Role;
import thesis.data.role.model.RoleType;

import java.util.UUID;

@Repository
public interface RoleRepository extends JpaRepository<Role, UUID> {
  Role findByName(RoleType name);
}