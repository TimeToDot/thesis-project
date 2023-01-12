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
import thesis.api.employee.model.*;
import thesis.api.employee.model.calendar.EmployeeCalendarResponse;
import thesis.api.employee.model.project.EmployeeProjectsResponse;
import thesis.api.employee.model.project.EmployeeProjectsToApprovePayload;
import thesis.api.employee.model.project.EmployeeProjectsToApproveRequest;
import thesis.api.employee.model.project.EmployeeProjectsToApproveResponse;
import thesis.api.employee.model.task.*;
import thesis.domain.paging.PagingSettings;
import thesis.domain.employee.EmployeeService;
import thesis.security.services.model.UserDetailsDefault;

import javax.validation.constraints.NotNull;
import java.util.Date;
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
    @PreAuthorize("hasAuthority(" + CAN_READ + ")")
    public ResponseEntity<EmployeeResponse> getEmployee(@PathVariable UUID id){

        var employeeDTO = employeeService.getEmployee(id);
        var employee = employeeMapper.map(employeeDTO);

        return ResponseEntity.ok(employee);
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
            @RequestBody PagingSettings pagingSettings
            ){

        if (!verifyEmployeeId(employeeId)){
            log.error("oo prosze: {}", employeeId);
            return ResponseEntity.badRequest().build();
        }
        log.info("controller: employeeId: {}, page: {}, size: {}", employeeId, pagingSettings.getPage(), pagingSettings.getSize());

        var employeeProjectsDTO = employeeService.getEmployeeProjects(employeeId, pagingSettings);
        var employeeProjectsResponse = employeeProjectsMapper.map(employeeProjectsDTO);

        return ResponseEntity.ok(employeeProjectsResponse);
    }

    @GetMapping("projects/toApprove")
    @PreAuthorize("hasAuthority('CAN_READ')")
    public ResponseEntity<EmployeeProjectsToApproveResponse> getProjectsToApprove(
            @RequestHeader UUID employeeId,
            @RequestBody EmployeeProjectsToApproveRequest request
    ){
        var toApproveDto = employeeService.getEmployeeProjectsToApprove(employeeId, request.startDate(), request.endDate());

        return ResponseEntity.ok(employeeProjectsMapper.toApproveMap(toApproveDto));
    }

    @PostMapping("projects/toApprove")
    @PreAuthorize("hasAuthority('CAN_READ')")
    public ResponseEntity<List<UUID>> sendProjectsToApprove(
            @RequestHeader UUID employeeId,
            @RequestBody EmployeeProjectsToApprovePayload payload
    ){

        employeeService.sendProjectsToApprove(employeeId, payload.projectIds(), payload.startDate(), payload.endDate());

        return ResponseEntity.ok(payload.projectIds());
    }

    @GetMapping("/tasks")
    public ResponseEntity<EmployeeTasksResponse> getEmployeeTasks(
            @RequestHeader @NotNull UUID employeeId,
            @RequestBody EmployeeTasksRequest request){

        var tasksDto = employeeService.getEmployeeTasks(employeeId, request.startDate(), request.endDate());
        var tasksResponse = employeeTasksMapper.map(tasksDto);

        return ResponseEntity.ok(tasksResponse);
    }

    @GetMapping("/task/{taskId}")
    public ResponseEntity<EmployeeTaskResponse> getEmployeeTask(
            @RequestHeader @NotNull UUID employeeId,
            @RequestHeader @NotNull UUID projectId,
            @PathVariable UUID taskId){

        var taskDto = employeeService.getEmployeeTask(employeeId, taskId);
        var taskResponse = employeeTaskMapper.map(taskDto);

        return ResponseEntity.ok(taskResponse);
    }

    @PostMapping("/task")
    public ResponseEntity<UUID> addEmployeeTask(
                                                                 @RequestHeader @NotNull UUID employeeId,
                                                                 @RequestHeader @NotNull UUID projectId,
                                                                 @RequestBody EmployeeTaskCreatePayload payload
    ){
        var employeeTaskCreateDto = employeeTaskCreatePayloadMapper.map(payload);
        var response = employeeService.createEmployeeTask(employeeId, employeeTaskCreateDto);

        return ResponseEntity.ok(response);
    }

    @PutMapping("/task")
    public ResponseEntity<UUID> updateEmployeeTask( // TODO: 28/12/2022 zmienic response na uuid po testach
            @RequestHeader @NotNull UUID employeeId,
            @RequestHeader @NotNull UUID projectId,
            @RequestBody EmployeeTaskUpdatePayload payload
    ){
        var employeeTaskUpdateDTO = employeeTaskUpdatePayloadMapper.map(payload);
        var response = employeeService.updateEmployeeTask(employeeId, employeeTaskUpdateDTO);

        return ResponseEntity.ok(response);
    }



    @GetMapping("/calendar")
    public ResponseEntity<EmployeeCalendarResponse> getEmployeeCalendar(
            @RequestHeader @NotNull UUID employeeId,
            @RequestBody @DateTimeFormat(pattern = "MM-yyyy") Date date){
        // TODO: 26/12/2022
        return  null;
    }





    private boolean verifyEmployeeId(UUID id){

        UserDetailsDefault principal = (UserDetailsDefault) SecurityContextHolder
                .getContext()
                .getAuthentication()
                .getPrincipal();

        return id.equals(principal.getId());
    }

}
