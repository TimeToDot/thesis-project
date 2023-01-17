package thesis.domain.employee;

import lombok.AllArgsConstructor;
import org.apache.commons.lang3.time.DateUtils;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import thesis.data.account.AccountDetailsRepository;
import thesis.data.account.AccountRepository;
import thesis.data.account.model.Account;
import thesis.data.account.model.AccountDetails;
import thesis.data.account.model.StatusType;
import thesis.data.position.PositionRepository;
import thesis.data.project.AccountProjectRepository;
import thesis.data.project.ProjectRepository;
import thesis.data.project.model.AccountProject;
import thesis.data.task.TaskFormRepository;
import thesis.data.task.TaskRepository;
import thesis.data.task.model.Task;
import thesis.data.task.model.TaskStatus;
import thesis.domain.employee.mapper.*;
import thesis.domain.employee.model.*;
import thesis.domain.paging.PagingSettings;
import thesis.domain.task.mapper.TaskFormDTOMapper;
import thesis.domain.task.model.TaskStatusDTO;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.*;
import java.util.*;
import java.util.stream.Collectors;

import static thesis.domain.paging.PagingHelper.getPaging;
import static thesis.domain.paging.PagingHelper.getSorting;


@AllArgsConstructor
@Service
public class EmployeeService {

    private final AccountRepository accountRepository;
    private final AccountDetailsRepository accountDetailsRepository;
    private final AccountProjectRepository accountProjectRepository;
    private final ProjectRepository projectRepository;

    private final TaskRepository taskRepository;
    private final TaskFormRepository taskFormRepository;
    private final EmployeeDTOMapper employeeDTOMapper;
    private final EmployeeProjectDTOMapper employeeProjectDTOMapper;
    private final EmployeeProjectDetailsDTOMapper employeeProjectDetailsDTOMapper;
    private final TaskFormDTOMapper taskFormDTOMapper;
    private final EmployeeTasksDTOMapper employeeTasksDTOMapper;
    private final EmployeeTaskDTOMapper employeeTaskDTOMapper;
    private final PositionRepository positionRepository;

    private final PasswordEncoder passwordEncoder;

    private final SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd'T'");

    public EmployeeDTO getEmployee(UUID id) {
        var account = accountRepository.findById(id).orElseThrow();
        var details = accountDetailsRepository.findByAccount(account).orElseThrow();
        return employeeDTOMapper.map(details);
    }

    public EmployeesDTO getEmployees(PagingSettings settings, Boolean active) {
        var status = Boolean.TRUE.equals(active) ? StatusType.ENABLE : StatusType.EXPIRED;

        var employees = accountRepository.findAllByStatus(status, settings.getPageable());
        var paging = getPaging(settings, employees);
        var sorting = getSorting(settings);

        return EmployeesDTO.builder()
                .employees(
                        employees
                                .map(Account::getDetails)
                                .map(employeeDTOMapper::map)
                                .toList()
                )
                .paging(paging)
                .sorting(sorting)
                .build();
    }

    @Transactional
    public UUID updateEmployee(UUID employeeId, EmployeeUpdatePayloadDTO payloadDTO){
        var account = accountRepository.findById(employeeId).orElseThrow();
        var accountDetails = accountDetailsRepository.findByAccount(account).orElseThrow();

        setAccountFields(payloadDTO, account);
        setAccountDetailsFields(payloadDTO, accountDetails);
        //var accountDetails = employeeDTOMapper.mapToDetails(payloadDTO); //check mappers fith instance

        accountRepository.save(account);
        accountDetailsRepository.save(accountDetails);

        return account.getId();
    }

    @Transactional
    public UUID updateEmployeePassword(UUID employeeId, PasswordUpdatePayloadDTO payloadDTO){
        var account = accountRepository.findById(employeeId).orElseThrow();

        account.setPass(passwordEncoder.encode(payloadDTO.password()));

        accountRepository.save(account);

        return account.getId();
    }

