package thesis.api.employee;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import thesis.api.ThesisController;
import thesis.api.employee.mapper.EmployeesMapper;
import thesis.api.employee.model.EmployeesResponse;
import thesis.domain.employee.EmployeeService;
import thesis.domain.paging.PagingSettings;

import java.util.UUID;

@Slf4j
@RestController
@AllArgsConstructor
@RequestMapping("/api/employees")
public class EmployeesController extends ThesisController {

    private final EmployeeService employeeService;

    private final EmployeesMapper employeesMapper;

    @PreAuthorize("hasAuthority('CAN_READ')")
    @GetMapping
    public ResponseEntity<EmployeesResponse> getEmployees(
            @RequestHeader UUID employeeId,
            @RequestParam(value="active", required = false, defaultValue = "true") Boolean active,
            @RequestParam(value="page", required = false) Integer page,
            @RequestParam(value="size", required = false) Integer size,
            @RequestParam(value="direction", required = false) String direction,
            @RequestParam(value="key", required = false) String key
    ) {
        var settings = initPagingSettings(page, size, key, direction);

        var dto = employeeService.getEmployees(settings, active);
        var response = employeesMapper.map(dto);

        return ResponseEntity.ok(response);
    }
}
