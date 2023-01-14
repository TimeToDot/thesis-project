package thesis.data.task;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import thesis.data.task.model.TaskFormDetails;

import java.util.UUID;

@Repository
public interface TaskFormDetailsRepository extends JpaRepository<TaskFormDetails, UUID> {
}
