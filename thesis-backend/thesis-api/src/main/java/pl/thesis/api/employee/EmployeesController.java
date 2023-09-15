package pl.thesis.api.employee;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import pl.thesis.api.ThesisController;
import pl.thesis.api.auth.mapper.AuthMapper;
import pl.thesis.api.converter.IdConverter;
import pl.thesis.api.employee.mapper.*;
import pl.thesis.api.employee.model.EmployeePasswordPayload;
import pl.thesis.api.employee.model.EmployeeResponse;
import pl.thesis.api.employee.model.EmployeeUpdatePayload;
import pl.thesis.api.converter.model.ThesisId;
import pl.thesis.api.employee.model.calendar.CalendarTask;
import pl.thesis.api.employee.model.project.EmployeeProjectsToApproveResponse;
import pl.thesis.api.employee.model.project.temp.EmployeeProjectTempResponse;
import pl.thesis.api.employee.model.task.EmployeeTaskUpdatePayload;
import pl.thesis.api.employee.model.task.temp.EmployeeTaskTemp;
import pl.thesis.api.employee.model.temp.AuthorizationTemp;
import pl.thesis.api.employee.model.temp.EmployeeProjectsApproveTempPayload;
import pl.thesis.domain.employee.EmployeeCalendarService;
import pl.thesis.domain.employee.EmployeeProjectsService;
import pl.thesis.domain.employee.EmployeeService;
import pl.thesis.domain.employee.EmployeeTasksService;
import pl.thesis.security.services.AuthService;

import java.text.ParseException;
import java.util.Date;
import java.util.List;

@Slf4j
@RestController
@AllArgsConstructor
@RequestMapping("/api/employees")
public class EmployeesController extends ThesisController {

    private final EmployeeService employeeService;
    private final EmployeeCalendarService employeeCalendarService;
    private final EmployeeProjectsService employeeProjectsService;
    private final EmployeeTasksService employeeTasksService;
    private final EmployeesMapper employeesMapper;
    private final EmployeeMapper employeeMapper;
    private final EmployeeTasksMapper employeeTasksMapper;
    private final EmployeeTaskMapper employeeTaskMapper;
    private final EmployeeProjectMapper employeeProjectMapper;
    private final EmployeeProjectsMapper employeeProjectsMapper;
    private final EmployeeTaskCreatePayloadMapper employeeTaskCreatePayloadMapper;
    private final EmployeeTaskUpdatePayloadMapper employeeTaskUpdatePayloadMapper;
    private final CalendarMapper calendarMapper;
    private final AuthService authService;
    private final AuthMapper authMapper;
    private final IdConverter converter;

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

    @GetMapping("/{employeeId}")
    @PreAuthorize("hasAuthority('CAN_READ')")
    public ResponseEntity<EmployeeResponse> getEmployee(
            @PathVariable ThesisId employeeId
    ) {
        var employeeDTO = employeeService.getEmployee(employeeId.id());
        var employee = employeeMapper.map(employeeDTO);

        return ResponseEntity.ok(employee);
    }

    @PutMapping("/{employeeId}")
    @PreAuthorize("hasAuthority('CAN_ADMIN_USERS')")
    public ResponseEntity<Long> updateEmployeeById(
            @RequestBody EmployeeUpdatePayload payload,
            @PathVariable ThesisId employeeId
    ) throws ParseException {
        var dto = employeeMapper.mapToEmployeeUpdatePayloadDto(payload, employeeId);
        var response = employeeService.updateEmployee(dto);

        return ResponseEntity.ok(response);
    }

    @PutMapping("/{employeeId}/password")
    @PreAuthorize("hasAuthority('CAN_READ')")
    public ResponseEntity<Long> updatePasswordEmployee(
            @RequestBody EmployeePasswordPayload payload,
            @PathVariable ThesisId employeeId
    ) {
        var dto = employeeMapper.mapToPasswordUpdatePayloadDto(payload, employeeId);
        var response = employeeService.updateEmployeePassword(dto);

        return ResponseEntity.ok(response);
    }

