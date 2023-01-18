package thesis.api.project;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import thesis.api.ThesisController;
import thesis.domain.project.ProjectService;
import thesis.domain.project.model.ProjectCreatePayloadDTO;
import thesis.domain.project.model.ProjectDTO;
import thesis.domain.project.model.ProjectUpdatePayloadDTO;
import thesis.domain.project.model.ProjectsDTO;
import thesis.domain.project.model.approval.ProjectApprovalsDTO;
import thesis.domain.project.model.employee.ProjectEmployeeCreatePayloadDTO;
import thesis.domain.project.model.employee.ProjectEmployeeDTO;
import thesis.domain.project.model.employee.ProjectEmployeeUpdatePayloadDTO;
import thesis.domain.project.model.employee.ProjectEmployeesDTO;
import thesis.domain.project.model.task.*;

import java.util.List;
import java.util.UUID;

@Slf4j
@AllArgsConstructor
@RestController
@RequestMapping("/api/projects")
public class ProjectsController extends ThesisController {

    private final ProjectService projectService;
    //@PreAuthorize("hasAuthority('CAN_ADMIN_PROJECTS')")
    @GetMapping
    public ResponseEntity<List<ProjectDTO>> getProjects(
            @RequestHeader(required = false)  UUID employeeId,
            @RequestHeader(required = false)  UUID projectId,
            @RequestParam(value="active", required = false, defaultValue = "true") Boolean active
    ){
        var response = projectService.getProjects(active);

        return ResponseEntity.ok(response.project());
    }

    //@PreAuthorize("hasAuthority('CAN_ADMIN_PROJECTS')")
    @GetMapping("/{pid}")
    public ResponseEntity<ProjectDTO> getProject(
            @RequestHeader(required = false)  UUID employeeId,
            @RequestHeader(required = false)  UUID projectId,
            @RequestParam(value="active", required = false, defaultValue = "true") Boolean active,
            @PathVariable UUID pid
    ){
        var response = projectService.getProject(pid);

        return ResponseEntity.ok(response);
    }

    //@PreAuthorize("hasAuthority('CAN_READ') && hasPermission(#projectId, 'CAN_READ_PROJECT')")
    @GetMapping("/{pid}/tasks/{taskId}")
    public ResponseEntity<ProjectTaskDetailsDTO> getProjectTask(
            @RequestHeader(required = false) UUID employeeId,
            @RequestHeader(required = false) UUID projectId,
            @PathVariable UUID taskId,
            @PathVariable UUID pid
    ){
        var response = projectService.getProjectTask(projectId, taskId);

        return ResponseEntity.ok(response);
    }

    //@PreAuthorize("hasAuthority('CAN_READ') && hasPermission(#projectId, 'CAN_MANAGE_TASKS')")
    @PutMapping("/{pid}/tasks/{taskId}")
    public ResponseEntity<UUID> updateProjectTask(
            @RequestHeader(required = false) UUID employeeId,
            @RequestHeader(required = false) UUID projectId,
            @RequestBody ProjectTaskUpdatePayloadDTO payload, //change
            @PathVariable UUID pid,
            @PathVariable UUID taskId
    ){
        var response = projectService.updateProjectTask(projectId, payload);

        return ResponseEntity.ok(response);

    }

    //@PreAuthorize("hasAuthority('CAN_READ') && hasPermission(#projectId, 'CAN_MANAGE_TASKS')")
    @PostMapping("/{pid}/tasks")
    public ResponseEntity<UUID> addProjectTask(
            @RequestHeader(required = false) UUID employeeId,
            @RequestHeader(required = false) UUID projectId,
            @RequestBody ProjectTaskCreatePayloadDTO payload,
            @PathVariable UUID pid
    ){
        var response = projectService.addProjectTask(projectId, payload);

        return ResponseEntity.ok(response);
    }

    //@PreAuthorize("hasAuthority('CAN_READ') && hasPermission(#projectId, 'CAN_READ')")
    @GetMapping("/{pid}/tasks")
    public ResponseEntity<ProjectTasksDetailsDTO> getProjectTasks(
            @RequestHeader(required = false) UUID employeeId,
            @RequestHeader(required = false) UUID projectId,
            @RequestParam(value="page", required = false) Integer page,
            @RequestParam(value="size", required = false) Integer size,
            @RequestParam(value="direction", required = false) String direction,
            @RequestParam(value="key", required = false) String key,
            @RequestParam(value="active", required = false, defaultValue = "true") Boolean active,
            @PathVariable UUID pid
    ){
        var settings = initPaging(page, size, key, direction);

        var response = projectService.getAdvancedProjectTasks(projectId, active, settings);

        return ResponseEntity.ok(response);

    }

