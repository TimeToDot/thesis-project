package thesis.api.employee;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import thesis.api.ThesisController;
import thesis.api.employee.mapper.*;
import thesis.api.employee.model.*;
import thesis.api.employee.model.calendar.EmployeeCalendarRequest;
import thesis.api.employee.model.calendar.EmployeeCalendarResponse;
import thesis.api.employee.model.project.EmployeeProjectsResponse;
import thesis.api.employee.model.project.EmployeeProjectsToApprovePayload;
import thesis.api.employee.model.project.EmployeeProjectsToApproveRequest;
import thesis.api.employee.model.project.EmployeeProjectsToApproveResponse;
import thesis.api.employee.model.task.*;
import thesis.domain.employee.model.EmployeeUpdatePayloadDTO;
import thesis.domain.employee.model.PasswordUpdatePayloadDTO;
import thesis.domain.paging.PagingSettings;
import thesis.domain.employee.EmployeeService;
import thesis.security.services.model.UserDetailsDefault;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.util.List;
import java.util.UUID;

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
    public ResponseEntity<EmployeeResponse> getEmployee(@RequestHeader UUID employeeId) {

        var employeeDTO = employeeService.getEmployee(employeeId);
        var employee = employeeMapper.map(employeeDTO);

        return ResponseEntity.ok(employee);
    }

    @PutMapping
    @PreAuthorize("hasAuthority('CAN_READ')")
    public ResponseEntity<UUID> updateEmployee(
            @RequestHeader UUID employeeId,
            @RequestBody EmployeeUpdatePayloadDTO payload
    ) {
        var response = employeeService.updateEmployee(employeeId, payload);

        return ResponseEntity.ok(response);
    }

    @PutMapping("/password")
    @PreAuthorize("hasAuthority('CAN_READ')")
    public ResponseEntity<UUID> updatePasswordEmployee(
            @RequestHeader UUID employeeId,
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
            @RequestParam(required = false) PagingSettings settings
    ) {
        if (settings == null) settings = new PagingSettings();

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
            @RequestParam EmployeeProjectsToApproveRequest request
    ) {
        var toApproveDto = employeeService.getEmployeeProjectsToApprove(employeeId, request.startDate(), request.endDate());

        return ResponseEntity.ok(employeeProjectsMapper.toApproveMap(toApproveDto));
    }

    @PostMapping("projects/toApprove")
    @PreAuthorize("hasAuthority('CAN_READ')")
    public ResponseEntity<List<UUID>> sendProjectsToApprove(
            @RequestHeader UUID employeeId,
            @RequestBody EmployeeProjectsToApprovePayload payload
    ) {

        employeeService.sendProjectsToApprove(employeeId, payload.projectIds(), payload.startDate(), payload.endDate());

        return ResponseEntity.ok(payload.projectIds());
    }

    @PreAuthorize("hasAuthority('CAN_READ')")
    @GetMapping("/tasks")
    public ResponseEntity<EmployeeTasksResponse> getEmployeeTasks(
            @RequestHeader @NotNull UUID employeeId,
            @RequestParam @Valid EmployeeTasksRequest request) {

        var tasksDto = employeeService.getEmployeeTasks(employeeId, request.startDate(), request.endDate(), request.settings());
        var tasksResponse = employeeTasksMapper.map(tasksDto);

        return ResponseEntity.ok(tasksResponse);
    }

    @PreAuthorize("hasAuthority('CAN_READ')")
    @GetMapping("/task/{taskId}")
    public ResponseEntity<EmployeeTaskResponse> getEmployeeTask(
            @RequestHeader @NotNull UUID employeeId,
            @PathVariable UUID taskId) {

        var taskDto = employeeService.getEmployeeTask(employeeId, taskId);
        var taskResponse = employeeTaskMapper.map(taskDto);

        return ResponseEntity.ok(taskResponse);
    }

    @PreAuthorize("hasAuthority('CAN_READ')")
    @PostMapping("/task")
    public ResponseEntity<UUID> addEmployeeTask(
            @RequestHeader @NotNull UUID employeeId,
            //@RequestHeader @NotNull UUID projectId, todo
            @RequestBody EmployeeTaskCreatePayload payload
    ) {
        var employeeTaskCreateDto = employeeTaskCreatePayloadMapper.map(payload);
        var response = employeeService.createEmployeeTask(employeeId, employeeTaskCreateDto);

        return ResponseEntity.ok(response);
    }

    @PreAuthorize("hasAuthority('CAN_READ')")
    @PutMapping("/task")
    public ResponseEntity<UUID> updateEmployeeTask(
            @RequestHeader @NotNull UUID employeeId,
            //@RequestHeader @NotNull UUID projectId, todo
            @RequestBody EmployeeTaskUpdatePayload payload
    ) {
        var employeeTaskUpdateDTO = employeeTaskUpdatePayloadMapper.map(payload);
        var response = employeeService.updateEmployeeTask(employeeId, employeeTaskUpdateDTO);

        return ResponseEntity.ok(response);
    }


    @PreAuthorize("hasAuthority('CAN_READ')")
    @GetMapping("/calendar")
    public ResponseEntity<EmployeeCalendarResponse> getEmployeeCalendar(
            @RequestHeader @NotNull UUID employeeId,
            @RequestBody EmployeeCalendarRequest request) {

        var calendarDTO = employeeService.getCalendar(employeeId, request.date());
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
