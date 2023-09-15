package pl.thesis.domain.employee;


import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;
import pl.thesis.data.task.model.Task;
import pl.thesis.data.task.model.TaskStatus;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(MockitoExtension.class)
public class EmployeeServiceTest {

    private List<Task> taskList;

    @InjectMocks
    private EmployeeService employeeService;

    @InjectMocks
    private EmployeeCalendarService employeeCalendarService;

    @BeforeEach
    public void initData() throws ParseException {
        SimpleDateFormat ft = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");

        taskList = List.of(
                Task.builder().id(1L).name("dummy").dateFrom(ft.parse("2023-01-01 12:00:02")).dateTo(ft.parse("2023-01-01 13:00:03")).status(TaskStatus.LOGGED).build(),
                Task.builder().id(2L).name("dummy").dateFrom(ft.parse("2023-01-01 13:02:02")).dateTo(ft.parse("2023-01-01 14:00:03")).status(TaskStatus.LOGGED).build(),
                Task.builder().id(3L).name("dummy").dateFrom(ft.parse("2023-01-01 15:00:02")).dateTo(ft.parse("2023-01-01 17:00:03")).status(TaskStatus.PENDING).build(),
                //
                Task.builder().id(4L).name("dummy").dateFrom(ft.parse("2023-01-04 12:00:02")).dateTo(ft.parse("2023-01-04 13:00:03")).status(TaskStatus.LOGGED).build(),
                Task.builder().id(5L).name("dummy").dateFrom(ft.parse("2023-01-04 13:02:02")).dateTo(ft.parse("2023-01-04 14:00:03")).status(TaskStatus.LOGGED).build(),
                Task.builder().id(6L).name("dummy").dateFrom(ft.parse("2023-01-04 15:00:02")).dateTo(ft.parse("2023-01-04 17:00:03")).status(TaskStatus.LOGGED).build(),
                //
                Task.builder().id(7L).name("dummy").dateFrom(ft.parse("2023-01-06 12:00:02")).dateTo(ft.parse("2023-01-06 13:00:03")).status(TaskStatus.LOGGED).build(),
                Task.builder().id(8L).name("dummy").dateFrom(ft.parse("2023-01-06 13:02:02")).dateTo(ft.parse("2023-01-06 14:00:03")).status(TaskStatus.LOGGED).build(),
                Task.builder().id(9L).name("dummy").dateFrom(ft.parse("2023-01-06 15:00:02")).dateTo(ft.parse("2023-01-06 17:00:03")).status(TaskStatus.APPROVED).build(),
                //
                Task.builder().id(10L).name("dummy").dateFrom(ft.parse("2023-01-11 12:00:02")).dateTo(ft.parse("2023-01-11 13:00:03")).status(TaskStatus.LOGGED).build(),
                Task.builder().id(11L).name("dummy").dateFrom(ft.parse("2023-01-11 13:02:02")).dateTo(ft.parse("2023-01-11 14:00:03")).status(TaskStatus.LOGGED).build(),
                Task.builder().id(12L).name("dummy").dateFrom(ft.parse("2023-01-11 15:00:02")).dateTo(ft.parse("2023-01-11 17:00:03")).status(TaskStatus.REJECTED).build(),
                //
                Task.builder().id(13L).name("dummy").dateFrom(ft.parse("2023-01-12 12:00:02")).dateTo(ft.parse("2023-01-01 13:00:03")).status(TaskStatus.APPROVED).build(),
                Task.builder().id(14L).name("dummy").dateFrom(ft.parse("2023-01-12 13:02:02")).dateTo(ft.parse("2023-01-01 14:00:03")).status(TaskStatus.APPROVED).build(),
                Task.builder().id(15L).name("dummy").dateFrom(ft.parse("2023-01-12 15:00:02")).dateTo(ft.parse("2023-01-01 17:00:03")).status(TaskStatus.APPROVED).build()
        );
    }

    @AfterEach
    public void dropData(){
        taskList = new ArrayList<>();
    }

    @Test
    public void shouldGetCalendarTaskDTOList(){
        //when
        var calendarList = employeeCalendarService.getCalendarTaskDTOList(taskList);
        //then
        assertThat(calendarList).isNotEmpty();

    }




}
