package pl.thesis.api.project;

import io.swagger.v3.oas.annotations.Hidden;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import pl.thesis.api.ThesisController;
import pl.thesis.api.project.mapper.ProjectMapper;
import pl.thesis.api.project.model.ProjectCreatePayload;
import pl.thesis.api.project.model.ProjectUpdatePayload;
import pl.thesis.api.project.model.employee.ProjectEmployeeCreatePayload;
import pl.thesis.api.project.model.task.ProjectTaskCreatePayload;
import pl.thesis.api.project.model.task.ProjectTaskUpdatePayload;
import pl.thesis.domain.project.ProjectService;
import pl.thesis.domain.project.model.approval.ProjectApprovalsDTO;
import pl.thesis.domain.project.model.employee.ProjectEmployeeDTO;
import pl.thesis.domain.project.model.employee.ProjectEmployeeUpdatePayloadDTO;
import pl.thesis.domain.project.model.employee.ProjectEmployeesDTO;
import pl.thesis.domain.project.model.task.ProjectTaskDetailsDTO;
import pl.thesis.domain.project.model.task.ProjectTasksDTO;
import pl.thesis.domain.project.model.task.ProjectTasksDetailsDTO;

import java.util.UUID;

//@Hidden
@Slf4j
@AllArgsConstructor
@RestController
@RequestMapping("/api/project")
public class ProjectController extends ThesisController {

    private final ProjectService projectService;
    private final ProjectMapper projectMapper;

    @PreAuthorize("hasAuthority('CAN_READ') && hasPermission(#projectId, 'CAN_READ_PROJECT')")
    @GetMapping("/task/{taskId}")
    public ResponseEntity<ProjectTaskDetailsDTO> getProjectTask(
            @RequestHeader UUID employeeId,
            @RequestHeader UUID projectId,
            @PathVariable UUID taskId
    ){
        var response = projectService.getProjectTask(projectId, taskId);

        return ResponseEntity.ok(response);
    }

    @PreAuthorize("hasAuthority('CAN_READ') && hasPermission(#projectId, 'CAN_MANAGE_TASKS')")
    @PutMapping("/task")
    public ResponseEntity<UUID> updateProjectTask(
            @RequestHeader UUID employeeId,
            @RequestHeader UUID projectId,
            @RequestBody ProjectTaskUpdatePayload payload
    ){
        var dto = projectMapper.mapToProjectTaskUpdatePayloadDTO(payload, projectId);
        var response = projectService.updateProjectTask(dto);

        return ResponseEntity.ok(response);

    }

    @PreAuthorize("hasAuthority('CAN_READ') && hasPermission(#projectId, 'CAN_MANAGE_TASKS')")
    @PostMapping("/task")
    public ResponseEntity<UUID> addProjectTask(
            @RequestHeader UUID employeeId,
            @RequestHeader UUID projectId,
            @RequestBody ProjectTaskCreatePayload payload
    ){
        var dto = projectMapper.mapToProjectTaskCreatePayloadDTO(payload, projectId);
        var response = projectService.addProjectTask(dto);

        return ResponseEntity.ok(response);
    }

    @PreAuthorize("hasAuthority('CAN_READ') && hasPermission(#projectId, 'CAN_READ')")
    @GetMapping("/tasks")
    public ResponseEntity<ProjectTasksDetailsDTO> getProjectTasks(
            @RequestHeader UUID employeeId,
            @RequestHeader UUID projectId,
            @RequestParam(value="page", required = false) Integer page,
            @RequestParam(value="size", required = false) Integer size,
            @RequestParam(value="direction", required = false) String direction,
            @RequestParam(value="key", required = false) String key,
            @RequestParam(value="active", required = false, defaultValue = "true") Boolean active
    ){
        var settings = initPaging(page, size, key, direction);

        var response = projectService.getAdvancedProjectTasks(projectId, active, settings);

        return ResponseEntity.ok(response);

    }

    @PreAuthorize("hasAuthority('CAN_READ') && hasPermission(#projectId, 'CAN_READ')")
    @GetMapping("{id}/tasks")
    public ResponseEntity<ProjectTasksDTO> getProjectTasksByEmployee(
            @RequestHeader UUID employeeId,
            @RequestHeader UUID projectId,
            @PathVariable UUID id,
            @RequestParam(value="active", required = false, defaultValue = "true") Boolean active
    ){
        var response = projectService.getProjectTasks(id, active);

        return ResponseEntity.ok(response);
    }

