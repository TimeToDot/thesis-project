package pl.thesis.domain.employee;

import org.springframework.transaction.annotation.Transactional;
import pl.thesis.domain.employee.model.EmployeeTaskCreatePayloadDTO;
import pl.thesis.domain.employee.model.EmployeeTaskDTO;
import pl.thesis.domain.employee.model.EmployeeTaskUpdatePayloadDTO;
import pl.thesis.domain.employee.model.EmployeeTasksDTO;
import pl.thesis.domain.paging.PagingSettings;

import java.util.Date;

public interface EmployeeTasksService {
    EmployeeTasksDTO getEmployeeTasks(Long employeeId, Date startDate, Date endDate, PagingSettings settings);

    EmployeeTaskDTO getTask(Long employeeId, Long taskId);

    @Transactional
    Long createTask(Long employeeId, EmployeeTaskCreatePayloadDTO payloadDTO);

    @Transactional
    Long updateTask(EmployeeTaskUpdatePayloadDTO payloadDTO);

    @Transactional
    void deleteTask(Long employeeId, Long taskId);
}