    public EmployeeProjectsDTO getEmployeeProjects(UUID id, PagingSettings pagingSettings) {
        if (!accountRepository.existsById(id)) {
            throw new UsernameNotFoundException("employee not found"); // TODO: 17/12/2022  exception handling
        }

        var account = accountRepository.findById(id).orElseThrow();
        var accountProjects = accountProjectRepository.findAllByAccountId(account.getId(), pagingSettings.getPageable()).orElseThrow();

        var paging = getPaging(pagingSettings, accountProjects);
        var sorting = getSorting(pagingSettings);

        return EmployeeProjectsDTO.builder()
                .employeeProjectDTOList(accountProjects.map(employeeProjectDetailsDTOMapper::map).toList())
                .paging(paging)
                .sorting(sorting)
                .build();
    }

    public EmployeeProjectsToApproveDTO getEmployeeProjectsToApprove(UUID accountId, Date startDate, Date endDate) {
        var account = accountRepository
                .findById(accountId)
                .orElseThrow();

        var tasks = taskRepository
                .findByAccountIdAndStatusAndDateFromBetween(
                        account.getId(),
                        TaskStatus.PENDING,
                        startDate,
                        endDate
                )
                .orElseThrow();

        var employeeProjects = tasks.stream()
                .collect(Collectors.groupingBy(task -> task.getForm().getProject().getId()))
                .entrySet().stream()
                .map(uuidListEntry -> {
                    var project = projectRepository.findById(uuidListEntry.getKey()).orElseThrow();

                    return EmployeeProjectToApproveDTO.builder()
                            .project(employeeProjectDTOMapper.map(project))
                            .count(uuidListEntry.getValue().size())
                            .build();
                })
                .toList();

        return EmployeeProjectsToApproveDTO.builder()
                .projects(employeeProjects)
                .build();

    }

    @Transactional
    public void sendProjectsToApprove(UUID accountId, List<UUID> projectIds, Date startDate, Date endDate) {
        var tasks = taskRepository
                .findByAccountIdAndStatusAndDateFromBetween(
                        accountId,
                        TaskStatus.PENDING,
                        startDate,
                        endDate
                )
                .orElseThrow();

        projectIds.forEach(projectId -> {
            tasks.stream()
                    .filter(task -> task.getForm().getProject().getId().compareTo(projectId) == 0)
                    .forEach(task -> task.setStatus(TaskStatus.APPROVED));

            taskRepository.saveAll(tasks);

        });

    }

    public EmployeeTasksDTO getEmployeeTasks(UUID employeeId, Date startDate, Date endDate, PagingSettings settings) {
        if (settings == null) {
            settings = new PagingSettings();
        }

        var tasks = taskRepository.findByAccountIdAndDateFromBetween(employeeId, startDate, endDate, settings.getPageable()).orElseThrow();

        var paging = getPaging(settings, tasks);

        var sorting = getSorting(settings);

        return EmployeeTasksDTO.builder()
                .tasks(employeeTasksDTOMapper.map(tasks.stream().toList()))
                .paging(paging)
                .sorting(sorting)
                .build();
    }

    public EmployeeTaskDTO getTask(UUID employeeId, UUID taskId) {
        var task = taskRepository.findById(taskId).orElseThrow();

        if (task.getAccount().getId().compareTo(employeeId) != 0){
            throw new RuntimeException("Task doesnt belong to employee with this id");
        }

        return employeeTaskDTOMapper.map(task);
    }

    @Transactional
    public UUID createTask(UUID employeeId, EmployeeTaskCreatePayloadDTO payloadDTO) {
        var account = accountRepository.findById(employeeId).orElseThrow();
        var taskForm = taskFormRepository.findById(payloadDTO.taskId()).orElseThrow();

        var task = Task.builder()
                .account(account)
                .form(taskForm)
                .createdAt(new Date())
                .dateFrom(payloadDTO.startDate())
                .dateTo(payloadDTO.endDate())
                .status(TaskStatus.LOGGED)
                .build();

        taskRepository.save(task);

        return task.getId();
    }

