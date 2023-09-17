package pl.thesis.api.project;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import pl.thesis.api.ThesisController;
import pl.thesis.api.employee.mapper.CalendarMapper;
import pl.thesis.api.employee.mapper.EmployeeTasksMapper;
import pl.thesis.api.employee.model.calendar.CalendarTask;
import pl.thesis.api.employee.model.task.temp.EmployeeTaskTemp;
import pl.thesis.api.project.mapper.ProjectMapper;
import pl.thesis.api.project.model.ProjectCreatePayload;
import pl.thesis.api.project.model.ProjectResponse;
import pl.thesis.api.project.model.ProjectUpdatePayload;
import pl.thesis.api.project.model.approval.ProjectApprovalResponse;
import pl.thesis.api.project.model.approval.ProjectCalendarResponse;
import pl.thesis.api.project.model.employee.ProjectEmployeeCreatePayload;
import pl.thesis.api.project.model.employee.ProjectEmployeeResponse;
import pl.thesis.api.project.model.employee.ProjectEmployeeUpdatePayload;
import pl.thesis.api.project.model.employee.ProjectEmployeesResponse;
import pl.thesis.api.project.model.task.ProjectTaskCreatePayload;
import pl.thesis.api.project.model.task.ProjectTaskDetails;
import pl.thesis.api.project.model.task.ProjectTaskUpdatePayload;
import pl.thesis.domain.project.ProjectEmployeeService;
import pl.thesis.domain.project.ProjectService;
import pl.thesis.domain.project.ProjectTaskService;
import pl.thesis.security.services.model.ThesisId;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Slf4j
@AllArgsConstructor
@RestController
@RequestMapping("/api/projects")
public class ProjectsController extends ThesisController {

    private final ProjectService projectService;
    private final ProjectEmployeeService projectEmployeeService;
    private final ProjectTaskService projectTaskService;
    private final CalendarMapper calendarMapper;
    private final EmployeeTasksMapper employeeTasksMapper;
    private final ProjectMapper projectMapper;

    @PreAuthorize("hasAuthority('CAN_READ')")
    @GetMapping
    public ResponseEntity<List<ProjectResponse>> getProjects(
            @RequestParam(value="active", required = false, defaultValue = "true") Boolean active
    ){
        var dto = projectService.getProjects(active);
        var response = projectMapper.mapToProjectsResponse(dto);

        return ResponseEntity.ok(response.projects());
    }

    @PreAuthorize("hasAuthority('CAN_READ_PROJECT')")
    @GetMapping("/{projectId}")
    public ResponseEntity<ProjectResponse> getProject(
            @RequestParam(value="active", required = false, defaultValue = "true") Boolean active,
            @PathVariable ThesisId projectId
    ){
        var dto = projectService.getProject(projectId.id());
        var response = projectMapper.mapToProjectResponse(dto);

        return ResponseEntity.ok(response);
    }

    @PreAuthorize("hasAuthority('CAN_READ') && hasPermission(#projectId, 'CAN_READ_PROJECT')")
    @GetMapping("/{projectId}/tasks/{taskId}")
    public ResponseEntity<ProjectTaskDetails> getProjectTask(
            @PathVariable ThesisId taskId,
            @PathVariable ThesisId projectId
    ){
        var dto = projectTaskService.getProjectTask(projectId.id(), taskId.id());
        var response = projectMapper.mapToProjectTaskDetailsResponse(dto);

        return ResponseEntity.ok(response);
    }

    @PreAuthorize("hasAuthority('CAN_READ') && hasPermission(#projectId, 'CAN_MANAGE_TASKS')")
    @PutMapping("/{projectId}/tasks/{taskId}")
    public ResponseEntity<Long> updateProjectTask(
            @RequestBody ProjectTaskUpdatePayload payload,
            @PathVariable ThesisId projectId,
            @PathVariable ThesisId taskId
    ){
        var dto = projectMapper.mapToProjectTaskUpdatePayloadDTO(payload, projectId.id());
        var response = projectTaskService.updateProjectTask(dto);

        return ResponseEntity.ok().build();

    }

    @PreAuthorize("hasAuthority('CAN_READ') && hasPermission(#projectId, 'CAN_MANAGE_TASKS')")
    @PostMapping("/{projectId}/tasks")
    public ResponseEntity<Long> addProjectTask(
            @RequestBody ProjectTaskCreatePayload payload,
            @PathVariable ThesisId projectId
    ){
        var dto = projectMapper.mapToProjectTaskCreatePayloadDTO(payload, projectId.id());
        var response = projectTaskService.addProjectTask(dto);

        return ResponseEntity.ok().build();
    }

