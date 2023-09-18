package pl.thesis.api.project.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import pl.thesis.security.converter.IdConverter;
import pl.thesis.api.employee.model.project.temp.ProjectTempResponse;
import pl.thesis.api.global.GlobalMapper;
import pl.thesis.api.position.PositionMapper;
import pl.thesis.api.project.model.ProjectCreatePayload;
import pl.thesis.api.project.model.ProjectResponse;
import pl.thesis.api.project.model.ProjectUpdatePayload;
import pl.thesis.api.project.model.ProjectsResponse;
import pl.thesis.api.project.model.approval.ProjectApprovalResponse;
import pl.thesis.api.project.model.approval.ProjectApprovalsResponse;
import pl.thesis.api.project.model.approval.ProjectCalendarResponse;
import pl.thesis.api.project.model.employee.*;
import pl.thesis.api.project.model.task.ProjectTaskCreatePayload;
import pl.thesis.api.project.model.task.ProjectTaskDetails;
import pl.thesis.api.project.model.task.ProjectTaskUpdatePayload;
import pl.thesis.api.project.model.task.ProjectTasksDetails;
import pl.thesis.domain.employee.model.EmployeeProjectDTO;
import pl.thesis.domain.mapper.MapStructConfig;
import pl.thesis.domain.project.model.ProjectCreatePayloadDTO;
import pl.thesis.domain.project.model.ProjectDTO;
import pl.thesis.domain.project.model.ProjectUpdatePayloadDTO;
import pl.thesis.domain.project.model.ProjectsDTO;
import pl.thesis.domain.project.model.approval.ProjectApprovalDTO;
import pl.thesis.domain.project.model.approval.ProjectApprovalTasksPayloadDTO;
import pl.thesis.domain.project.model.approval.ProjectApprovalsDTO;
import pl.thesis.domain.project.model.approval.ProjectCalendarDTO;
import pl.thesis.domain.project.model.employee.*;
import pl.thesis.domain.project.model.task.ProjectTaskCreatePayloadDTO;
import pl.thesis.domain.project.model.task.ProjectTaskDetailsDTO;
import pl.thesis.domain.project.model.task.ProjectTaskUpdatePayloadDTO;
import pl.thesis.domain.project.model.task.ProjectTasksDetailsDTO;

import java.util.List;

@Mapper(
        config = MapStructConfig.class,
        uses = {
                GlobalMapper.class,
                PositionMapper.class,
                IdConverter.class
        }
)
public interface ProjectMapper {

    @Mapping(target = "moderatorId", source = "moderatorId", qualifiedByName = {"mapToId"})
    ProjectCreatePayloadDTO mapToCreatePayloadDto(ProjectCreatePayload payload);

    @Mapping(target = "projectId", source = "projectId")
    @Mapping(target = "moderatorId", source = "payload.moderatorId", qualifiedByName = {"mapToId"})
    ProjectUpdatePayloadDTO mapToUpdatePayloadDto(ProjectUpdatePayload payload, Long projectId);

    @Mapping(target = "moderatorId", source = "payload.moderatorId", qualifiedByName = {"mapToId"})
    ProjectUpdatePayloadDTO mapToDto(ProjectUpdatePayload payload, Long projectId);

    @Mapping(target = "id", source = "id", qualifiedByName = {"mapToText"})
    @Mapping(target = "moderatorId", source = "moderatorId", qualifiedByName = {"mapToText"})
    ProjectResponse mapToProjectResponse(ProjectDTO projectDTO);

    ProjectsResponse mapToProjectsResponse(ProjectsDTO projectsDTO);

    @Mapping(target = "id", source = "id", qualifiedByName = {"mapToText"})
    ProjectTempResponse mapToProjectTempResponse(EmployeeProjectDTO dto);

    //tasks

    @Mapping(target = "id", source = "id", qualifiedByName = {"mapToText"})
    @Mapping(target = "projectId", source = "projectId", qualifiedByName = {"mapToText"})
    ProjectTaskDetails mapToProjectTaskDetailsResponse(ProjectTaskDetailsDTO detailsDTO);

    ProjectTasksDetails mapToProjectTasksDetailsResponse(ProjectTasksDetailsDTO detailsDTO);

    @Mapping(target = "id", source = "payload.id", qualifiedByName = {"mapToId"})
    @Mapping(target = "projectId", source = "projectId", qualifiedByName = {"mapToId"})
    ProjectTaskUpdatePayloadDTO mapToProjectTaskUpdatePayloadDTO(ProjectTaskUpdatePayload payload, Long projectId);

    @Mapping(target = "projectId", source = "projectId", qualifiedByName = {"mapToId"})
    ProjectTaskCreatePayloadDTO mapToProjectTaskCreatePayloadDTO(ProjectTaskCreatePayload payload, Long projectId);

    //employees
    @Mapping(target = "projectEmployeeId", source = "projectEmployeeId", qualifiedByName = {"mapToText"})
    ProjectEmployeeResponse mapToProjectEmployeeResponse(ProjectEmployeeDTO dto);

    @Mapping(target = "employeeId", source = "employeeId", qualifiedByName = {"mapToText"})
    EmployeeResponse mapToEmployeeResponse(EmployeeDTO dto);

    @Mapping(target = "projectId", source = "projectId", qualifiedByName = {"mapToId"})
    @Mapping(target = "employeeId", source = "payload.employeeId", qualifiedByName = {"mapToId"})
    ProjectEmployeeCreatePayloadDTO mapToProjectEmployeeCreatePayloadDTO(ProjectEmployeeCreatePayload payload, Long projectId);

    @Mapping(target = "projectId", source = "projectId", qualifiedByName = {"mapToId"})
    @Mapping(target = "projectEmployeeId", source = "projectEmployeeId", qualifiedByName = {"mapToId"})
    ProjectEmployeeUpdatePayloadDTO mapToProjectEmployeeUpdatePayloadDTO(ProjectEmployeeUpdatePayload payload, Long projectId, Long projectEmployeeId);

    ProjectEmployeesResponse mapToProjectEmployeesResponse(ProjectEmployeesDTO dto);

    //approvals

    @Mapping(target = "projectEmployeeId", source = "projectEmployeeId", qualifiedByName = {"mapToText"})
    ProjectApprovalResponse mapToProjectApprovalResponse(ProjectApprovalDTO dto);

    ProjectApprovalsResponse mapToProjectApprovalsResponse(ProjectApprovalsDTO dto);

    @Mapping(target = "projectId", source = "projectId")
    @Mapping(target = "accountProjectId", source = "accountProjectId")
    @Mapping(target = "tasks", source = "tasks", qualifiedByName = {"mapToIdList"})
    ProjectApprovalTasksPayloadDTO mapToProjectApprovalTasksPayloadDTO(List<String> tasks, Long projectId, Long accountProjectId);

    @Mapping(target = "projectEmployeeId", source = "projectEmployeeId", qualifiedByName = {"mapToText"})
    @Mapping(target = "employeeId", source = "employeeId", qualifiedByName = {"mapToText"})
    ProjectCalendarResponse mapToProjectCalendarResponse(ProjectCalendarDTO dto);

    @Mapping(target = "moderatorId", source = "moderatorId", qualifiedByName = {"mapToId"})
    ProjectCreatePayloadDTO mapToProjectCreatePayloadDTO(ProjectCreatePayload dto);


}