    @GetMapping("/{employeeId}/projects")
    @PreAuthorize("hasAuthority('CAN_READ')")
    public ResponseEntity<List<EmployeeProjectTempResponse>> getEmployeeProjects(
            @RequestParam(value="page", required = false) Integer page,
            @RequestParam(value="size", required = false) Integer size,
            @RequestParam(value="direction", required = false) String direction,
            @RequestParam(value="key", required = false) String key,
            @PathVariable ThesisId employeeId
    ) {
        var settings = initPaging(page, size, key, direction);

        log.debug("controller: employeeId: {}, page: {}, size: {}", employeeId.id(), settings.getPage(), settings.getSize());

        var employeeProjectsDTO = employeeProjectsService.getEmployeeProjects(employeeId.id(), settings);
        var employeeProjectsResponse = employeeProjectsMapper.mapTemp(employeeProjectsDTO);

        return ResponseEntity.ok(employeeProjectsResponse.projects());
    }

    @GetMapping("/{employeeId}/approve")
    @PreAuthorize("hasAuthority('CAN_READ')")
    public ResponseEntity<EmployeeProjectsToApproveResponse> getProjectsToApprove(
            @RequestParam(required = false) @DateTimeFormat(pattern = "yyyy-MM-dd") Date startDate,
            @RequestParam(required = false) @DateTimeFormat(pattern = "yyyy-MM-dd") Date endDate,
            @PathVariable ThesisId employeeId
    ) {
        startDate = checkDate(startDate, Destiny.APPROVE_START);
        endDate = checkDate(endDate, Destiny.APPROVE_END);

        var toApproveDto = employeeProjectsService.getEmployeeProjectsToApprove(employeeId.id(), startDate, endDate);

        return ResponseEntity.ok(employeeProjectsMapper.toApproveMap(toApproveDto));
    }

    @PostMapping("/{employeeId}/toApprove")
    @PreAuthorize("hasAuthority('CAN_READ')")
    public ResponseEntity<List<String>> sendProjectsToApprove(
            @RequestBody EmployeeProjectsApproveTempPayload payload,
            @RequestParam(required = false) @DateTimeFormat(pattern = "yyyy-MM-dd") Date startDate,
            @RequestParam(required = false) @DateTimeFormat(pattern = "yyyy-MM-dd") Date endDate,
            @PathVariable ThesisId employeeId
    ) {
        startDate = checkDate(startDate, Destiny.APPROVE_START);
        endDate = checkDate(endDate, Destiny.APPROVE_END);

        var dto = employeeProjectMapper.mapToEmployeeProjectsApproveTemp(payload, employeeId);

        employeeProjectsService.sendProjectsToApprove(dto, startDate, endDate);

        return ResponseEntity.ok(payload.projects());
    }

    @PreAuthorize("hasAuthority('CAN_READ')")
    @GetMapping("/{employeeId}/tasks")
    public ResponseEntity<List<EmployeeTaskTemp>> getEmployeeTasks(
            @RequestParam(required = false) @DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm") Date startDate,
            @RequestParam(required = false) @DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm") Date endDate,
            @RequestParam(value="page", required = false) Integer page,
            @RequestParam(value="size", required = false) Integer size,
            @RequestParam(value="direction", required = false) String direction,
            @RequestParam(value="key", required = false) String key,
            @PathVariable ThesisId employeeId,
            @RequestParam @DateTimeFormat(pattern = "yyyy-MM-dd") Date date
    ) {
        var settings = initPaging(page, size, key, direction);

        date = checkDate(date, Destiny.TASKS_START);
        endDate = checkDate(date, Destiny.TASKS_END);

        var tasksDto = employeeTasksService.getEmployeeTasks(employeeId.id(), date, endDate, settings);
        var tasksResponse = employeeTasksMapper.mapTemp(tasksDto);

        return ResponseEntity.ok(tasksResponse.tasks());

    }


