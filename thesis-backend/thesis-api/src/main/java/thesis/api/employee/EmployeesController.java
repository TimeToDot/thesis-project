package thesis.api.employee;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import thesis.api.employee.model.EmployeesResponse;
import thesis.domain.PagingSettings;

@RestController
@RequestMapping("/api/employees")
public class EmployeesController {

    @GetMapping
    public ResponseEntity<EmployeesResponse> getEmployees(@RequestBody PagingSettings pagingSettings){

        return ResponseEntity.ok().build();
    }
}