    @Transactional
    public UUID updateTask(UUID employeeId, EmployeeTaskUpdatePayloadDTO payloadDTO) {
        var account = accountRepository.findById(employeeId).orElseThrow();
        var project = projectRepository.findById(payloadDTO.projectId()).orElseThrow();

        if(project.getAccountProjects().stream().map(AccountProject::getAccount).noneMatch(account1 -> account1.getId().compareTo(account.getId()) == 0)) {
            throw new RuntimeException("employee doesnt exist in this project");
        }

        var taskForm = taskFormRepository.findById(payloadDTO.taskId()).orElseThrow();
        var task = taskRepository.findById(payloadDTO.id()).orElseThrow();

        task.setDateFrom(payloadDTO.startDate());
        task.setDateTo(payloadDTO.endDate());
        task.setForm(taskForm);
        task.setStatus(TaskStatus.valueOf(payloadDTO.status().name()));

        taskRepository.save(task);

        return task.getId();
    }

    public CalendarDTO getCalendar(UUID employeeId, Date date) {
        var account = accountRepository.findById(employeeId).orElseThrow();
        var listOfDate = getFirstAndLastDayOfMonth(date);
        var size = 100;
        var page = 1;
        var settings = PagingSettings.builder().page(page).size(size).build();
        var tasks = taskRepository.findByAccountIdAndDateFromBetween(account.getId(), listOfDate.get(0), listOfDate.get(1), settings.getPageable()).orElseThrow();
        var taskList = new ArrayList<>(tasks.stream().toList());

        while (tasks.getTotalPages() > page) {
            page += 1;
            settings = PagingSettings.builder().page(page).size(size).build();
            tasks = taskRepository.findByAccountIdAndDateFromBetween(account.getId(), listOfDate.get(0), listOfDate.get(1), settings.getPageable()).orElseThrow();
            taskList.addAll(tasks.stream().toList());
        }


        return new CalendarDTO(getCalendarTaskDTOList(taskList));
    }

    public CalendarDTO getEmployeeCalendar(UUID employeeId, UUID projectId, Date date) {
        var account = accountRepository.findById(employeeId).orElseThrow();
        var project = projectRepository.findById(projectId).orElseThrow();
        var listOfDate = getFirstAndLastDayOfMonth(date);
        var size = 100;
        var page = 1;
        var settings = PagingSettings.builder().page(page).size(size).build();
        var tasks = taskRepository.findByAccountIdAndFormProjectIdAndDateFromBetween(account.getId(), project.getId(), listOfDate.get(0), listOfDate.get(1), settings.getPageable()).orElseThrow();
        var taskList = new ArrayList<>(tasks.stream().toList());

        while (tasks.getTotalPages() > page) {
            page += 1;
            settings = PagingSettings.builder().page(page).size(size).build();
            tasks = taskRepository.findByAccountIdAndFormProjectIdAndDateFromBetween(account.getId(), project.getId(), listOfDate.get(0), listOfDate.get(1), settings.getPageable()).orElseThrow();
            taskList.addAll(tasks.stream().toList());
        }


        return new CalendarDTO(getCalendarTaskDTOList(taskList));
    }

    public List<CalendarTaskDTO> getCalendarTaskDTOList(List<Task> tasks) {
        var groupedTasks = tasks.stream()
                .collect(
                        Collectors.groupingBy(
                                task -> getSimplyDate(task.getDateFrom()),
                                Collectors.groupingBy(
                                        Task::getStatus,
                                        Collectors.counting()
                                )
                        )
                );

        return groupedTasks
                .entrySet()
                .stream()
                .map(
                        dateMapEntry -> new CalendarTaskDTO(
                                dateMapEntry.getKey(),
                                getStatus(dateMapEntry.getValue())
                        )
                )
                .toList();
    }