    @PreAuthorize("hasAuthority('CAN_READ') && hasPermission(#projectId, 'CAN_READ')")
    @GetMapping("/{projectId}/tasks")
    public ResponseEntity<List<ProjectTaskDetails>> getProjectTasks(
            @RequestParam(value="page", required = false) Integer page,
            @RequestParam(value="size", required = false) Integer size,
            @RequestParam(value="direction", required = false) String direction,
            @RequestParam(value="key", required = false) String key,
            @RequestParam(value="active", required = false, defaultValue = "true") Boolean active,
            @PathVariable ThesisId projectId
    ){
        var settings = initPaging(page, size, key, direction);
        var dto = projectTaskService.getAdvancedProjectTasks(projectId.id(), active, settings);
        var response = projectMapper.mapToProjectTasksDetailsResponse(dto);

        return ResponseEntity.ok(response.tasks());

    }

    @PreAuthorize("hasAuthority('CAN_READ') && hasPermission(#projectId, 'CAN_MANAGE_PROJECT_USERS')")
    @GetMapping("/{projectId}/employees/{projectEmployeeId}")
    public ResponseEntity<ProjectEmployeeResponse> getProjectEmployee(
            @PathVariable ThesisId projectEmployeeId,
            @PathVariable ThesisId projectId
    ){
        var dto = projectEmployeeService.getProjectEmployee(projectId.id(), projectEmployeeId.id());
        var response = projectMapper.mapToProjectEmployeeResponse(dto);

        return ResponseEntity.ok(response);
    }

    @PreAuthorize("hasAuthority('CAN_READ') && hasPermission(#projectId, 'CAN_MANAGE_PROJECT_USERS')")
    @PostMapping("/{projectId}/employees")
    public ResponseEntity<Long> addProjectEmployee(
            @RequestBody ProjectEmployeeCreatePayload payload,
            @PathVariable ThesisId projectId
    ){
        var dto = projectMapper.mapToProjectEmployeeCreatePayloadDTO(payload, projectId.id());
        var response = projectEmployeeService.addProjectEmployee(dto);

        return ResponseEntity.ok(response);
    }

    @PreAuthorize("hasAuthority('CAN_READ') && hasPermission(#projectId, 'CAN_MANAGE_PROJECT_USERS')")
    @PutMapping("/{projectId}/employees/{id}")
    public ResponseEntity<Long> updateProjectEmployee(
            @RequestBody ProjectEmployeeUpdatePayload payload,
            @PathVariable ThesisId projectId,
            @PathVariable ThesisId employeeId
    ){
        var dto = projectMapper.mapToProjectEmployeeUpdatePayloadDTO(payload, projectId.id(), employeeId.id());
        var response = projectEmployeeService.updateProjectEmployee(dto);

        return ResponseEntity.ok(response);
    }

    @PreAuthorize("hasAuthority('CAN_READ') && hasPermission(#projectId, 'CAN_MANAGE_PROJECT_USERS')")
    @GetMapping("/{projectId}/employees")
    public ResponseEntity<ProjectEmployeesResponse> getProjectEmployees(
            @RequestParam(value="page", required = false) Integer page,
            @RequestParam(value="size", required = false) Integer size,
            @RequestParam(value="direction", required = false) String direction,
            @RequestParam(value="key", required = false) String key,
            @RequestParam(value="active", required = false, defaultValue = "true") Boolean active,
            @PathVariable ThesisId projectId
    ){
        var settings = initPaging(page, size, direction, key);
        var dto = projectEmployeeService.getProjectEmployees(projectId.id(), active, settings);
        var response = projectMapper.mapToProjectEmployeesResponse(dto);

        return ResponseEntity.ok(response);
    }

    @PreAuthorize("hasAuthority('CAN_READ') && hasPermission(#projectId, 'CAN_MANAGE_PROJECT_USERS')")
    @GetMapping("/{projectId}/approvals")
    public ResponseEntity<List<ProjectApprovalResponse>> getProjectApprovals(
            @RequestParam(value="page", required = false) Integer page,
            @RequestParam(value="size", required = false) Integer size,
            @RequestParam(value="direction", required = false) String direction,
            @RequestParam(value="key", required = false) String key,
            @PathVariable ThesisId projectId
    ){
        var settings = initPaging(page, size, direction, key);

        var dto = projectEmployeeService.getProjectApprovalEmployees(projectId.id(), settings);
        var response = projectMapper.mapToProjectApprovalsResponse(dto);

        return ResponseEntity.ok(response.employees());
    }

