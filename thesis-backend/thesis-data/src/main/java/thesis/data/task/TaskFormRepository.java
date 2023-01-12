package thesis.data.task;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;
import thesis.data.task.model.TaskForm;

import java.util.Optional;
import java.util.UUID;

public interface TaskFormRepository extends JpaRepository<TaskForm, UUID> {

    @Transactional
    Optional<TaskForm> findByName(String name);
}
