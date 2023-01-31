package pl.thesis.api.project;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import pl.thesis.api.ThesisController;
import pl.thesis.api.converter.UuidConverter;
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
import pl.thesis.domain.employee.EmployeeService;
import pl.thesis.domain.project.ProjectService;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

@Slf4j
@AllArgsConstructor
@RestController
@RequestMapping("/api/projects")
public class ProjectsController extends ThesisController {

    private final ProjectService projectService;
    private final EmployeeService employeeService;
    private final CalendarMapper calendarMapper;
    private final EmployeeTasksMapper employeeTasksMapper;
    private final ProjectMapper projectMapper;
    private final UuidConverter converter;

    @PreAuthorize("hasAuthority('CAN_READ')")
    @GetMapping
    public ResponseEntity<List<ProjectResponse>> getProjects(
            @RequestParam(value="active", required = false, defaultValue = "true") Boolean active
    ){
        var dto = projectService.getProjects(active);
        var response = projectMapper.mapToProjectsResponse(dto);

        return ResponseEntity.ok(response.projects());
    }

    //@PreAuthorize("hasAuthority('CAN_ADMIN_PROJECTS')")
    @GetMapping("/{projectId}")
    public ResponseEntity<ProjectResponse> getProject(
            @RequestParam(value="active", required = false, defaultValue = "true") Boolean active,
            @PathVariable String projectId
    ){
        var pId = converter.mapToId(projectId);
        var dto = projectService.getProject(pId);
        var response = projectMapper.mapToProjectResponse(dto);

        return ResponseEntity.ok(response);
    }

    @PreAuthorize("hasAuthority('CAN_READ') && hasPermission(#projectId, 'CAN_READ_PROJECT')")
    @GetMapping("/{projectId}/tasks/{taskId}")
    public ResponseEntity<ProjectTaskDetails> getProjectTask(
            @PathVariable String taskId,
            @PathVariable String projectId
    ){
        var tId = converter.mapToId(taskId);
        var pId = converter.mapToId(projectId);
        var dto = projectService.getProjectTask(pId, tId);
        var response = projectMapper.mapToProjectTaskDetailsResponse(dto);

        return ResponseEntity.ok(response);
    }

    @PreAuthorize("hasAuthority('CAN_READ') && hasPermission(#projectId, 'CAN_MANAGE_TASKS')")
    @PutMapping("/{projectId}/tasks/{taskId}")
    public ResponseEntity<UUID> updateProjectTask(
            @RequestBody ProjectTaskUpdatePayload payload, //change
            @PathVariable String projectId,
            @PathVariable String taskId
    ){
        var tId = converter.mapToId(taskId);
        var pId = converter.mapToId(projectId);
        var dto = projectMapper.mapToProjectTaskUpdatePayloadDTO(payload, pId);
        var response = projectService.updateProjectTask(dto);

        return ResponseEntity.ok().build();

    }

    //@PreAuthorize("hasAuthority('CAN_READ') && hasPermission(#projectId, 'CAN_MANAGE_TASKS')")
    @PostMapping("/{projectId}/tasks")
    public ResponseEntity<UUID> addProjectTask(
            @RequestBody ProjectTaskCreatePayload payload,
            @PathVariable String projectId
    ){
        var pId = converter.mapToId(projectId);
        var dto = projectMapper.mapToProjectTaskCreatePayloadDTO(payload, pId);
        var response = projectService.addProjectTask(dto);

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
            @PathVariable String projectId
    ){
        var pId = converter.mapToId(projectId);
        var settings = initPaging(page, size, key, direction);
        var dto = projectService.getAdvancedProjectTasks(pId, active, settings);
        var response = projectMapper.mapToProjectTasksDetailsResponse(dto);

        return ResponseEntity.ok(response.tasks());

    }

    @PreAuthorize("hasAuthority('CAN_READ') && hasPermission(#projectId, 'CAN_MANAGE_PROJECT_USERS')")
    @GetMapping("/{projectId}/employees/{id}")
    public ResponseEntity<ProjectEmployeeResponse> getProjectEmployee(
            @PathVariable String id,
            @PathVariable String projectId
    ){
        var eId = converter.mapToId(id);
        var pId = converter.mapToId(projectId);
        var dto = projectService.getProjectEmployee(pId, eId);
        var response = projectMapper.mapToProjectEmployeeResponse(dto);

        return ResponseEntity.ok(response);
    }

    @PreAuthorize("hasAuthority('CAN_READ') && hasPermission(#projectId, 'CAN_MANAGE_PROJECT_USERS')")
    @PostMapping("/{projectId}/employees")
    public ResponseEntity<UUID> addProjectEmployee(
            @RequestBody ProjectEmployeeCreatePayload payload,
            @PathVariable String projectId
    ){
        var pId = converter.mapToId(projectId);
        var dto = projectMapper.mapToProjectEmployeeCreatePayloadDTO(payload, pId);
        var response = projectService.addProjectEmployee(dto);

        return ResponseEntity.ok(response);
    }

