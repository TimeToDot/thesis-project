package thesis.api.employee;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import thesis.api.ThesisController;
import thesis.api.employee.mapper.*;
import thesis.api.employee.model.EmployeeResponse;
import thesis.api.employee.model.calendar.EmployeeCalendarResponse;
import thesis.api.employee.model.project.EmployeeProjectsResponse;
import thesis.api.employee.model.project.EmployeeProjectsToApprovePayload;
import thesis.api.employee.model.project.EmployeeProjectsToApproveRequest;
import thesis.api.employee.model.project.EmployeeProjectsToApproveResponse;
import thesis.api.employee.model.task.*;
import thesis.domain.employee.EmployeeService;
import thesis.domain.employee.model.BillingPeriodDTO;
import thesis.domain.employee.model.ContractTypeDTO;
import thesis.domain.employee.model.EmployeeUpdatePayloadDTO;
import thesis.domain.employee.model.PasswordUpdatePayloadDTO;
import thesis.security.services.model.UserDetailsDefault;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.util.*;
import java.util.stream.Collectors;

@Slf4j
@RestController
@AllArgsConstructor
@RequestMapping("/api/employee")
public class EmployeeController extends ThesisController {

    private final EmployeeService employeeService;

    private final EmployeeMapper employeeMapper;
    private final EmployeeTasksMapper employeeTasksMapper;
    private final EmployeeTaskMapper employeeTaskMapper;
    private final EmployeeProjectsMapper employeeProjectsMapper;
    private final EmployeeTaskCreatePayloadMapper employeeTaskCreatePayloadMapper;
    private final EmployeeTaskUpdatePayloadMapper employeeTaskUpdatePayloadMapper;
    private final CalendarMapper calendarMapper;

