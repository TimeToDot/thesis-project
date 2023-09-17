package pl.thesis.domain.project;

import org.springframework.transaction.annotation.Transactional;
import pl.thesis.domain.project.model.ProjectCreatePayloadDTO;
import pl.thesis.domain.project.model.ProjectDTO;
import pl.thesis.domain.project.model.ProjectUpdatePayloadDTO;
import pl.thesis.domain.project.model.ProjectsDTO;
import pl.thesis.domain.project.model.approval.ProjectCalendarDTO;

import java.util.Date;

public interface ProjectService {
    ProjectDTO getProject(Long projectId);

    ProjectsDTO getProjects(Boolean active);

    ProjectCalendarDTO getProjectCalendar(Long id, Long projectId, Date date);

    @Transactional
    Long addProject(ProjectCreatePayloadDTO payloadDTO);

    @Transactional
    Long updateProject(ProjectUpdatePayloadDTO payloadDTO);
}
