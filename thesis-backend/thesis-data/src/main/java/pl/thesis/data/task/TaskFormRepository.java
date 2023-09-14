package pl.thesis.data.task;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;
import pl.thesis.data.task.model.TaskFormType;
import pl.thesis.data.task.model.TaskForm;

import java.util.Optional;
import java.util.UUID;

public interface TaskFormRepository extends JpaRepository<TaskForm, UUID> {

    @Transactional
    Optional<TaskForm> findByName(String name);

    Page<TaskForm> findAllByProjectIdAndDetailsStatus(UUID projectId, TaskFormType status, Pageable pageable);

    /*List<TaskForm> findAllByProjectIdAndDetailsStatus(UUID projectId, TaskFormType status);*/
}