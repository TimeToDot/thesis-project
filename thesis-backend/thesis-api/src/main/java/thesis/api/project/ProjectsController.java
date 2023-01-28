package thesis.api.project;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import thesis.api.ThesisController;
import thesis.api.employee.mapper.CalendarMapper;
import thesis.api.employee.mapper.EmployeeTasksMapper;
import thesis.api.employee.model.calendar.CalendarTask;
import thesis.api.employee.model.task.temp.EmployeeTaskTemp;
import thesis.domain.employee.EmployeeService;
import thesis.domain.project.ProjectService;
import thesis.domain.project.model.ProjectCreatePayloadDTO;
import thesis.domain.project.model.ProjectDTO;
import thesis.domain.project.model.ProjectUpdatePayloadDTO;
import thesis.domain.project.model.approval.ProjectApprovalDTO;
import thesis.domain.project.model.approval.ProjectCalendarDTO;
import thesis.domain.project.model.employee.ProjectEmployeeCreatePayloadDTO;
import thesis.domain.project.model.employee.ProjectEmployeeDTO;
import thesis.domain.project.model.employee.ProjectEmployeeUpdatePayloadDTO;
import thesis.domain.project.model.employee.ProjectEmployeesDTO;
import thesis.domain.project.model.task.ProjectTaskCreatePayloadDTO;
import thesis.domain.project.model.task.ProjectTaskDetailsDTO;
import thesis.domain.project.model.task.ProjectTaskUpdatePayloadDTO;

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
    //@PreAuthorize("hasAuthority('CAN_ADMIN_PROJECTS')")
    @GetMapping
    public ResponseEntity<List<ProjectDTO>> getProjects(
            @RequestHeader(required = false)  UUID employeeId,
            @RequestHeader(required = false)  UUID projectId,
            @RequestParam(value="active", required = false, defaultValue = "true") Boolean active
    ){
        var response = projectService.getProjects(active);

        return ResponseEntity.ok(response.projects());
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
        var response = projectService.getProjectTask(pid, taskId);

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
        var response = projectService.updateProjectTask(pid, payload);

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
        var response = projectService.addProjectTask(pid, payload);

        return ResponseEntity.ok(response);
    }

    //@PreAuthorize("hasAuthority('CAN_READ') && hasPermission(#projectId, 'CAN_READ')")
    @GetMapping("/{pid}/tasks")
    public ResponseEntity<List<ProjectTaskDetailsDTO>> getProjectTasks(
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

        var response = projectService.getAdvancedProjectTasks(pid, active, settings);

        return ResponseEntity.ok(response.tasks());

    }

    //@PreAuthorize("hasAuthority('CAN_READ') && hasPermission(#projectId, 'CAN_MANAGE_PROJECT_USERS')")
    @GetMapping("/{pid}/employees/{id}")
    public ResponseEntity<ProjectEmployeeDTO> getProjectEmployee(
            @RequestHeader(required = false) UUID employeeId,
            @RequestHeader(required = false) UUID projectId,
            @PathVariable UUID id,
            @PathVariable UUID pid
    ){
        var response = projectService.getProjectEmployee(pid, id);

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
        var response = projectService.addProjectEmployee(pid, payload);

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
        var response = projectService.updateProjectEmployee(pid, id, payload);

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

        var response = projectService.getProjectEmployees(pid, active, settings);

        return ResponseEntity.ok(response);
    }

    //@PreAuthorize("hasAuthority('CAN_READ') && hasPermission(#projectId, 'CAN_MANAGE_PROJECT_USERS')")
    @GetMapping("/{pid}/approvals")
    public ResponseEntity<List<ProjectApprovalDTO>> getProjectApprovals(
            @RequestHeader(required = false) UUID employeeId,
            @RequestHeader(required = false) UUID projectId,
            @RequestParam(value="page", required = false) Integer page,
            @RequestParam(value="size", required = false) Integer size,
            @RequestParam(value="direction", required = false) String direction,
            @RequestParam(value="key", required = false) String key,
            @PathVariable UUID pid
    ){
        var settings = initPaging(page, size, direction, key);

        var response = projectService.getProjectApprovalEmployees(pid, settings);

        return ResponseEntity.ok(response.employees());
    }

    @PostMapping("/{pid}/approvals/{id}")
    public ResponseEntity<UUID> approveProjectTasks(
            @RequestHeader(required = false) UUID employeeId,
            @RequestHeader(required = false) UUID projectId,
            @RequestBody(required = false) List<UUID> tasks,
            @PathVariable UUID pid,
            @PathVariable UUID id
    ){
        var settings = initPaging();

        if (tasks == null) {
            tasks = new ArrayList<>();
        }

        projectService.setProjectApprovalsEmployee(pid, id, tasks, settings);

        return ResponseEntity.ok(id);
    }

    //@PreAuthorize("hasAuthority('CAN_READ') && hasPermission(#projectId, 'CAN_MANAGE_TASKS')")
    @GetMapping("/{pid}/approvals/{id}")
    public ResponseEntity<ProjectCalendarDTO> getProjectEmployeeApproval(
            @RequestHeader(required = false) UUID employeeId,
            @RequestHeader(required = false) UUID projectId,
            //@RequestParam(required = false) @DateTimeFormat(pattern="MM-yyyy") Date date,
            @PathVariable UUID pid,
            @PathVariable UUID id) throws ParseException {
        var date = employeeService.getMonth(new Date());
        var eId = employeeService.getEmployeeIdByProjectEmployee(id);

        var calendarDTO = employeeService.getEmployeeCalendar(eId, pid, date);
        var calendarResponse = calendarMapper.map(calendarDTO);

        var response = ProjectCalendarDTO.builder()
                .employeeId(eId)
                .projectEmployeeId(id)
                .tasks(calendarDTO.tasks())
                .build();

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

        var response = projectService.updateProject(pid, payload);

        return ResponseEntity.ok(response);
    }

    //@PreAuthorize("hasAuthority('CAN_READ') && hasPermission(#projectId, 'CAN_MANAGE_TASKS')")
    @GetMapping("/{pid}/employees/{id}/calendar")
    public ResponseEntity<List<CalendarTask>> getProjectEmployeeCalendar(
            @RequestHeader(required = false) UUID employeeId,
            @RequestHeader(required = false) UUID projectId,
            @RequestParam(required = false) @DateTimeFormat(pattern="MM-yyyy") Date date,
            @PathVariable UUID pid,
            @PathVariable UUID id) throws ParseException {

        if (date == null){
            date = employeeService.getMonth(new Date());
        }

        var calendarDTO = projectService.getProjectEmployeeCalendar(id, pid, date);
        var calendarResponse = calendarMapper.map(calendarDTO);

        return ResponseEntity.ok(calendarResponse.tasks());
    }

    @GetMapping("/{pid}/employees/{id}/tasks")
    public ResponseEntity<List<EmployeeTaskTemp>> getProjectEmployeeTasks(
            @RequestHeader(required = false) UUID employeeId,
            @RequestHeader(required = false) UUID projectId,
            @RequestParam(required = false) @DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm") Date startDate,
            @RequestParam(required = false) @DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm") Date endDate,
            @RequestParam(value="page", required = false) Integer page,
            @RequestParam(value="size", required = false) Integer size,
            @RequestParam(value="direction", required = false) String direction,
            @RequestParam(value="key", required = false) String key,
            @PathVariable UUID id,
            @PathVariable UUID pid,
            @RequestParam @DateTimeFormat(pattern = "yyyy-MM-dd") Date date
    ) {
        var settings = initPaging(page, size, key, direction);

        date = checkDate(date, Destiny.TASKS_START);
        var endOfDay = checkDate(date, Destiny.TASKS_END);

        var tasksDto = projectService.getProjectEmployeeTasks(id, pid, date, endOfDay, settings);
        var tasksResponse = employeeTasksMapper.mapTemp(tasksDto);

        return ResponseEntity.ok(tasksResponse.tasks());
    }
}
