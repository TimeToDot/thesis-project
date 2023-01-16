package thesis.domain.project;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import thesis.data.project.ProjectRepository;
import thesis.data.task.TaskFormRepository;
import thesis.data.task.model.TaskFormType;
import thesis.domain.project.model.task.ProjectTaskDTO;

import java.util.UUID;

@Slf4j
@AllArgsConstructor
@Service
public class ProjectService {

    private final ProjectRepository projectRepository;
    private final TaskFormRepository taskFormRepository;

    public ProjectTaskDTO getProjectTask(UUID projectId, UUID taskId){
        var taskForm = taskFormRepository.findById(taskId).orElseThrow();

        return ProjectTaskDTO.builder()
                .id(taskForm.getId())
                .name(taskForm.getName())
                .description(taskForm.getDescription())
                .creationDate(taskForm.getCreatedAt())
                .archiveDate(taskForm.getArchiveDate())
                .projectId(taskForm.getId())
                .active(taskForm.getDetails().getStatus().compareTo(TaskFormType.OPEN) == 0)
                .build();

    }
}
