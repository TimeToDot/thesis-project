package thesis.domain.employee;

import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import thesis.data.account.AccountDetailsRepository;
import thesis.data.account.AccountRepository;
import thesis.data.project.ProjectAccountRoleRepository;
import thesis.data.project.model.Project;
import thesis.data.project.model.ProjectAccountRole;
import thesis.domain.paging.Paging;
import thesis.domain.paging.PagingSettings;
import thesis.domain.employee.mapper.EmployeeDTOMapper;
import thesis.domain.employee.mapper.EmployeeProjectDTOMapper;
import thesis.domain.employee.model.EmployeeDTO;
import thesis.domain.employee.model.EmployeeProjectsDTO;
import thesis.domain.paging.Sorting;

import java.util.UUID;

import static thesis.domain.paging.PagingHelper.getPaging;
import static thesis.domain.paging.PagingHelper.getSorting;


@AllArgsConstructor
@Service

public class EmployeeService {

    private final AccountRepository accountRepository;
    private final AccountDetailsRepository accountDetailsRepository;

    private final ProjectAccountRoleRepository projectAccountRoleRepository;
    private final EmployeeDTOMapper employeeDTOMapper;
    private final EmployeeProjectDTOMapper employeeProjectDTOMapper;

    public EmployeeDTO getEmployee(UUID id) {
        var account = accountRepository.findById(id).orElseThrow();
        var details = accountDetailsRepository.findByAccount(account).orElseThrow();
        return employeeDTOMapper.map(details);
    }

    public EmployeeProjectsDTO getEmployeeProjects(UUID id, PagingSettings pagingSettings) {
        if (!accountRepository.existsById(id)) {
            throw new UsernameNotFoundException("employee not found"); // TODO: 17/12/2022  exception handling
        }

        var account = accountRepository
                .findById(id)
                .orElseThrow();

        var projects = projectAccountRoleRepository.findAllByAccount_Id(account.getId(), pagingSettings.getPageable())
                .orElseThrow()
                .map(ProjectAccountRole::getProject);

        var paging = getPaging(pagingSettings, projects);
        var sorting = getSorting(pagingSettings);

        return EmployeeProjectsDTO.builder()
                .employeeProjectDTOList(projects.map(employeeProjectDTOMapper::map).toList())
                .paging(paging)
                .sorting(sorting)
                .build();
    }

}