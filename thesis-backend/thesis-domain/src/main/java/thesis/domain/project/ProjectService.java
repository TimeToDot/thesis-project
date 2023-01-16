package thesis.domain.project;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import thesis.data.project.ProjectRepository;
import thesis.data.task.TaskFormRepository;
import thesis.data.task.model.TaskForm;
import thesis.data.task.model.TaskFormType;
import thesis.domain.paging.PagingHelper;
import thesis.domain.paging.PagingSettings;
import thesis.domain.project.model.task.ProjectTaskDTO;
import thesis.domain.project.model.task.ProjectTaskDetailsDTO;
import thesis.domain.project.model.task.ProjectTasksDTO;
import thesis.domain.project.model.task.ProjectTasksDetailsDTO;

import java.util.ArrayList;
import java.util.UUID;

@Slf4j
@AllArgsConstructor
@Service
public class ProjectService {

    private final ProjectRepository projectRepository;
    private final TaskFormRepository taskFormRepository;

    public ProjectTaskDetailsDTO getProjectTask(UUID projectId, UUID taskId){
        var taskForm = taskFormRepository.findById(taskId).orElseThrow();

        if (taskForm.getProject().getId().compareTo(projectId) != 0){
            throw new RuntimeException("This task " + taskId + "is not from project: " + projectId);
        }

        return ProjectTaskDetailsDTO.builder()
                .id(taskForm.getId())
                .name(taskForm.getName())
                .description(taskForm.getDescription())
                .creationDate(taskForm.getCreatedAt())
                .archiveDate(taskForm.getArchiveDate())
                .projectId(taskForm.getId())
                .active(taskForm.getDetails().getStatus().compareTo(TaskFormType.OPEN) == 0)
                .build();

    }

    public ProjectTasksDetailsDTO getAdvancedProjectTasks(UUID projectId, Boolean status, PagingSettings settings){
        var taskFormStatus = status ? TaskFormType.OPEN : TaskFormType.CLOSE;

        var taskForms = taskFormRepository.findAllByProjectIdAndDetailsStatus(projectId, taskFormStatus, settings.getPageable());
        var paging = PagingHelper.getPaging(settings, taskForms);
        var sorting = PagingHelper.getSorting(settings);
        var taskFormList = taskForms.stream()
                .map(this::getAdvancedProjectTaskDTO)
                .toList();

        return ProjectTasksDetailsDTO.builder()
                .tasks(taskFormList)
                .sorting(sorting)
                .paging(paging)
                .build();

    }

    public ProjectTasksDTO getProjectTasks(UUID projectId, Boolean status){
        var taskFormStatus = status ? TaskFormType.OPEN : TaskFormType.CLOSE;
        var size = 100;
        var page = 1;
        var settings = PagingSettings.builder().page(page).size(size).build();
        var taskForms = taskFormRepository.findAllByProjectIdAndDetailsStatus(projectId, taskFormStatus, settings.getPageable());
        var tempTaskForms = new ArrayList<>(taskForms.stream().toList());

        while (taskForms.getTotalPages() > page) {
            page += 1;
            settings = PagingSettings.builder().page(page).size(size).build();
            taskForms = taskFormRepository.findAllByProjectIdAndDetailsStatus(projectId, taskFormStatus, settings.getPageable());
            tempTaskForms.addAll(taskForms.stream().toList());
        }

        var taskFormList = tempTaskForms.stream()
                .map(this::getAdvancedProjectTaskDTO)
                .toList();

        return ProjectTasksDTO.builder()
                .tasks(taskFormList)
                .build();
    }

    private ProjectTaskDetailsDTO getAdvancedProjectTaskDTO(TaskForm taskForm) {
        return ProjectTaskDetailsDTO.builder()
                .id(taskForm.getId())
                .name(taskForm.getName())
                .description(taskForm.getDescription())
                .creationDate(taskForm.getCreatedAt())
                .archiveDate(taskForm.getArchiveDate())
                .projectId(taskForm.getProject().getId())
                .build();
    }

    private ProjectTaskDTO getProjectTaskDTO(TaskForm taskForm) {
        return ProjectTaskDTO.builder()
                .id(taskForm.getId())
                .projectId(taskForm.getProject().getId())
                .name(taskForm.getName())
                .build();
    }
}
