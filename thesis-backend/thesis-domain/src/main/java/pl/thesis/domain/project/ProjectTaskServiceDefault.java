package pl.thesis.domain.project;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pl.thesis.data.project.ProjectRepository;
import pl.thesis.data.task.TaskFormDetailsRepository;
import pl.thesis.data.task.TaskFormRepository;
import pl.thesis.data.task.model.TaskForm;
import pl.thesis.data.task.model.TaskFormDetails;
import pl.thesis.data.task.model.TaskFormType;
import pl.thesis.domain.paging.PagingHelper;
import pl.thesis.domain.paging.PagingSettings;
import pl.thesis.domain.project.mapper.ProjectTaskDetailsDTOMapper;
import pl.thesis.domain.project.model.task.*;

import java.util.ArrayList;
import java.util.Date;

@Slf4j
@AllArgsConstructor
@Service
public class ProjectTaskServiceDefault implements ProjectTaskService {

    private final ProjectRepository projectRepository;
    private final TaskFormRepository taskFormRepository;
    private final TaskFormDetailsRepository taskFormDetailsRepository;
    private final ProjectTaskDetailsDTOMapper projectTaskDetailsDTOMapper;

    @Override
    public ProjectTaskDetailsDTO getProjectTask(Long projectId, Long taskId){
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

    @Override
    public ProjectTasksDetailsDTO getAdvancedProjectTasks(Long projectId, Boolean status, PagingSettings settings){
        var taskFormStatus = Boolean.TRUE.equals(status) ? TaskFormType.OPEN : TaskFormType.CLOSE;

        var taskForms = taskFormRepository.findAllByProjectIdAndDetailsStatus(projectId, taskFormStatus, settings.getPageable());
        var paging = PagingHelper.getPaging(settings, taskForms);
        var sorting = PagingHelper.getSorting(settings);
        var taskFormList = taskForms.stream()
                .map(projectTaskDetailsDTOMapper::mapToProjectTaskDetailsDTO)
                .toList();

        return ProjectTasksDetailsDTO.builder()
                .tasks(taskFormList)
                .sorting(sorting)
                .paging(paging)
                .build();

    }

    @Override
    public ProjectTasksDTO getProjectTasks(Long projectId, Boolean status){
        var taskFormStatus = Boolean.TRUE.equals(status) ? TaskFormType.OPEN : TaskFormType.CLOSE;
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
                .map(projectTaskDetailsDTOMapper::mapToProjectTaskDetailsDTO)
                .toList();

        return ProjectTasksDTO.builder()
                .tasks(taskFormList)
                .build();
    }

    @Override
    @Transactional
    public Long addProjectTask(ProjectTaskCreatePayloadDTO payloadDTO){
        var project = projectRepository.findById(payloadDTO.projectId()).orElseThrow();

        var taskForm = TaskForm.builder()
                .project(project)
                .name(payloadDTO.name())
                .description(payloadDTO.description())
                .createdAt(new Date())
                .build();

        taskFormRepository.save(taskForm);

        var taskFormDetails = TaskFormDetails.builder()
                .taskForm(taskForm)
                .status(TaskFormType.OPEN)
                .build();

        taskFormDetailsRepository.save(taskFormDetails);

        return taskForm.getId();
    }

    @Override
    @Transactional
    public Long updateProjectTask(ProjectTaskUpdatePayloadDTO payloadDTO){
        var project = projectRepository.findById(payloadDTO.projectId()).orElseThrow();
        var taskForm = taskFormRepository.findById(payloadDTO.id()).orElseThrow();

        taskForm.setName(payloadDTO.name());
        taskForm.setDescription(payloadDTO.description());
        var status = Boolean.TRUE.equals(payloadDTO.active()) ? TaskFormType.OPEN : TaskFormType.CLOSE;
        taskForm.getDetails().setStatus(status);

        taskFormRepository.save(taskForm);

        return taskForm.getId();
    }
}
