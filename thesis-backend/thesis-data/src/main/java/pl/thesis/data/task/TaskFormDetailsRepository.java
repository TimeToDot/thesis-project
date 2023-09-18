package pl.thesis.data.task;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pl.thesis.data.task.model.TaskFormDetails;

@Repository
public interface TaskFormDetailsRepository extends JpaRepository<TaskFormDetails, Long> {
}