    @PreAuthorize("hasAuthority('CAN_READ') && hasPermission(#projectId, 'CAN_MANAGE_PROJECT_USERS')")
    @PutMapping("/{projectId}/employees/{id}")
    public ResponseEntity<UUID> updateProjectEmployee(
            @RequestBody ProjectEmployeeUpdatePayload payload,
            @PathVariable String projectId,
            @PathVariable String id
    ){
        var eId = converter.mapToId(id);
        var pId = converter.mapToId(projectId);
        var dto = projectMapper.mapToProjectEmployeeUpdatePayloadDTO(payload, pId, eId);
        var response = projectService.updateProjectEmployee(dto);

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
            @PathVariable String projectId
    ){
        var settings = initPaging(page, size, direction, key);
        var pId = converter.mapToId(projectId);
        var dto = projectService.getProjectEmployees(pId, active, settings);
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
            @PathVariable String projectId
    ){
        var settings = initPaging(page, size, direction, key);

        var pId = converter.mapToId(projectId);
        var dto = projectService.getProjectApprovalEmployees(pId, settings);
        var response = projectMapper.mapToProjectApprovalsResponse(dto);

        return ResponseEntity.ok(response.employees());
    }

    @PreAuthorize("hasAuthority('CAN_READ') && hasPermission(#projectId, 'CAN_MANAGE_PROJECT_USERS')")
    @PostMapping("/{projectId}/approvals/{id}")
    public ResponseEntity<String> approveProjectTasks(
            @RequestBody(required = false) List<String> tasks,
            @PathVariable String projectId,
            @PathVariable String id
    ){
        var settings = initPaging();

        if (tasks == null) {
            tasks = new ArrayList<>();
        }
        var dto = projectMapper.mapToProjectApprovalTasksPayloadDTO(tasks, projectId, id);
        projectService.setProjectApprovalsEmployee(dto, settings);

        return ResponseEntity.ok(id);
    }

    @PreAuthorize("hasAuthority('CAN_READ') && hasPermission(#projectId, 'CAN_MANAGE_TASKS')")
    @GetMapping("/{projectId}/approvals/{id}")
    public ResponseEntity<ProjectCalendarResponse> getProjectEmployeeApproval(
            @RequestParam(required = false) @DateTimeFormat(pattern="MM-yyyy") Date date,
            @PathVariable String projectId,
            @PathVariable String id
    ) throws ParseException {
        if (date == null){
            date = employeeService.getMonth(new Date());
        }
        var eId = converter.mapToId(id);
        var pId = converter.mapToId(projectId);
        var dto = projectService.getProjectCalendarDTO(eId, pId, date);
        var response = projectMapper.mapToProjectCalendarResponse(dto);

        return ResponseEntity.ok(response);
    }

    @PreAuthorize("hasAuthority('CAN_ADMIN_PROJECTS')")
    @PostMapping
    public ResponseEntity<UUID> addProject(
            @RequestBody ProjectCreatePayload payload
    ){
        var dto = projectMapper.mapToProjectCreatePayloadDTO(payload);
        var response = projectService.addProject(dto);

        return ResponseEntity.ok(response);
    }

    @PreAuthorize("hasPermission(#projectId, 'CAN_MANAGE_PROJECT_USERS')")
    @PutMapping("/{projectId}")
    public ResponseEntity<UUID> updateProject(
            @RequestBody ProjectUpdatePayload payload,
            @PathVariable String projectId
    ){

        var payloadDto = projectMapper.mapToUpdatePayloadDto(payload, projectId);
        var response = projectService.updateProject(payloadDto);

        return ResponseEntity.ok(response);
    }

    @PreAuthorize("hasAuthority('CAN_READ') && hasPermission(#projectId, 'CAN_MANAGE_TASKS')")
    @GetMapping("/{projectId}/employees/{id}/calendar")
    public ResponseEntity<List<CalendarTask>> getProjectEmployeeCalendar(
            @RequestParam(required = false) @DateTimeFormat(pattern="MM-yyyy") Date date,
            @PathVariable String projectId,
            @PathVariable String id
    ) throws ParseException {

        if (date == null){
            date = employeeService.getMonth(new Date());
        }

        var pId = converter.mapToId(projectId);
        var pEmployeeId = converter.mapToId(id);
        var calendarDTO = projectService.getProjectEmployeeCalendar(pEmployeeId, pId, date);
        var calendarResponse = calendarMapper.map(calendarDTO);

        return ResponseEntity.ok(calendarResponse.tasks());
    }

    @PreAuthorize("hasAuthority('CAN_READ') && hasPermission(#projectId, 'CAN_MANAGE_TASKS')")
    @GetMapping("/{projectId}/employees/{id}/tasks")
    public ResponseEntity<List<EmployeeTaskTemp>> getProjectEmployeeTasks(
            @RequestParam(value="page", required = false) Integer page,
            @RequestParam(value="size", required = false) Integer size,
            @RequestParam(value="direction", required = false) String direction,
            @RequestParam(value="key", required = false) String key,
            @PathVariable String id,
            @PathVariable String projectId,
            @RequestParam @DateTimeFormat(pattern = "yyyy-MM-dd") Date date
    ) {
        var settings = initPaging(page, size, key, direction);

        date = checkDate(date, Destiny.TASKS_START);
        var endOfDay = checkDate(date, Destiny.TASKS_END);

        var pId = converter.mapToId(projectId);
        var pEmployeeId = converter.mapToId(id);
        var tasksDto = projectService.getProjectEmployeeTasks(pEmployeeId, pId, date, endOfDay, settings);
        var tasksResponse = employeeTasksMapper.mapTemp(tasksDto);

        return ResponseEntity.ok(tasksResponse.tasks());
    }
}
