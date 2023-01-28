package thesis.api.employee;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import thesis.api.ThesisController;
import thesis.api.auth.mapper.AuthMapper;
import thesis.api.employee.mapper.*;
import thesis.api.employee.model.EmployeeResponse;
import thesis.api.employee.model.calendar.CalendarTask;
import thesis.api.employee.model.project.EmployeeProjectsToApprovePayload;
import thesis.api.employee.model.project.EmployeeProjectsToApproveResponse;
import thesis.api.employee.model.project.temp.EmployeeProjectTempResponse;
import thesis.api.employee.model.project.temp.EmployeeProjectsApproveTemp;
import thesis.api.employee.model.task.EmployeeTaskResponse;
import thesis.api.employee.model.task.EmployeeTaskUpdatePayload;
import thesis.api.employee.model.task.temp.EmployeeTaskTemp;
import thesis.api.employee.model.temp.AuthorizationTemp;
import thesis.domain.employee.EmployeeService;
import thesis.domain.employee.model.EmployeeUpdatePayloadDTO;
import thesis.domain.employee.model.PasswordUpdatePayloadDTO;
import thesis.security.services.AuthService;
import thesis.security.services.model.UserDetailsDefault;

import java.text.ParseException;
import java.util.Date;
import java.util.List;
import java.util.UUID;

@Slf4j
@RestController
@AllArgsConstructor
@RequestMapping("/api/employees")
public class EmployeesController extends ThesisController {

    private final EmployeeService employeeService;
    private final EmployeesMapper employeesMapper;
    private final EmployeeMapper employeeMapper;
    private final EmployeeTasksMapper employeeTasksMapper;
    private final EmployeeTaskMapper employeeTaskMapper;
    private final EmployeeProjectsMapper employeeProjectsMapper;
    private final EmployeeTaskCreatePayloadMapper employeeTaskCreatePayloadMapper;
    private final EmployeeTaskUpdatePayloadMapper employeeTaskUpdatePayloadMapper;
    private final EmployeeTaskDeletePayloadMapper employeeTaskDeletePayloadMapper;
    private final CalendarMapper calendarMapper;

    private final AuthService authService;
    private final AuthMapper authMapper;

    //@PreAuthorize("hasAuthority('CAN_READ')")
    @GetMapping
    public ResponseEntity<List<EmployeeResponse>> getEmployees(
            @RequestHeader(required = false) UUID employeeId,
            @RequestHeader(required = false) UUID projectId,
            @RequestParam(value="active", required = false, defaultValue = "true") Boolean active,
            @RequestParam(value="page", required = false) Integer page,
            @RequestParam(value="size", required = false) Integer size,
            @RequestParam(value="direction", required = false) String direction,
            @RequestParam(value="key", required = false) String key
    ) {
        var settings = initPaging(1, 100, key, direction);

        var dto = employeeService.getEmployees(settings, active);
        var response = employeesMapper.map(dto);

        return ResponseEntity.ok(response.employees());
    }

    @GetMapping("/{id}")
    //@PreAuthorize("hasAuthority('CAN_READ')")
    public ResponseEntity<EmployeeResponse> getEmployee(
            @RequestHeader(required = false) UUID employeeId,
            @RequestHeader(required = false) UUID projectId,
            @PathVariable UUID id
    ) {

        var employeeDTO = employeeService.getEmployee(id);
        var employee = employeeMapper.map(employeeDTO);

        return ResponseEntity.ok(employee);
    }

/*    @GetMapping("/{id}")
    //@PreAuthorize("hasPermission(#projectId, 'CAN_MANAGE_PROJECT_USERS') || hasAuthority('CAN_ADMIN_USERS')")
    public ResponseEntity<EmployeeResponse> getEmployeeByProject(
            @RequestHeader(required = false) UUID employeeId,
            @RequestHeader(required = false) UUID projectId,
            @PathVariable UUID id
    ) {

        var employeeDTO = employeeService.getEmployee(id);
        var employee = employeeMapper.map(employeeDTO);

        return ResponseEntity.ok(employee);
    }*/

    @PutMapping
    //@PreAuthorize("hasAuthority('CAN_READ')")
    public ResponseEntity<UUID> updateEmployee(
            @RequestHeader(required = false) UUID employeeId,
            @RequestHeader(required = false) UUID projectId,
            @RequestBody EmployeeUpdatePayloadDTO payload
    ) throws ParseException {
        var response = employeeService.updateEmployee(employeeId, payload);

        return ResponseEntity.ok(response);
    }

    @PutMapping("/{id}")
    //@PreAuthorize("hasAuthority('CAN_ADMIN_USERS')")
    public ResponseEntity<UUID> updateEmployeeById(
            @RequestHeader(required = false) UUID employeeId,
            @RequestHeader(required = false) UUID projectId,
            @RequestBody EmployeeUpdatePayloadDTO payload,
            @PathVariable UUID id
    ) throws ParseException {
        var response = employeeService.updateEmployee(id, payload);

        return ResponseEntity.ok(response);
    }

