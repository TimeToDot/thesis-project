package thesis.data.task;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import thesis.data.task.model.Task;
import thesis.data.task.model.TaskStatus;

import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface TaskRepository extends JpaRepository<Task, UUID> {

    @Transactional
    Optional<Task> findByName(String name);

    @Transactional
    Optional<List<Task>> findByAccountIdAndStatusAndDateFromBetween(UUID id, TaskStatus status, Date startDate, Date endDate);

/*    @Transactional
    Optional<List<Task>> findByAccountIdAndDateFromBetween(UUID id, Date startDate, Date endDate);*/

    @Transactional
    Optional<Page<Task>> findByAccountIdAndDateFromBetween(UUID id, Date startDate, Date endDate, Pageable pageable);
}