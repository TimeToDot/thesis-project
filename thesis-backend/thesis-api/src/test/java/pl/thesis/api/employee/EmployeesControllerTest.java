package pl.thesis.api.employee;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import pl.thesis.api.employee.mapper.EmployeeMapper;
import pl.thesis.api.employee.mapper.EmployeesMapper;
import pl.thesis.domain.employee.EmployeeProjectsService;
import pl.thesis.domain.employee.EmployeeService;
import pl.thesis.domain.employee.EmployeeTasksService;
import pl.thesis.security.services.AuthService;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ExtendWith(MockitoExtension.class)
public class EmployeesControllerTest {

    private final EmployeesFactory employeesFactory = new EmployeesFactory();

    @Autowired
    private MockMvc mockMvc;
    @Mock
    private EmployeeService employeeService;

    @Mock
    EmployeesMapper employeesMapper;

    @InjectMocks
    private EmployeesController employeesController;

    @BeforeEach
    public void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(employeesController).build();
    }

    @Test
    public void testGetEmployees() throws Exception {
        when(employeeService.getEmployees(any(), anyBoolean()))
                .thenReturn(employeesFactory.getDummyEmployeesDTO());
        when(employeesMapper.map(any())).thenReturn(employeesFactory.getDummyEmployeesResponse());

        mockMvc.perform(get("/api/employees"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON));
    }

}
