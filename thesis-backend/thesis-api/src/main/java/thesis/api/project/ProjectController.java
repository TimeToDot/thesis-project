package thesis.api.project;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import thesis.api.ThesisController;
import thesis.api.project.model.ProjectTaskCreatePayload;
import thesis.api.project.model.ProjectTaskResponse;
import thesis.api.project.model.ProjectTaskUpdatePayload;
import thesis.api.project.model.ProjectTasksResponse;

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

}
