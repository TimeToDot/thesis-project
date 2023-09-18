package pl.thesis.domain.project;

import org.springframework.transaction.annotation.Transactional;
import pl.thesis.domain.paging.PagingSettings;
import pl.thesis.domain.project.model.task.*;

public interface ProjectTaskService {
    ProjectTaskDetailsDTO getProjectTask(Long projectId, Long taskId);

    ProjectTasksDetailsDTO getAdvancedProjectTasks(Long projectId, Boolean status, PagingSettings settings);

    ProjectTasksDTO getProjectTasks(Long projectId, Boolean status);

    @Transactional
    Long addProjectTask(ProjectTaskCreatePayloadDTO payloadDTO);

    @Transactional
    Long updateProjectTask(ProjectTaskUpdatePayloadDTO payloadDTO);
}
