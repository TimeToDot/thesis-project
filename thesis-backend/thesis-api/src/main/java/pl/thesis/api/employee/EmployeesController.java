package pl.thesis.api.employee;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import pl.thesis.api.ThesisController;
import pl.thesis.api.auth.mapper.AuthMapper;
import pl.thesis.api.converter.UuidConverter;
import pl.thesis.api.employee.mapper.*;
import pl.thesis.api.employee.model.EmployeePasswordPayload;
import pl.thesis.api.employee.model.EmployeeResponse;
import pl.thesis.api.employee.model.EmployeeUpdatePayload;
import pl.thesis.api.employee.model.calendar.CalendarTask;
import pl.thesis.api.employee.model.project.EmployeeProjectsToApproveResponse;
import pl.thesis.api.employee.model.project.temp.EmployeeProjectTempResponse;
import pl.thesis.api.employee.model.task.EmployeeTaskUpdatePayload;
import pl.thesis.api.employee.model.task.temp.EmployeeTaskTemp;
import pl.thesis.api.employee.model.temp.AuthorizationTemp;
import pl.thesis.api.employee.model.temp.EmployeeProjectsApproveTempPayload;
import pl.thesis.domain.employee.EmployeeService;
import pl.thesis.security.services.AuthService;
import pl.thesis.security.services.model.UserDetailsDefault;

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
    private final EmployeeProjectMapper employeeProjectMapper;
    private final EmployeeProjectsMapper employeeProjectsMapper;
    private final EmployeeTaskCreatePayloadMapper employeeTaskCreatePayloadMapper;
    private final EmployeeTaskUpdatePayloadMapper employeeTaskUpdatePayloadMapper;
    private final EmployeeTaskDeletePayloadMapper employeeTaskDeletePayloadMapper;
    private final CalendarMapper calendarMapper;
    private final AuthService authService;
    private final AuthMapper authMapper;
    private final UuidConverter converter;

    @PreAuthorize("hasAuthority('CAN_READ')")
    @GetMapping
    public ResponseEntity<List<EmployeeResponse>> getEmployees(
            @RequestParam(value="active", required = false, defaultValue = "true") Boolean active,
            @RequestParam(value="page", required = false) Integer page,
            @RequestParam(value="size", required = false) Integer size,
            @RequestParam(value="direction", required = false) String direction,
            @RequestParam(value="key", required = false) String key
    ) {
        var settings = initPaging(page, size, key, direction);

        var dto = employeeService.getEmployees(settings, active);
        var response = employeesMapper.map(dto);

        return ResponseEntity.ok(response.employees());
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasAuthority('CAN_READ')")
    public ResponseEntity<EmployeeResponse> getEmployee(
            @PathVariable String id
    ) {

        var employeeId = converter.mapToId(id);
        var employeeDTO = employeeService.getEmployee(employeeId);
        var employee = employeeMapper.map(employeeDTO);

        return ResponseEntity.ok(employee);
    }

    @PutMapping("/{id}")
    //@PreAuthorize("hasAuthority('CAN_ADMIN_USERS')")
    public ResponseEntity<UUID> updateEmployeeById(
            @RequestBody EmployeeUpdatePayload payload,
            @PathVariable String id
    ) throws ParseException {
        var dto = employeeMapper.mapToEmployeeUpdatePayloadDto(payload, id);
        var response = employeeService.updateEmployee(dto);

        return ResponseEntity.ok(response);
    }

    @PutMapping("/{id}/password")
    //@PreAuthorize("hasAuthority('CAN_READ')")
    public ResponseEntity<UUID> updatePasswordEmployee(
            @RequestBody EmployeePasswordPayload payload,
            @PathVariable String id
    ) {
        var dto = employeeMapper.mapToPasswordUpdatePayloadDto(payload, id);
        var response = employeeService.updateEmployeePassword(dto);

        return ResponseEntity.ok(response);
    }

    @GetMapping("/{id}/projects")
    //@PreAuthorize("hasAuthority('CAN_READ')")
    public ResponseEntity<List<EmployeeProjectTempResponse>> getEmployeeProjects(
            @RequestParam(value="page", required = false) Integer page,
            @RequestParam(value="size", required = false) Integer size,
            @RequestParam(value="direction", required = false) String direction,
            @RequestParam(value="key", required = false) String key,
            @PathVariable String id
    ) {
        var settings = initPaging(page, size, key, direction);

        log.info("controller: employeeId: {}, page: {}, size: {}", id, settings.getPage(), settings.getSize());

        var projectEmployeeId = converter.mapToId(id);
        var employeeProjectsDTO = employeeService.getEmployeeProjects(projectEmployeeId, settings);
        var employeeProjectsResponse = employeeProjectsMapper.mapTemp(employeeProjectsDTO);

        return ResponseEntity.ok(employeeProjectsResponse.projects());
    }

    @GetMapping("/{id}/approve")
    //@PreAuthorize("hasAuthority('CAN_READ')")
    public ResponseEntity<EmployeeProjectsToApproveResponse> getProjectsToApprove(
            @RequestParam(required = false) @DateTimeFormat(pattern = "yyyy-MM-dd") Date startDate,
            @RequestParam(required = false) @DateTimeFormat(pattern = "yyyy-MM-dd") Date endDate,
            @PathVariable String id
    ) {
        startDate = checkDate(startDate, Destiny.APPROVE_START);
        endDate = checkDate(endDate, Destiny.APPROVE_END);

        var employeeId = converter.mapToId(id);
        var toApproveDto = employeeService.getEmployeeProjectsToApprove(employeeId, startDate, endDate);

        return ResponseEntity.ok(employeeProjectsMapper.toApproveMap(toApproveDto));
    }

    @PostMapping("/{id}/toApprove")
    //@PreAuthorize("hasAuthority('CAN_READ')")
    public ResponseEntity<List<String>> sendProjectsToApprove(
            @RequestBody EmployeeProjectsApproveTempPayload payload,
            @RequestParam(required = false) @DateTimeFormat(pattern = "yyyy-MM-dd") Date startDate,
            @RequestParam(required = false) @DateTimeFormat(pattern = "yyyy-MM-dd") Date endDate,
            @PathVariable String id
    ) {
        startDate = checkDate(startDate, Destiny.APPROVE_START);
        endDate = checkDate(endDate, Destiny.APPROVE_END);

        var dto = employeeProjectMapper.mapToEmployeeProjectsApproveTemp(payload, id);

        employeeService.sendProjectsToApprove(dto, startDate, endDate);

        return ResponseEntity.ok(payload.projects());
    }

    //@PreAuthorize("hasAuthority('CAN_READ')")
    @GetMapping("/{id}/tasks")
    public ResponseEntity<List<EmployeeTaskTemp>> getEmployeeTasks(
            @RequestParam(required = false) @DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm") Date startDate,
            @RequestParam(required = false) @DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm") Date endDate,
            @RequestParam(value="page", required = false) Integer page,
            @RequestParam(value="size", required = false) Integer size,
            @RequestParam(value="direction", required = false) String direction,
            @RequestParam(value="key", required = false) String key,
            @PathVariable String id,
            @RequestParam @DateTimeFormat(pattern = "yyyy-MM-dd") Date date
    ) {
        var settings = initPaging(page, size, key, direction);

        date = checkDate(date, Destiny.TASKS_START);
        endDate = checkDate(date, Destiny.TASKS_END);


            var employeeId = converter.mapToId(id);
            var tasksDto = employeeService.getEmployeeTasks(employeeId, date, endDate, settings);
            var tasksResponse = employeeTasksMapper.mapTemp(tasksDto);


            return ResponseEntity.ok(tasksResponse.tasks());

    }


    //@PreAuthorize("hasAuthority('CAN_READ') && hasPermission(#projectId, 'CAN_MANAGE_TASKS')")
    @GetMapping("/{id}/tasks/{taskId}")
    public ResponseEntity<EmployeeTaskTemp> getEmployeeTask(
            @PathVariable String taskId,
            @PathVariable String id) {

        var employeeId = converter.mapToId(id);
        var eTaskId = converter.mapToId(taskId);
        var taskDto = employeeService.getTask(employeeId, eTaskId);
        var taskResponse = employeeTaskMapper.mapTemp(taskDto);

        return ResponseEntity.ok(taskResponse);
    }

    //@PreAuthorize("hasAuthority('CAN_READ')")
    @PostMapping("/{id}/tasks")
    public ResponseEntity<String> addTask(
            @RequestBody EmployeeTaskTemp payload,
            @PathVariable String id
    ) {
        var employeeTaskCreateDto = employeeTaskCreatePayloadMapper.mapTemp(payload);
        var employeeId = converter.mapToId(id);
        var response = converter.mapToText(employeeService.createTask(employeeId, employeeTaskCreateDto));

        return ResponseEntity.ok(response);
    }

    @PutMapping("/{eid}/tasks/{tid}")
    public ResponseEntity<UUID> updateTask(
            @RequestBody EmployeeTaskTemp payload,
            @PathVariable String eid,
            @PathVariable String tid
    ) {
        var employeeTaskUpdateDTO = employeeTaskUpdatePayloadMapper.mapTemp(payload, eid);
        var response = employeeService.updateTask(employeeTaskUpdateDTO);

        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/{eid}/tasks/{tid}")
    public ResponseEntity<String> deleteTask(
            @PathVariable String eid,
            @PathVariable String tid
    ) {
        var employeeId = converter.mapToId(eid);
        var taskId = converter.mapToId(tid);

        employeeService.deleteTask(employeeId, taskId);

        return ResponseEntity.ok(tid);
    }

    //@PreAuthorize("hasAuthority('CAN_READ') && hasPermission(#payload.projectId(), 'CAN_MANAGE_TASKS')")
    @PutMapping("/{id}/task")
    public ResponseEntity<UUID> updateEmployeeTask(
            @RequestBody EmployeeTaskUpdatePayload payload,
            @PathVariable String id
    ) {
        var employeeTaskUpdateDTO = employeeTaskUpdatePayloadMapper.map(payload, id);
        var response = employeeService.updateTask(employeeTaskUpdateDTO);

        return ResponseEntity.ok(response);
    }


    //@PreAuthorize("hasAuthority('CAN_READ')")
    @GetMapping("/{id}/calendar")
    public ResponseEntity<List<CalendarTask>> getCalendar(
            @RequestParam(required = false) @DateTimeFormat(pattern="MM-yyyy") Date date,
            @PathVariable String id) throws ParseException {

        if (date == null){
            date = employeeService.getMonth(new Date());
        }
        var employeeId = converter.mapToId(id);
        var calendarDTO = employeeService.getCalendar(employeeId, date);
        var calendarResponse = calendarMapper.map(calendarDTO);

        return ResponseEntity.ok(calendarResponse.tasks());
    }

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