    @PutMapping("/password")
    //@PreAuthorize("hasAuthority('CAN_READ')")
    public ResponseEntity<UUID> updatePasswordEmployee(
            @RequestHeader(required = false) UUID employeeId,
            @RequestHeader(required = false) UUID projectId,
            @RequestBody PasswordUpdatePayloadDTO payload
    ) {
        var response = employeeService.updateEmployeePassword(employeeId, payload);

        return ResponseEntity.ok(response);
    }

    @GetMapping("/{id}/projects")
    //@PreAuthorize("hasAuthority('CAN_READ')")
    public ResponseEntity<List<EmployeeProjectTempResponse>> getEmployeeProjects(
            @RequestHeader(required = false) UUID employeeId,
            @RequestHeader(required = false) UUID projectId,
            @RequestParam(value="page", required = false) Integer page,
            @RequestParam(value="size", required = false) Integer size,
            @RequestParam(value="direction", required = false) String direction,
            @RequestParam(value="key", required = false) String key,
            @PathVariable UUID id
    ) {
        var settings = initPaging(page, size, key, direction);

        log.info("controller: employeeId: {}, page: {}, size: {}", id, settings.getPage(), settings.getSize());

        var employeeProjectsDTO = employeeService.getEmployeeProjects(id, settings);
        var employeeProjectsResponse = employeeProjectsMapper.mapTemp(employeeProjectsDTO);

        return ResponseEntity.ok(employeeProjectsResponse.projects());
    }

    @GetMapping("/{id}/approve")
    //@PreAuthorize("hasAuthority('CAN_READ')")
    public ResponseEntity<EmployeeProjectsToApproveResponse> getProjectsToApprove(
            @RequestHeader(required = false) UUID employeeId,
            @RequestHeader(required = false) UUID projectId,
            @RequestParam(required = false) @DateTimeFormat(pattern = "yyyy-MM-dd") Date startDate,
            @RequestParam(required = false) @DateTimeFormat(pattern = "yyyy-MM-dd") Date endDate,
            @PathVariable UUID id
    ) {
        startDate = checkDate(startDate, Destiny.APPROVE_START);
        endDate = checkDate(endDate, Destiny.APPROVE_END);

        var toApproveDto = employeeService.getEmployeeProjectsToApprove(id, startDate, endDate);

        return ResponseEntity.ok(employeeProjectsMapper.toApproveMap(toApproveDto));
    }

    @PostMapping("/{id}/toApprove")
    //@PreAuthorize("hasAuthority('CAN_READ')")
    public ResponseEntity<List<UUID>> sendProjectsToApprove(
            @RequestHeader(required = false) UUID employeeId,
            @RequestHeader(required = false) UUID projectId,
            @RequestBody EmployeeProjectsApproveTemp payload,
            @RequestParam(required = false) @DateTimeFormat(pattern = "yyyy-MM-dd") Date startDate,
            @RequestParam(required = false) @DateTimeFormat(pattern = "yyyy-MM-dd") Date endDate,
            @PathVariable UUID id
    ) {
        startDate = checkDate(startDate, Destiny.APPROVE_START);
        endDate = checkDate(endDate, Destiny.APPROVE_END);

        employeeService.sendProjectsToApprove(id, payload.projects(), startDate, endDate);

        return ResponseEntity.ok(payload.projects());
    }

    //@PreAuthorize("hasAuthority('CAN_READ')")
    @GetMapping("/{id}/tasks")
    public ResponseEntity<List<EmployeeTaskTemp>> getEmployeeTasks(
            @RequestHeader(required = false) UUID employeeId,
            @RequestHeader(required = false) UUID projectId,
            @RequestParam(required = false) @DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm") Date startDate,
            @RequestParam(required = false) @DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm") Date endDate,
            @RequestParam(value="page", required = false) Integer page,
            @RequestParam(value="size", required = false) Integer size,
            @RequestParam(value="direction", required = false) String direction,
            @RequestParam(value="key", required = false) String key,
            @PathVariable UUID id,
            @RequestParam @DateTimeFormat(pattern = "yyyy-MM-dd") Date date
    ) {
        var settings = initPaging(page, size, key, direction);

        date = checkDate(date, Destiny.TASKS_START);
        endDate = checkDate(date, Destiny.TASKS_END);

        var tasksDto = employeeService.getEmployeeTasks(id, date, endDate, settings);
        var tasksResponse = employeeTasksMapper.mapTemp(tasksDto);

        return ResponseEntity.ok(tasksResponse.tasks());
    }

    //@PreAuthorize("hasAuthority('CAN_READ')")
    @GetMapping("/task/{taskId}")
    public ResponseEntity<EmployeeTaskResponse> getTask(
            @RequestHeader(required = false) UUID employeeId,
            @RequestHeader(required = false) UUID projectId,
            @PathVariable UUID taskId) {

        var taskDto = employeeService.getTask(employeeId, taskId);
        var taskResponse = employeeTaskMapper.map(taskDto);

        return ResponseEntity.ok(taskResponse);
    }

