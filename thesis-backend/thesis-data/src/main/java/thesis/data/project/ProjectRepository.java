package thesis.data.project;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import thesis.data.account.model.Account;
import thesis.data.project.model.Project;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

/**
 * <h1>ProjectRepository</h1>
 *
 * @author docz <a href="http://www.ailleron.com">AILLERON S.A.</a>
 * @version 1.0.0
 * @since TODO
 */
@Repository
public interface ProjectRepository extends JpaRepository<Project, UUID> {
  Optional<Project> findByName(String name);
  List<Project> findByOwner(Account account);
}