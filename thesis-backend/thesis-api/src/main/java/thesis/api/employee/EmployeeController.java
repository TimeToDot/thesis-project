package thesis.api.employee;

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
import thesis.domain.employee.EmployeeService;

import java.util.UUID;

@RestController
@AllArgsConstructor
@RequestMapping("/api/employee")
public class EmployeeController {

    private final EmployeeService employeeService;

    private final EmployeeMapper employeeMapper;
    private final EmployeeProjectMapper employeeProjectMapper;

    @GetMapping("/{id}")
    @PreAuthorize("hasAuthority('CAN_READ')")
    public ResponseEntity<EmployeeResponse> getEmployee(@PathVariable UUID id){

        var employeeDTO = employeeService.getEmployee(id);
        var employee = employeeMapper.map(employeeDTO);

        return ResponseEntity.ok(employee);
    }

    @GetMapping("/{id}/projects")
    @PreAuthorize("hasAuthority('CAN_READ')")
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
    public ResponseEntity<EmployeeTasksResponse> getEmployeeTasks(@PathVariable UUID id, @RequestBody EmployeeTasksRequest employeeTasksRequest/*, @RequestBody PagingSettings pagingSettings*/){

        return ResponseEntity.ok().build();
    }



}
