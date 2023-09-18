package pl.thesis.data.permission;

import org.springframework.data.jpa.repository.JpaRepository;
import pl.thesis.data.permission.model.Privilege;
import pl.thesis.data.permission.model.PrivilegeType;

import java.util.Optional;

public interface PrivilegeRepository extends JpaRepository<Privilege, Long> {
  Optional<Privilege> findByName(PrivilegeType name);
}
