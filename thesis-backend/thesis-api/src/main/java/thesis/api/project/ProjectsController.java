package thesis.api.project;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import thesis.api.ThesisController;
import thesis.domain.project.ProjectService;
import thesis.domain.project.model.ProjectsDTO;

import java.util.UUID;

@Slf4j
@AllArgsConstructor
@RestController
@RequestMapping("/api/projects")
public class ProjectsController extends ThesisController {

    private final ProjectService projectService;
    @PreAuthorize("hasAuthority('CAN_ADMIN_PROJECTS')")
    @GetMapping()
    public ResponseEntity<ProjectsDTO> getProjects(
            @RequestHeader UUID employeeId,
            @RequestHeader(required = false) UUID projectId,
            @RequestParam(value="active", required = false, defaultValue = "true") Boolean active
    ){

        var response = projectService.getProjects(active);
        return ResponseEntity.ok(response);

    }
}
