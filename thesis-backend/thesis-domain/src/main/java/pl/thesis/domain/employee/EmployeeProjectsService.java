package pl.thesis.domain.employee;

import org.springframework.transaction.annotation.Transactional;
import pl.thesis.domain.employee.model.EmployeeProjectsApproveTemp;
import pl.thesis.domain.employee.model.EmployeeProjectsDTO;
import pl.thesis.domain.employee.model.EmployeeProjectsToApproveDTO;
import pl.thesis.domain.paging.PagingSettings;

import java.util.Date;

public interface EmployeeProjectsService {
    EmployeeProjectsDTO getEmployeeProjectToApproveList(Long id, PagingSettings pagingSettings);

    EmployeeProjectsToApproveDTO getEmployeeProjectsToApprove(Long accountId, Date startDate, Date endDate);

    @Transactional
    void sendProjectsToApprove(EmployeeProjectsApproveTemp dto, Date startDate, Date endDate);
}
