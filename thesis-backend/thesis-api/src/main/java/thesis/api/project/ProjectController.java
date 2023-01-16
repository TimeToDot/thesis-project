package thesis.api.project;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import thesis.api.ThesisController;
import thesis.domain.project.ProjectService;
import thesis.domain.project.model.employee.ProjectEmployeeDTO;
import thesis.domain.project.model.employee.ProjectEmployeeUpdatePayloadDTO;
import thesis.domain.project.model.employee.ProjectEmployeesDTO;
import thesis.domain.project.model.task.*;
import thesis.domain.paging.PagingSettings;

import javax.validation.constraints.NotNull;
import java.util.UUID;

@Slf4j
@AllArgsConstructor
@RestController
@RequestMapping("/api/project")
public class ProjectController extends ThesisController {

    private final ProjectService projectService;

    @PreAuthorize("hasAuthority('CAN_READ') && hasPermission(#projectId, 'CAR_READ')")
    @GetMapping("/task/{taskId}")
    public ResponseEntity<ProjectTaskDetailsDTO> getProjectTask(
            @RequestHeader @NotNull UUID employeeId,
            @RequestHeader @NotNull UUID projectId,
            @PathVariable UUID taskId){

        var response = projectService.getProjectTask(projectId, taskId);
        return ResponseEntity.ok(response);

    }

    @PreAuthorize("hasAuthority('CAN_READ') && hasPermission(#projectId, 'CAN_MANAGE_TASKS')")
    @PutMapping("/task")
    public ResponseEntity<ProjectTaskDetailsDTO> updateProjectTask(
            @RequestHeader @NotNull UUID employeeId,
            @RequestHeader @NotNull UUID projectId,
            @RequestBody ProjectTaskUpdatePayloadDTO payload){

        // TODO: 28/12/2022
        return null;

    }

    @PreAuthorize("hasAuthority('CAN_READ') && hasPermission(#projectId, 'CAN_MANAGE_TASKS')")
    @PostMapping("/task")
    public ResponseEntity<ProjectTaskDetailsDTO> addProjectTask(
            @RequestHeader @NotNull UUID employeeId,
            @RequestHeader @NotNull UUID projectId,
            @RequestBody ProjectTaskCreatePayloadDTO payload
            ){

        // TODO: 28/12/2022
        return null;
    }

    @PreAuthorize("hasAuthority('CAN_READ') && hasPermission(#projectId, 'CAN_READ')")
    @GetMapping("/tasks")
    public ResponseEntity<ProjectTasksDetailsDTO> getProjectTasks(
            @RequestHeader @NotNull UUID employeeId,
            @RequestHeader @NotNull UUID projectId,
            @RequestParam(value="page", required = false) Integer page,
            @RequestParam(value="size", required = false) Integer size,
            @RequestParam(value="direction", required = false) String direction,
            @RequestParam(value="key", required = false) String key,
            @RequestParam(value="active", required = false, defaultValue = "true") Boolean active){
        var settings = initPagingSettings(page, size, key, direction);
        var response = projectService.getAdvancedProjectTasks(projectId, active, settings);
        return ResponseEntity.ok(response);

    }

    @PreAuthorize("hasAuthority('CAN_READ') && hasPermission(#projectId, 'CAN_READ')")
    @GetMapping("{id}/tasks")
    public ResponseEntity<ProjectTasksDTO> getProjectTasksByEmployee(
            @RequestHeader @NotNull UUID employeeId,
            @RequestHeader @NotNull UUID projectId,
            @PathVariable UUID id,
            @RequestParam(value="active", required = false, defaultValue = "true") Boolean active){

        var response = projectService.getProjectTasks(id, active);
        return ResponseEntity.ok(response);

    }

    @PreAuthorize("hasAuthority('CAN_READ') && hasPermission(#projectId, 'CAN_MANAGE_PROJECT_USERS')")
    @GetMapping("/employee")
    public ResponseEntity<ProjectEmployeeDTO> getProjectEmployee(
            @RequestHeader @NotNull UUID employeeId,
            @RequestHeader @NotNull UUID projectId
    ){
        // TODO: 28/12/2022
        return null;
    }

    @PreAuthorize("hasAuthority('CAN_READ') && hasPermission(#projectId, 'CAN_MANAGE_PROJECT_USERS')")
    @PostMapping("/employee")
    public ResponseEntity<ProjectEmployeeDTO> addProjectEmployee(
            @RequestHeader @NotNull UUID employeeId,
            @RequestHeader @NotNull UUID projectId,
            @RequestBody ProjectEmployeeUpdatePayloadDTO payload
    ){
        // TODO: 28/12/2022 stworzyc payload
        return null;
    }

    @PreAuthorize("hasAuthority('CAN_READ') && hasPermission(#projectId, 'CAN_MANAGE_PROJECT_USERS')")
    @PutMapping("/employee")
    public ResponseEntity<ProjectEmployeeDTO> updateProjectEmployee(
            @RequestHeader @NotNull UUID employeeId,
            @RequestHeader @NotNull UUID projectId,
            @RequestBody ProjectEmployeeUpdatePayloadDTO payload
    ){
        // TODO: 28/12/2022 stworzyc payload
        return null;
    }

    @PreAuthorize("hasAuthority('CAN_READ') && hasPermission(#projectId, 'CAN_MANAGE_PROJECT_USERS')")
    @GetMapping("/employees")
    public ResponseEntity<ProjectEmployeesDTO> getProjectEmployees(
            @RequestHeader @NotNull UUID employeeId,
            @RequestHeader @NotNull UUID projectId,
            @RequestParam(value="page", required = false) Integer page,
            @RequestParam(value="size", required = false) Integer size,
            @RequestParam(value="direction", required = false) String direction,
            @RequestParam(value="key", required = false) String key,
            @RequestParam(value="active", required = false, defaultValue = "true") Boolean active
            ){
        var settings = initPagingSettings(page, size, direction, key);
        // TODO: 28/12/2022
        return null;
    }

    @PreAuthorize("hasAuthority('CAN_READ') && hasPermission(#projectId, 'CAN_MANAGE_PROJECT_USERS')")
    @GetMapping("/approvals")
    public ResponseEntity<ProjectEmployeesDTO> getProjectApprovals(
            @RequestHeader @NotNull UUID employeeId,
            @RequestHeader @NotNull UUID projectId,
            @RequestParam(value="page", required = false) Integer page,
            @RequestParam(value="size", required = false) Integer size,
            @RequestParam(value="direction", required = false) String direction,
            @RequestParam(value="key", required = false) String key,
            @RequestParam(value="active", required = false, defaultValue = "true") Boolean active
    ){
        var settings = initPagingSettings(page, size, direction, key);
        // TODO: 28/12/2022
        return null;
    }

}