    @Operation(summary = "Gets employee by ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "OK"),
            @ApiResponse(responseCode = "400", description = "Invalid ID supplied"),
            @ApiResponse(responseCode = "401", description = "Unauthorized"),
            @ApiResponse(responseCode = "403", description = "Access to the requested resource is forbidden"),
            @ApiResponse(responseCode = "404", description = "Customer not found"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    @GetMapping
    @PreAuthorize("hasAuthority('CAN_READ')")
    public ResponseEntity<EmployeeResponse> getEmployee(
            @RequestHeader UUID employeeId,
            @RequestHeader(required = false) UUID projectId
    ) {

        var employeeDTO = employeeService.getEmployee(employeeId);
        var employee = employeeMapper.map(employeeDTO);

        return ResponseEntity.ok(employee);
    }

    @GetMapping("{id}")
    @PreAuthorize("hasAuthority('CAN_READ') && hasPermission(#projectId, 'CAN_MANAGE_PROJECT_USERS')")
    public ResponseEntity<EmployeeResponse> getEmployeeByProject(
            @RequestHeader UUID employeeId,
            @RequestHeader(required = false) UUID projectId,
            @PathVariable UUID id
    ) {

        var employeeDTO = employeeService.getEmployee(id);
        var employee = employeeMapper.map(employeeDTO);

        return ResponseEntity.ok(employee);
    }

    @GetMapping("/contractTypes")
    @PreAuthorize("hasAuthority('CAN_READ')")
    public ResponseEntity<Map<Integer, String>> getContractTypes(
            @RequestHeader UUID employeeId,
            @RequestHeader(required = false) UUID projectId
    ) {
        var response = Arrays.stream(ContractTypeDTO.values())
                .collect(Collectors.toMap(Enum::ordinal, contractTypeDTO -> contractTypeDTO.label));

        return ResponseEntity.ok(response);
    }

    @GetMapping("/bellingPeriod")
    @PreAuthorize("hasAuthority('CAN_READ')")
    public ResponseEntity<Map<Integer, String>> getBillingPeriod(
            @RequestHeader UUID employeeId,
            @RequestHeader(required = false) UUID projectId
    ) {
        var response = Arrays.stream(BillingPeriodDTO.values())
                .collect(Collectors.toMap(Enum::ordinal, contractTypeDTO -> contractTypeDTO.label));

        return ResponseEntity.ok(response);
    }

    @PutMapping
    @PreAuthorize("hasAuthority('CAN_READ')")
    public ResponseEntity<UUID> updateEmployee(
            @RequestHeader UUID employeeId,
            @RequestHeader(required = false) UUID projectId,
            @RequestBody EmployeeUpdatePayloadDTO payload
    ) {
        var response = employeeService.updateEmployee(employeeId, payload);

        return ResponseEntity.ok(response);
    }

    @PutMapping("/password")
    @PreAuthorize("hasAuthority('CAN_READ')")
    public ResponseEntity<UUID> updatePasswordEmployee(
            @RequestHeader UUID employeeId,
            @RequestHeader(required = false) UUID projectId,
            @RequestBody PasswordUpdatePayloadDTO payload
    ) {
        var response = employeeService.updateEmployeePassword(employeeId, payload);

        return ResponseEntity.ok(response);
    }

    @Operation(summary = "Gets employee projects by ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "OK"),
            @ApiResponse(responseCode = "400", description = "Invalid ID supplied"),
            @ApiResponse(responseCode = "401", description = "Unauthorized"),
            @ApiResponse(responseCode = "403", description = "Access to the requested resource is forbidden"),
            @ApiResponse(responseCode = "404", description = "Customer not found"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    @GetMapping("/projects")
    @PreAuthorize("hasAuthority('CAN_READ')")
    public ResponseEntity<EmployeeProjectsResponse> getEmployeeProjects(
            @RequestHeader UUID employeeId,
            @RequestHeader(required = false) UUID projectId,
            @RequestParam(value="page", required = false) Integer page,
            @RequestParam(value="size", required = false) Integer size,
            @RequestParam(value="direction", required = false) String direction,
            @RequestParam(value="key", required = false) String key
    ) {
        var settings = initPagingSettings(page, size, key, direction);

        if (!verifyEmployeeId(employeeId)) {
            log.error("oo prosze: {}", employeeId);
            return ResponseEntity.badRequest().build();
        }

        log.info("controller: employeeId: {}, page: {}, size: {}", employeeId, settings.getPage(), settings.getSize());

        var employeeProjectsDTO = employeeService.getEmployeeProjects(employeeId, settings);
        var employeeProjectsResponse = employeeProjectsMapper.map(employeeProjectsDTO);

        return ResponseEntity.ok(employeeProjectsResponse);
    }

    @GetMapping("projects/toApprove")
    @PreAuthorize("hasAuthority('CAN_READ')")
    public ResponseEntity<EmployeeProjectsToApproveResponse> getProjectsToApprove(
            @RequestHeader UUID employeeId,
            @RequestHeader(required = false) UUID projectId,
            @RequestParam @DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm") Date startDate,
            @RequestParam @DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm") Date endDate
    ) {
        var toApproveDto = employeeService.getEmployeeProjectsToApprove(employeeId, startDate, endDate);

        return ResponseEntity.ok(employeeProjectsMapper.toApproveMap(toApproveDto));
    }

    @PostMapping("projects/toApprove")
    @PreAuthorize("hasAuthority('CAN_READ')")
    public ResponseEntity<List<UUID>> sendProjectsToApprove(
            @RequestHeader UUID employeeId,
            @RequestHeader(required = false) UUID projectId,
            @RequestBody EmployeeProjectsToApprovePayload payload
    ) {

        employeeService.sendProjectsToApprove(employeeId, payload.projectIds(), payload.startDate(), payload.endDate());

        return ResponseEntity.ok(payload.projectIds());
    }

    @PreAuthorize("hasAuthority('CAN_READ')")
    @GetMapping("/tasks")
    public ResponseEntity<EmployeeTasksResponse> getEmployeeTasks(
            @RequestHeader @NotNull UUID employeeId,
            @RequestHeader(required = false) UUID projectId,
            @RequestParam @NotNull Date startDate,
            @RequestParam@NotNull Date endDate,
            @RequestParam(value="page", required = false) Integer page,
            @RequestParam(value="size", required = false) Integer size,
            @RequestParam(value="direction", required = false) String direction,
            @RequestParam(value="key", required = false) String key
    ) {

        var settings = initPagingSettings(page, size, key, direction);
        var tasksDto = employeeService.getEmployeeTasks(employeeId, startDate, endDate, settings);
        var tasksResponse = employeeTasksMapper.map(tasksDto);

        return ResponseEntity.ok(tasksResponse);
    }

    @PreAuthorize("hasAuthority('CAN_READ')")
    @GetMapping("/task/{taskId}")
    public ResponseEntity<EmployeeTaskResponse> getTask(
            @RequestHeader @NotNull UUID employeeId,
            @RequestHeader(required = false) UUID projectId,
            @PathVariable UUID taskId) {

        var taskDto = employeeService.getTask(employeeId, taskId);
        var taskResponse = employeeTaskMapper.map(taskDto);

        return ResponseEntity.ok(taskResponse);
    }

    @PreAuthorize("hasAuthority('CAN_READ') && hasPermission(#projectId, 'CAN_MANAGE_TASKS')")
    @GetMapping("{id}/task/{taskId}")
    public ResponseEntity<EmployeeTaskResponse> getEmployeeTask(
            @RequestHeader @NotNull UUID employeeId,
            @RequestHeader(required = false) @NotNull UUID projectId,
            @PathVariable UUID taskId,
            @PathVariable UUID id) {

        var taskDto = employeeService.getTask(id, taskId);
        var taskResponse = employeeTaskMapper.map(taskDto);

        return ResponseEntity.ok(taskResponse);
    }

    @PreAuthorize("hasAuthority('CAN_READ')")
    @PostMapping("/task")
    public ResponseEntity<UUID> addTask(
            @RequestHeader @NotNull UUID employeeId,
            @RequestHeader(required = false) UUID projectId,
            @RequestBody EmployeeTaskCreatePayload payload
    ) {
        var employeeTaskCreateDto = employeeTaskCreatePayloadMapper.map(payload);
        var response = employeeService.createTask(employeeId, employeeTaskCreateDto);

        return ResponseEntity.ok(response);
    }

    @PutMapping("/task")
    public ResponseEntity<UUID> updateTask(
            @RequestHeader @NotNull UUID employeeId,
            @RequestHeader(required = false) UUID projectId,
            @RequestBody EmployeeTaskUpdatePayload payload
    ) {
        var employeeTaskUpdateDTO = employeeTaskUpdatePayloadMapper.map(payload);
        var response = employeeService.updateTask(employeeId, employeeTaskUpdateDTO);

        return ResponseEntity.ok(response);
    }

    @PreAuthorize("hasAuthority('CAN_READ') && hasPermission(#payload.projectId(), 'CAN_MANAGE_TASKS')")
    @PutMapping("{id}/task")
    public ResponseEntity<UUID> updateEmployeeTask(
            @RequestHeader @NotNull UUID employeeId,
            @RequestHeader UUID projectId,
            @RequestBody EmployeeTaskUpdatePayload payload,
            @PathVariable UUID id
    ) {
        var employeeTaskUpdateDTO = employeeTaskUpdatePayloadMapper.map(payload);
        var response = employeeService.updateTask(id, employeeTaskUpdateDTO);

        return ResponseEntity.ok(response);
    }


    @PreAuthorize("hasAuthority('CAN_READ')")
    @GetMapping("/calendar")
    public ResponseEntity<EmployeeCalendarResponse> getCalendar(
            @RequestHeader @NotNull UUID employeeId,
            @RequestHeader(required = false) UUID projectId,
            @RequestParam @DateTimeFormat(pattern="MM-yyyy") Date date) {

        var calendarDTO = employeeService.getCalendar(employeeId, date);
        var calendarResponse = calendarMapper.map(calendarDTO);

        return ResponseEntity.ok(calendarResponse);
    }


    @PreAuthorize("hasAuthority('CAN_READ') && hasPermission(#projectId, 'CAN_MANAGE_TASKS')")
    @GetMapping("/{id}/calendar")
    public ResponseEntity<EmployeeCalendarResponse> getEmployeeCalendar(
            @RequestHeader @NotNull UUID employeeId,
            @RequestHeader(required = false) @NotNull UUID projectId,
            @RequestParam @DateTimeFormat(pattern="MM-yyyy") Date date,
            @PathVariable UUID id) {

        var calendarDTO = employeeService.getEmployeeCalendar(id, projectId, date);
        var calendarResponse = calendarMapper.map(calendarDTO);

        return ResponseEntity.ok(calendarResponse);
    }


    private boolean verifyEmployeeId(UUID id) {

        UserDetailsDefault principal = (UserDetailsDefault) SecurityContextHolder
                .getContext()
                .getAuthentication()
                .getPrincipal();

        return id.equals(principal.getId());
    }

}
