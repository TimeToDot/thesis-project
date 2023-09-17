package pl.thesis.domain.project;

import org.springframework.transaction.annotation.Transactional;
import pl.thesis.domain.employee.model.CalendarDTO;
import pl.thesis.domain.employee.model.EmployeeTasksDTO;
import pl.thesis.domain.paging.PagingSettings;
import pl.thesis.domain.project.model.approval.ProjectApprovalTasksPayloadDTO;
import pl.thesis.domain.project.model.approval.ProjectApprovalsDTO;
import pl.thesis.domain.project.model.employee.ProjectEmployeeCreatePayloadDTO;
import pl.thesis.domain.project.model.employee.ProjectEmployeeDTO;
import pl.thesis.domain.project.model.employee.ProjectEmployeeUpdatePayloadDTO;
import pl.thesis.domain.project.model.employee.ProjectEmployeesDTO;

import java.util.Date;

public interface ProjectEmployeeService {
    ProjectEmployeeDTO getProjectEmployee(Long projectId, Long employeeId);

    CalendarDTO getProjectEmployeeCalendar(Long projectEmployeeId, Long projectId, Date date);

    EmployeeTasksDTO getProjectEmployeeTasks(Long projectEmployeeId, Long projectId, Date startDate, Date endDate, PagingSettings settings);

    @Transactional
    ProjectEmployeesDTO getProjectEmployees(Long projectId, Boolean active, PagingSettings settings);

    @Transactional
    ProjectApprovalsDTO getProjectApprovalEmployees(Long projectId, PagingSettings settings);

    @Transactional
    void setProjectApprovalsEmployee(ProjectApprovalTasksPayloadDTO payloadDTO, PagingSettings settings);

    @Transactional
    Long addProjectEmployee(ProjectEmployeeCreatePayloadDTO payloadDTO);

    @Transactional
    Long updateProjectEmployee(ProjectEmployeeUpdatePayloadDTO payloadDTO);
}