    //@PreAuthorize("hasAuthority('CAN_READ') && hasPermission(#projectId, 'CAN_MANAGE_TASKS')")
    @GetMapping("/{id}/tasks/{taskId}")
    public ResponseEntity<EmployeeTaskTemp> getEmployeeTask(
            @RequestHeader(required = false) UUID employeeId,
            @RequestHeader(required = false) UUID projectId,
            @PathVariable UUID taskId,
            @PathVariable UUID id) {

        var taskDto = employeeService.getTask(id, taskId);
        var taskResponse = employeeTaskMapper.mapTemp(taskDto);

        return ResponseEntity.ok(taskResponse);
    }

    //@PreAuthorize("hasAuthority('CAN_READ')")
    @PostMapping("/{id}/tasks")
    public ResponseEntity<UUID> addTask(
            @RequestHeader(required = false) UUID employeeId,
            @RequestHeader(required = false) UUID projectId,
            @RequestBody EmployeeTaskTemp payload,
            @PathVariable UUID id
    ) {
        var employeeTaskCreateDto = employeeTaskCreatePayloadMapper.mapTemp(payload);
        var response = employeeService.createTask(id, employeeTaskCreateDto);

        return ResponseEntity.ok(response);
    }

    @PutMapping("/{eid}/tasks/{tid}")
    public ResponseEntity<UUID> updateTask(
            @RequestHeader(required = false) UUID employeeId,
            @RequestHeader(required = false) UUID projectId,
            @RequestBody EmployeeTaskTemp payload,
            @PathVariable UUID eid,
            @PathVariable UUID tid
    ) {
        var employeeTaskUpdateDTO = employeeTaskUpdatePayloadMapper.mapTemp(payload);
        var response = employeeService.updateTask(eid, employeeTaskUpdateDTO);

        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/{eid}/tasks/{tid}")
    public ResponseEntity<UUID> deleteTask(
            @RequestHeader(required = false) UUID employeeId,
            @RequestHeader(required = false) UUID projectId,
            @RequestBody EmployeeTaskTemp payload,
            @PathVariable UUID eid,
            @PathVariable UUID tid
    ) {
        var employeeTaskDeleteDTO = employeeTaskDeletePayloadMapper.mapTemp(payload);

        employeeService.deleteTask(eid, employeeTaskDeleteDTO);

        return ResponseEntity.ok(tid);
    }

    //@PreAuthorize("hasAuthority('CAN_READ') && hasPermission(#payload.projectId(), 'CAN_MANAGE_TASKS')")
    @PutMapping("/{id}/task")
    public ResponseEntity<UUID> updateEmployeeTask(
            @RequestHeader(required = false) UUID employeeId,
            @RequestHeader UUID projectId,
            @RequestBody EmployeeTaskUpdatePayload payload,
            @PathVariable UUID id
    ) {
        var employeeTaskUpdateDTO = employeeTaskUpdatePayloadMapper.map(payload);
        var response = employeeService.updateTask(id, employeeTaskUpdateDTO);

        return ResponseEntity.ok(response);
    }


    //@PreAuthorize("hasAuthority('CAN_READ')")
    @GetMapping("/{id}/calendar")
    public ResponseEntity<List<CalendarTask>> getCalendar(
            @RequestHeader(required = false) UUID employeeId,
            @RequestHeader(required = false) UUID projectId,
            @RequestParam(required = false) @DateTimeFormat(pattern="MM-yyyy") Date date,
            @PathVariable UUID id) throws ParseException {

        if (date == null){
            date = employeeService.getMonth(new Date());
        }

        var calendarDTO = employeeService.getCalendar(id, date);
        var calendarResponse = calendarMapper.map(calendarDTO);

        return ResponseEntity.ok(calendarResponse.tasks());
    }


    //@PreAuthorize("hasAuthority('CAN_READ') && hasPermission(#projectId, 'CAN_MANAGE_TASKS')")
/*    @GetMapping("/{id}/calendar")
    public ResponseEntity<EmployeeCalendarResponse> getEmployeeCalendar(
            @RequestHeader(required = false) UUID employeeId,
            @RequestHeader(required = false) @NotNull UUID projectId,
            @RequestParam @DateTimeFormat(pattern="MM-yyyy") Date date,
            @PathVariable UUID id) {

        var calendarDTO = employeeService.getEmployeeCalendar(id, projectId, date);
        var calendarResponse = calendarMapper.map(calendarDTO);

        return ResponseEntity.ok(calendarResponse);
    }*/

    //@PreAuthorize("hasAuthority('CAN_CREATE_USERS')")
    @PostMapping
    public ResponseEntity<UUID> addEmployee(
            @RequestHeader(required = false) UUID employeeId,
            @RequestBody AuthorizationTemp authorizationPayload
    ) {

        if (Boolean.TRUE.equals(authService.isAccountExist(authorizationPayload.email()))) {
            return ResponseEntity.badRequest().build();
        }

        var dto = authMapper.mapToAuthorizationDTO(authorizationPayload);

        var response = authService.addUser(dto);


        return ResponseEntity.ok(response);
    }


    private boolean verifyEmployeeId(UUID id) {

        UserDetailsDefault principal = (UserDetailsDefault) SecurityContextHolder
                .getContext()
                .getAuthentication()
                .getPrincipal();

        return id.equals(principal.getId());
    }

}