    @PreAuthorize("hasAuthority('CAN_READ') && hasPermission(#projectId, 'CAN_MANAGE_PROJECT_USERS')")
    @GetMapping("/employee/{id}")
    public ResponseEntity<ProjectEmployeeDTO> getProjectEmployee(
            @RequestHeader UUID employeeId,
            @RequestHeader UUID projectId,
            @PathVariable UUID id
    ){
        var response = projectService.getProjectEmployee(projectId, id);

        return ResponseEntity.ok(response);
    }

    @PreAuthorize("hasAuthority('CAN_READ') && hasPermission(#projectId, 'CAN_MANAGE_PROJECT_USERS')")
    @PostMapping("/employee")
    public ResponseEntity<UUID> addProjectEmployee(
            @RequestHeader UUID employeeId,
            @RequestHeader UUID projectId,
            @RequestBody ProjectEmployeeCreatePayload payload
    ){
        var dto = projectMapper.mapToProjectEmployeeCreatePayloadDTO(payload, projectId);
        var response = projectService.addProjectEmployee(dto);

        return ResponseEntity.ok(response);
    }

    @PreAuthorize("hasAuthority('CAN_READ') && hasPermission(#projectId, 'CAN_MANAGE_PROJECT_USERS')")
    @PutMapping("/employee")
    public ResponseEntity<UUID> updateProjectEmployee(
            @RequestHeader UUID employeeId,
            @RequestHeader UUID projectId,
            @RequestBody ProjectEmployeeUpdatePayloadDTO payload
    ){
        var response = projectService.updateProjectEmployee(payload);

        return ResponseEntity.ok(response);
    }

    @PreAuthorize("hasAuthority('CAN_READ') && hasPermission(#projectId, 'CAN_MANAGE_PROJECT_USERS')")
    @GetMapping("/employees")
    public ResponseEntity<ProjectEmployeesDTO> getProjectEmployees(
            @RequestHeader UUID employeeId,
            @RequestHeader UUID projectId,
            @RequestParam(value="page", required = false) Integer page,
            @RequestParam(value="size", required = false) Integer size,
            @RequestParam(value="direction", required = false) String direction,
            @RequestParam(value="key", required = false) String key,
            @RequestParam(value="active", required = false, defaultValue = "true") Boolean active
    ){
        var settings = initPaging(page, size, direction, key);

        var response = projectService.getProjectEmployees(projectId, active, settings);

        return ResponseEntity.ok(response);
    }

    @PreAuthorize("hasAuthority('CAN_READ') && hasPermission(#projectId, 'CAN_MANAGE_PROJECT_USERS')")
    @GetMapping("/approvals")
    public ResponseEntity<ProjectApprovalsDTO> getProjectApprovals(
            @RequestHeader UUID employeeId,
            @RequestHeader UUID projectId,
            @RequestParam(value="page", required = false) Integer page,
            @RequestParam(value="size", required = false) Integer size,
            @RequestParam(value="direction", required = false) String direction,
            @RequestParam(value="key", required = false) String key
    ){
        var settings = initPaging(page, size, direction, key);

        var response = projectService.getProjectApprovalEmployees(projectId, settings);

        return ResponseEntity.ok(response);
    }

    @Hidden
    @PreAuthorize("hasAuthority('CAN_ADMIN_PROJECTS')")
    @PostMapping
    public ResponseEntity<UUID> addProject(
            @RequestHeader UUID employeeId,
            @RequestHeader(required = false) UUID projectId,
            @RequestBody ProjectCreatePayload payload
    ){

        var payloadDto = projectMapper.mapToCreatePayloadDto(payload);
        var response = projectService.addProject(payloadDto);

        return ResponseEntity.ok(response);
    }

    @Hidden
    @PreAuthorize("hasPermission(#projectId, 'CAN_READ_PROJECT')")
    @PutMapping
    public ResponseEntity<UUID> updateProject(
            @RequestHeader UUID employeeId,
            @RequestHeader UUID projectId,
            @RequestBody ProjectUpdatePayload payload
    ){

        var payloadDto = projectMapper.mapToDto(payload, projectId);
        var response = projectService.updateProject(payloadDto);

        return ResponseEntity.ok(response);
    }


}
