package pl.thesis.data.task;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import pl.thesis.data.task.model.TaskForm;
import pl.thesis.data.task.model.TaskFormType;

import java.util.Optional;

public interface TaskFormRepository extends JpaRepository<TaskForm, Long> {

    Optional<TaskForm> findByName(String name);

    Page<TaskForm> findAllByProjectIdAndDetailsStatus(Long projectId, TaskFormType status, Pageable pageable);
}
