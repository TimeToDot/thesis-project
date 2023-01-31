package pl.thesis.data.project;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pl.thesis.data.account.model.Account;
import pl.thesis.data.project.model.Project;
import pl.thesis.data.project.model.ProjectType;

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

  Optional<List<Project>> findAllByStatus(ProjectType status);
  List<Project> findByOwner(Account account);
}