    //@PreAuthorize("hasAuthority('CAN_READ') && hasPermission(#projectId, 'CAN_MANAGE_PROJECT_USERS')")
    @GetMapping("/{pid}/employees/{id}")
    public ResponseEntity<ProjectEmployeeDTO> getProjectEmployee(
            @RequestHeader(required = false) UUID employeeId,
            @RequestHeader(required = false) UUID projectId,
            @PathVariable UUID id,
            @PathVariable UUID pid
    ){
        var response = projectService.getProjectEmployee(projectId, id);

        return ResponseEntity.ok(response);
    }

    //@PreAuthorize("hasAuthority('CAN_READ') && hasPermission(#projectId, 'CAN_MANAGE_PROJECT_USERS')")
    @PostMapping("/{pid}/employees")
    public ResponseEntity<UUID> addProjectEmployee(
            @RequestHeader(required = false) UUID employeeId,
            @RequestHeader(required = false) UUID projectId,
            @RequestBody ProjectEmployeeCreatePayloadDTO payload,
            @PathVariable UUID pid
    ){
        var response = projectService.addProjectEmployee(projectId, payload);

        return ResponseEntity.ok(response);
    }

    //@PreAuthorize("hasAuthority('CAN_READ') && hasPermission(#projectId, 'CAN_MANAGE_PROJECT_USERS')")
    @PutMapping("/{pid}/employees/{id}")
    public ResponseEntity<UUID> updateProjectEmployee(
            @RequestHeader(required = false) UUID employeeId,
            @RequestHeader(required = false) UUID projectId,
            @RequestBody ProjectEmployeeUpdatePayloadDTO payload,
            @PathVariable UUID pid,
            @PathVariable UUID id
    ){
        var response = projectService.updateProjectEmployee(projectId, payload);

        return ResponseEntity.ok(response);
    }

    //@PreAuthorize("hasAuthority('CAN_READ') && hasPermission(#projectId, 'CAN_MANAGE_PROJECT_USERS')")
    @GetMapping("/{pid}/employees")
    public ResponseEntity<ProjectEmployeesDTO> getProjectEmployees(
            @RequestHeader(required = false) UUID employeeId,
            @RequestHeader(required = false) UUID projectId,
            @RequestParam(value="page", required = false) Integer page,
            @RequestParam(value="size", required = false) Integer size,
            @RequestParam(value="direction", required = false) String direction,
            @RequestParam(value="key", required = false) String key,
            @RequestParam(value="active", required = false, defaultValue = "true") Boolean active,
            @PathVariable UUID pid
    ){
        var settings = initPaging(page, size, direction, key);

        var response = projectService.getProjectEmployees(projectId, active, settings);

        return ResponseEntity.ok(response);
    }

    //@PreAuthorize("hasAuthority('CAN_READ') && hasPermission(#projectId, 'CAN_MANAGE_PROJECT_USERS')")
    @GetMapping("/{pid}/approvals")
    public ResponseEntity<ProjectApprovalsDTO> getProjectApprovals(
            @RequestHeader(required = false) UUID employeeId,
            @RequestHeader(required = false) UUID projectId,
            @RequestParam(value="page", required = false) Integer page,
            @RequestParam(value="size", required = false) Integer size,
            @RequestParam(value="direction", required = false) String direction,
            @RequestParam(value="key", required = false) String key,
            @PathVariable UUID pid
    ){
        var settings = initPaging(page, size, direction, key);

        var response = projectService.getProjectApprovalEmployees(projectId, settings);

        return ResponseEntity.ok(response);
    }

    //@PreAuthorize("hasAuthority('CAN_ADMIN_PROJECTS')")
    @PostMapping
    public ResponseEntity<UUID> addProject(
            @RequestHeader(required = false) UUID employeeId,
            @RequestHeader(required = false) UUID projectId,
            @RequestBody ProjectCreatePayloadDTO payload
    ){
        var response = projectService.addProject(payload);

        return ResponseEntity.ok(response);
    }

    //@PreAuthorize("hasPermission(#projectId, 'CAN_READ_PROJECT')")
    @PutMapping("/{pid}")
    public ResponseEntity<UUID> updateProject(
            @RequestHeader(required = false) UUID employeeId,
            @RequestHeader(required = false) UUID projectId,
            @RequestBody ProjectUpdatePayloadDTO payload,
            @PathVariable UUID pid
    ){
        var response = projectService.updateProject(payload);

        return ResponseEntity.ok(response);
    }
}
