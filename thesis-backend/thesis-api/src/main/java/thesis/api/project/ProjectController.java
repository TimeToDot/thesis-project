package thesis.api.project;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import thesis.api.ThesisController;
import thesis.api.project.model.employee.ProjectEmployeeResponse;
import thesis.api.project.model.employee.ProjectEmployeeUpdatePayload;
import thesis.api.project.model.employee.ProjectEmployeesResponse;
import thesis.api.project.model.task.ProjectTaskCreatePayload;
import thesis.api.project.model.task.ProjectTaskResponse;
import thesis.api.project.model.task.ProjectTaskUpdatePayload;
import thesis.api.project.model.task.ProjectTasksResponse;
import thesis.domain.paging.PagingSettings;

import javax.validation.constraints.NotNull;
import java.util.UUID;

@RestController
@RequestMapping("/api/project")
public class ProjectController extends ThesisController {

    @GetMapping("/task/{taskId}")
    public ResponseEntity<ProjectTaskResponse> getProjectTask(
            @RequestHeader @NotNull UUID employeeId,
            @RequestHeader @NotNull UUID projectId,
            @PathVariable UUID taskId){

        // TODO: 28/12/2022
        return null;

    }

    @PutMapping("/task")
    public ResponseEntity<ProjectTaskResponse> updateProjectTask(
            @RequestHeader @NotNull UUID employeeId,
            @RequestHeader @NotNull UUID projectId,
            @RequestBody ProjectTaskUpdatePayload payload){

        // TODO: 28/12/2022
        return null;

    }

    @PostMapping("/task")
    public ResponseEntity<ProjectTaskResponse> addProjectTask(
            @RequestHeader @NotNull UUID employeeId,
            @RequestHeader @NotNull UUID projectId,
            @RequestBody ProjectTaskCreatePayload payload
            ){

        // TODO: 28/12/2022
        return null;
    }

    @GetMapping("/tasks")
    public ResponseEntity<ProjectTasksResponse> getProjectTasks(
            @RequestHeader @NotNull UUID employeeId,
            @RequestHeader @NotNull UUID projectId,
            @RequestParam(value="active", required = false, defaultValue = "true") Boolean active){

        // TODO: 28/12/2022
        return null;

    }

    @GetMapping("/employee")
    public ResponseEntity<ProjectEmployeeResponse> getProjectEmployee(
            @RequestHeader @NotNull UUID employeeId,
            @RequestHeader @NotNull UUID projectId
    ){
        // TODO: 28/12/2022
        return null;
    }

    @PostMapping("/employee")
    public ResponseEntity<ProjectEmployeeResponse> addProjectEmployee(
            @RequestHeader @NotNull UUID employeeId,
            @RequestHeader @NotNull UUID projectId,
            @RequestBody ProjectEmployeeUpdatePayload payload
    ){
        // TODO: 28/12/2022 stworzyc payload
        return null;
    }

    @PutMapping("/employee")
    public ResponseEntity<ProjectEmployeeResponse> updateProjectEmployee(
            @RequestHeader @NotNull UUID employeeId,
            @RequestHeader @NotNull UUID projectId,
            @RequestBody ProjectEmployeeUpdatePayload payload
    ){
        // TODO: 28/12/2022 stworzyc payload
        return null;
    }

    @GetMapping("/employees")
    public ResponseEntity<ProjectEmployeesResponse> getProjectEmployees(
            @RequestHeader @NotNull UUID employeeId,
            @RequestHeader @NotNull UUID projectId,
            @RequestBody PagingSettings settings,
            @RequestParam(value="active", required = false, defaultValue = "true") Boolean active
            ){
        // TODO: 28/12/2022
        return null;
    }

    @GetMapping("/approvals")
    public ResponseEntity<ProjectEmployeesResponse> getProjectApprovals(
            @RequestHeader @NotNull UUID employeeId,
            @RequestHeader @NotNull UUID projectId,
            @RequestBody PagingSettings settings,
            @RequestParam(value="active", required = false, defaultValue = "true") Boolean active
    ){
        // TODO: 28/12/2022
        return null;
    }

}
