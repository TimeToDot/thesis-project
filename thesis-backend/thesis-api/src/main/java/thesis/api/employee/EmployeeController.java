package thesis.api.employee;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import thesis.api.employee.mapper.EmployeeMapper;
import thesis.api.employee.mapper.EmployeeProjectMapper;
import thesis.api.employee.model.EmployeeProjectsResponse;
import thesis.api.employee.model.EmployeeResponse;
import thesis.api.employee.model.EmployeeTasksRequest;
import thesis.api.employee.model.EmployeeTasksResponse;
import thesis.domain.PagingSettings;
import thesis.domain.employee.EmployeeService;

import java.util.UUID;

@RestController
@AllArgsConstructor
@RequestMapping("/api/employee")
public class EmployeeController {

    private final EmployeeService employeeService;

    private final EmployeeMapper employeeMapper;
    private final EmployeeProjectMapper employeeProjectMapper;

    @Operation(summary = "Gets employee by ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "OK"),
            @ApiResponse(responseCode = "400", description = "Invalid ID supplied"),
            @ApiResponse(responseCode = "401", description = "Unauthorized"),
            @ApiResponse(responseCode = "403", description = "Access to the requested resource is forbidden"),
            @ApiResponse(responseCode = "404", description = "Customer not found"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    @GetMapping("/{id}")
    @PreAuthorize("hasAuthority('CAN_READ')")
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
    @GetMapping("/{id}/projects")
    @PreAuthorize("hasAuthority('CAN_ADMIN_USERS')")
    public ResponseEntity<EmployeeProjectsResponse> getEmployeeProjects(@PathVariable UUID id/*, @RequestBody PagingSettings pagingSettings*/){

        var employeeProjectsDTO = employeeService.getEmployeeProjects(id);

        var employeeProjectResponse = EmployeeProjectsResponse
                .builder()
                .projects(employeeProjectsDTO
                        .stream()
                        .map(employeeProjectMapper::map)
                        .toList())
                .build();

        return ResponseEntity.ok(employeeProjectResponse);
    }

    @GetMapping("/{id}/tasks")
    public ResponseEntity<EmployeeTasksResponse> getEmployeeTasks(@PathVariable UUID id, @RequestBody EmployeeTasksRequest employeeTasksRequest, @RequestBody PagingSettings pagingSettings){

        return ResponseEntity.ok().build();
    }



}
