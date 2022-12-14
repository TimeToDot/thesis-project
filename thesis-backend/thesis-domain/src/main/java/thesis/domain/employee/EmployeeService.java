package thesis.domain.employee;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import thesis.data.account.AccountDetailsRepository;
import thesis.data.account.AccountRepository;
import thesis.data.project.model.ProjectAccountRole;
import thesis.domain.employee.mapper.EmployeeDTOMapper;
import thesis.domain.employee.mapper.EmployeeProjectDTOMapper;
import thesis.domain.employee.model.EmployeeDTO;
import thesis.domain.employee.model.EmployeeProjectDTO;

import java.util.List;
import java.util.UUID;


@AllArgsConstructor
@Service

public class EmployeeService {

    private final AccountRepository accountRepository;
    private final AccountDetailsRepository accountDetailsRepository;
    private final EmployeeDTOMapper employeeDTOMapper;
    private final EmployeeProjectDTOMapper employeeProjectDTOMapper;

    public EmployeeDTO getEmployee(UUID id){
        var account = accountRepository.findById(id).orElseThrow();
        var details = accountDetailsRepository.findByAccount(account).orElseThrow();
        return employeeDTOMapper.map(details);
    }

    public List<EmployeeProjectDTO> getEmployeeProjects(UUID id){
        var account = accountRepository.findById(id).orElseThrow();

        var projects = account.getProjectAccountRoles().stream().map(ProjectAccountRole::getProject);

        return projects.map(employeeProjectDTOMapper::map).toList();
    }

}