    private TaskStatusDTO getStatus(Map<TaskStatus, Long> tasks) {

        if (tasks.get(TaskStatus.PENDING) != null && tasks.get(TaskStatus.PENDING) > 0) {
            return TaskStatusDTO.PENDING;
        } else if (tasks.get(TaskStatus.REJECTED) != null && tasks.get(TaskStatus.REJECTED) > 0) {
            return TaskStatusDTO.REJECTED;
        } else if (tasks.get(TaskStatus.LOGGED) != null && tasks.get(TaskStatus.LOGGED) > 0) {
            return TaskStatusDTO.LOGGED;
        } else if (tasks.get(TaskStatus.APPROVED) != null && tasks.get(TaskStatus.APPROVED) > 0) {
            return TaskStatusDTO.APPROVED;
        }
        return TaskStatusDTO.NONE;
    }

    public List<Date> getFirstAndLastDayOfMonth(Date date) {
        LocalDate currentDate = Instant.ofEpochMilli(date.getTime())
                .atZone(ZoneId.systemDefault())
                .toLocalDate();
        YearMonth yearMonth = YearMonth.of(currentDate.getYear(), currentDate.getMonth());

        var firstDay = Date
                .from(yearMonth
                        .atDay(1)
                        .atStartOfDay(ZoneId.systemDefault())
                        .toInstant()
                );
        var lastDay = Date
                .from(yearMonth
                        .atEndOfMonth()
                        .atStartOfDay(ZoneId.systemDefault())
                        .toInstant()
                );

        return List.of(
                firstDay,
                lastDay
        );

    }

    private Date getSimplyDate(Date date) {
        try {
            format.setTimeZone(TimeZone.getTimeZone("UTC"));
            return DateUtils.truncate(format.parse(format.format(date)), Calendar.DATE);
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }
    }

    private LocalDateTime convertToLocalDateTime(Date dateToConvert) {
        return dateToConvert.toInstant()
                .atZone(ZoneId.systemDefault())
                .toLocalDateTime();
    }

    private void setAccountDetailsFields(EmployeeUpdatePayloadDTO payloadDTO, AccountDetails accountDetails) {
        accountDetails.setName(payloadDTO.firstName());
        accountDetails.setSurname(payloadDTO.lastName());
        accountDetails.setMiddleName(payloadDTO.middleName());
        accountDetails.setSex(payloadDTO.sex());
        accountDetails.setBirthDate(payloadDTO.birthDate());
        accountDetails.setBirthPlace(payloadDTO.birthPlace());
        accountDetails.setEmploymentDate(payloadDTO.employmentDate());
        accountDetails.setExitDate(payloadDTO.exitDate());
        accountDetails.setApartmentNumber(payloadDTO.apartmentNumber());
        accountDetails.setHouseNumber(payloadDTO.houseNumber());
        accountDetails.setStreet(payloadDTO.street());
        accountDetails.setCity(payloadDTO.city());
        accountDetails.setPostalCode(payloadDTO.postalCode());
        accountDetails.setCountry(payloadDTO.country());
        accountDetails.setPesel(payloadDTO.pesel());
        accountDetails.setTaxNumber(payloadDTO.accountNumber());
        accountDetails.setIdCardNumber(payloadDTO.idCardNumber());
        accountDetails.setPhoneNumber(payloadDTO.phoneNumber());
        accountDetails.setPrivateEmail(payloadDTO.privateEmail());
        accountDetails.setWorkingTime(payloadDTO.workingTime());
        accountDetails.setWage(payloadDTO.wage());
        accountDetails.setPayday(payloadDTO.payday());
        accountDetails.setContractType(payloadDTO.contractType());
    }

    private void setAccountFields(EmployeeUpdatePayloadDTO payloadDTO, Account account) {
        if(payloadDTO.active() != null){
            if (Boolean.TRUE.equals(payloadDTO.active())) {
                account.setStatus(StatusType.ENABLE);
            }
            account.setStatus(StatusType.EXPIRED);
        }
        if (payloadDTO.positionId() != null){
            var position = positionRepository.findById(payloadDTO.positionId()).orElseThrow();
            account.setPosition(position);
        }
        if (payloadDTO.email() != null){
            account.setEmail(payloadDTO.email());
        }
    }
}