    @PreAuthorize("hasAuthority('CAN_READ') && hasPermission(#projectId, 'CAN_MANAGE_TASKS')")
    @GetMapping("/{employeeId}/tasks/{taskId}")
    public ResponseEntity<EmployeeTaskTemp> getEmployeeTask(
            @PathVariable ThesisId taskId,
            @PathVariable ThesisId employeeId
    ) {
        var taskDto = employeeTasksService.getTask(employeeId.id(), taskId.id());
        var taskResponse = employeeTaskMapper.mapTemp(taskDto);

        return ResponseEntity.ok(taskResponse);
    }

    @PreAuthorize("hasAuthority('CAN_READ')")
    @PostMapping("/{employeeId}/tasks")
    public ResponseEntity<String> addTask(
            @RequestBody EmployeeTaskTemp payload,
            @PathVariable ThesisId employeeId
    ) {
        var employeeTaskCreateDto = employeeTaskCreatePayloadMapper.mapTemp(payload);
        var response = converter.mapToText(employeeTasksService.createTask(employeeId.id(), employeeTaskCreateDto));

        return ResponseEntity.ok(response);
    }

    @PutMapping("/{employeeId}/tasks/{taskId}")
    public ResponseEntity<Long> updateTask(
            @RequestBody EmployeeTaskTemp payload,
            @PathVariable ThesisId employeeId,
            @PathVariable ThesisId taskId
    ) {
        var employeeTaskUpdateDTO = employeeTaskUpdatePayloadMapper.mapTemp(payload, employeeId);
        var response = employeeTasksService.updateTask(employeeTaskUpdateDTO);

        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/{employeeId}/tasks/{taskId}")
    public ResponseEntity<String> deleteTask(
            @PathVariable ThesisId employeeId,
            @PathVariable ThesisId taskId
    ) {
        employeeTasksService.deleteTask(employeeId.id(), taskId.id());

        return ResponseEntity.ok(taskId.id().toString());
    }

    @PreAuthorize("hasAuthority('CAN_READ') && hasPermission(#payload.projectId(), 'CAN_MANAGE_TASKS')")
    @PutMapping("/{employeeId}/task")
    public ResponseEntity<Long> updateEmployeeTask(
            @RequestBody EmployeeTaskUpdatePayload payload,
            @PathVariable ThesisId employeeId
    ) {
        var employeeTaskUpdateDTO = employeeTaskUpdatePayloadMapper.map(payload, employeeId);
        var response = employeeTasksService.updateTask(employeeTaskUpdateDTO);

        return ResponseEntity.ok(response);
    }


    @PreAuthorize("hasAuthority('CAN_READ')")
    @GetMapping("/{employeeId}/calendar")
    public ResponseEntity<List<CalendarTask>> getCalendar(
            @RequestParam(required = false) @DateTimeFormat(pattern="MM-yyyy") Date date,
            @PathVariable ThesisId employeeId

    ) throws ParseException {
        date = checkDate(date, Destiny.TASKS_START);

        var calendarDTO = employeeCalendarService.getCalendar(employeeId.id(), date);
        var calendarResponse = calendarMapper.map(calendarDTO);

        return ResponseEntity.ok(calendarResponse.tasks());
    }

    @PreAuthorize("hasAuthority('CAN_CREATE_USERS')")
    @PostMapping
    public ResponseEntity<Long> addEmployee(
            @RequestBody AuthorizationTemp authorizationPayload
    ) {

        if (Boolean.TRUE.equals(authService.isAccountExist(authorizationPayload.email()))) {
            return ResponseEntity.badRequest().build();
        }

        var dto = authMapper.mapToAuthorizationDTO(authorizationPayload);

        var response = authService.addUser(dto);


        return ResponseEntity.ok(response);
    }

}
