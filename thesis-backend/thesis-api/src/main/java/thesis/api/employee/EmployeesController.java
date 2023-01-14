package thesis.api.employee;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import thesis.api.employee.model.EmployeesResponse;
import thesis.domain.paging.PagingSettings;

import java.util.UUID;

@RestController
@RequestMapping("/api/employees")
public class EmployeesController {

    @GetMapping
    public ResponseEntity<EmployeesResponse> getEmployees(
            @RequestHeader UUID employeeId,
            @RequestBody PagingSettings pagingSettings,
            @RequestParam(value="active", required = false, defaultValue = "true") Boolean active
    ){


        return ResponseEntity.ok().build();
    }
}
