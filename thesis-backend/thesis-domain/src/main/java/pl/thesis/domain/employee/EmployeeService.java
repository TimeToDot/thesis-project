package pl.thesis.domain.employee;

import org.springframework.transaction.annotation.Transactional;
import pl.thesis.domain.employee.model.EmployeeDTO;
import pl.thesis.domain.employee.model.EmployeeUpdatePayloadDTO;
import pl.thesis.domain.employee.model.EmployeesDTO;
import pl.thesis.domain.employee.model.PasswordUpdatePayloadDTO;
import pl.thesis.domain.paging.PagingSettings;

import java.text.ParseException;

public interface EmployeeService {
    @Transactional
    EmployeeDTO getEmployee(Long id);

    EmployeesDTO getEmployees(PagingSettings settings, Boolean active);

    @Transactional
    Long updateEmployee(EmployeeUpdatePayloadDTO payloadDTO) throws ParseException;

    @Transactional
    Long updateEmployeePassword(PasswordUpdatePayloadDTO payloadDTO);
}