    @PreAuthorize("hasAuthority('CAN_READ') && hasPermission(#projectId, 'CAN_MANAGE_PROJECT_USERS')")
    @PostMapping("/{projectId}/approvals/{employeeId}")
    public ResponseEntity<String> approveProjectTasks(
            @RequestBody(required = false) List<String> tasks,
            @PathVariable ThesisId projectId,
            @PathVariable ThesisId employeeId
    ){
        var settings = initPaging();

        if (tasks == null) {
            tasks = new ArrayList<>();
        }
        var dto = projectMapper.mapToProjectApprovalTasksPayloadDTO(tasks, projectId.id(), employeeId.id());
        projectEmployeeService.setProjectApprovalsEmployee(dto, settings);

        return ResponseEntity.ok(employeeId.id().toString());
    }

    @PreAuthorize("hasAuthority('CAN_READ') && hasPermission(#projectId, 'CAN_MANAGE_TASKS')")
    @GetMapping("/{projectId}/approvals/{employeeId}")
    public ResponseEntity<ProjectCalendarResponse> getProjectEmployeeApproval(
            @RequestParam(required = false) @DateTimeFormat(pattern="MM-yyyy") Date date,
            @PathVariable ThesisId projectId,
            @PathVariable ThesisId employeeId
    ) {
        date = checkDate(date, Destiny.TASKS_START);
        var dto = projectService.getProjectCalendar(employeeId.id(), projectId.id(), date);
        var response = projectMapper.mapToProjectCalendarResponse(dto);

        return ResponseEntity.ok(response);
    }

    @PreAuthorize("hasAuthority('CAN_ADMIN_PROJECTS')")
    @PostMapping
    public ResponseEntity<Long> addProject(
            @RequestBody ProjectCreatePayload payload
    ){
        var dto = projectMapper.mapToProjectCreatePayloadDTO(payload);
        var response = projectService.addProject(dto);

        return ResponseEntity.ok(response);
    }

    @PreAuthorize("hasPermission(#projectId, 'CAN_MANAGE_PROJECT_USERS')")
    @PutMapping("/{projectId}")
    public ResponseEntity<Long> updateProject(
            @RequestBody ProjectUpdatePayload payload,
            @PathVariable ThesisId projectId
    ){

        var payloadDto = projectMapper.mapToUpdatePayloadDto(payload, projectId.id());
        var response = projectService.updateProject(payloadDto);

        return ResponseEntity.ok(response);
    }

    @PreAuthorize("hasAuthority('CAN_READ') && hasPermission(#projectId, 'CAN_MANAGE_TASKS')")
    @GetMapping("/{projectId}/employees/{projectEmployeeId}/calendar")
    public ResponseEntity<List<CalendarTask>> getProjectEmployeeCalendar(
            @RequestParam(required = false) @DateTimeFormat(pattern="MM-yyyy") Date date,
            @PathVariable ThesisId projectId,
            @PathVariable ThesisId projectEmployeeId
    ) {
        date = checkDate(date, Destiny.TASKS_START);

        var calendarDTO = projectEmployeeService.getProjectEmployeeCalendar(projectEmployeeId.id(), projectId.id(), date);
        var calendarResponse = calendarMapper.map(calendarDTO);

        return ResponseEntity.ok(calendarResponse.tasks());
    }

    @PreAuthorize("hasAuthority('CAN_READ') && hasPermission(#projectId, 'CAN_MANAGE_TASKS')")
    @GetMapping("/{projectId}/employees/{projectEmployeeId}/tasks")
    public ResponseEntity<List<EmployeeTaskTemp>> getProjectEmployeeTasks(
            @RequestParam(value="page", required = false) Integer page,
            @RequestParam(value="size", required = false) Integer size,
            @RequestParam(value="direction", required = false) String direction,
            @RequestParam(value="key", required = false) String key,
            @PathVariable ThesisId projectEmployeeId,
            @PathVariable ThesisId projectId,
            @RequestParam @DateTimeFormat(pattern = "yyyy-MM-dd") Date date
    ) {
        var settings = initPaging(page, size, key, direction);

        date = checkDate(date, Destiny.TASKS_START);
        var endOfDay = checkDate(date, Destiny.TASKS_END);

        var tasksDto = projectEmployeeService.getProjectEmployeeTasks(projectEmployeeId.id(), projectId.id(), date, endOfDay, settings);
        var tasksResponse = employeeTasksMapper.mapTemp(tasksDto);

        return ResponseEntity.ok(tasksResponse.tasks());
    }
}
