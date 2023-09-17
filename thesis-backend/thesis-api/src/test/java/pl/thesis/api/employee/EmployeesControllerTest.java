package pl.thesis.api.employee;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import pl.thesis.api.employee.mapper.EmployeesMapper;
import pl.thesis.domain.employee.EmployeeServiceDefault;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.anyBoolean;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(MockitoExtension.class)
public class EmployeesControllerTest {

    private final EmployeesFactory employeesFactory = new EmployeesFactory();

    @Autowired
    private MockMvc mockMvc;
    @Mock
    private EmployeeServiceDefault employeeService;

    @Mock
    EmployeesMapper employeesMapper;

    @InjectMocks
    private EmployeesController employeesController;

    @BeforeEach
    public void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(employeesController).build();
    }

    @Test
    public void getEmployees() throws Exception {
        //when
        when(employeeService.getEmployees(any(), anyBoolean()))
                .thenReturn(employeesFactory.getDummyEmployeesDTO());
        when(employeesMapper.map(any())).thenReturn(employeesFactory.getDummyEmployeesResponse());

        //then
        mockMvc.perform(get("/api/employees"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON));
    }

    @Test
    void getEmployee() {
    }

    @Test
    void updateEmployeeById() {
    }

    @Test
    void updatePasswordEmployee() {
    }

    @Test
    void getEmployeeProjects() {
    }

    @Test
    void getProjectsToApprove() {
    }

    @Test
    void sendProjectsToApprove() {
    }

    @Test
    void getEmployeeTasks() {
    }

    @Test
    void getEmployeeTask() {
    }

    @Test
    void addTask() {
    }

    @Test
    void updateTask() {
    }

    @Test
    void deleteTask() {
    }

    @Test
    void updateEmployeeTask() {
    }

    @Test
    void getCalendar() {
    }

    @Test
    void addEmployee() {
    }
}
