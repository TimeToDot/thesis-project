package pl.thesis.domain.employee;

import lombok.AllArgsConstructor;
import org.apache.commons.lang3.time.DateUtils;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pl.thesis.data.account.*;
import pl.thesis.data.account.model.*;
import pl.thesis.data.position.PositionRepository;
import pl.thesis.data.position.model.Position;
import pl.thesis.data.project.AccountProjectRepository;
import pl.thesis.data.project.ProjectRepository;
import pl.thesis.data.project.model.AccountProject;
import pl.thesis.data.task.TaskFormRepository;
import pl.thesis.data.task.TaskRepository;
import pl.thesis.data.task.model.Task;
import pl.thesis.data.task.model.TaskStatus;
import pl.thesis.domain.employee.mapper.*;
import pl.thesis.domain.employee.model.*;
import pl.thesis.domain.paging.PagingHelper;
import pl.thesis.domain.paging.PagingSettings;
import pl.thesis.domain.task.model.TaskStatusDTO;

import javax.annotation.PostConstruct;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.*;
import java.util.*;
import java.util.stream.Collectors;


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
    private final EmployeeTasksDTOMapper employeeTasksDTOMapper;
    private final EmployeeTaskDTOMapper employeeTaskDTOMapper;
    private final PositionRepository positionRepository;
    private final ContractTypeRepository contractTypeRepository;
    private final PasswordEncoder passwordEncoder;
    private final SexRepository sexRepository;
    private final CountryRepository countryRepository;

    private final SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
    private final SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

    @PostConstruct
    private void init(){
        format.setTimeZone(TimeZone.getTimeZone("UTC"));
    }

    @Transactional
    public EmployeeDTO getEmployee(UUID id) {
        var account = accountRepository.findById(id).orElseThrow();
        var details = accountDetailsRepository.findByAccount(account).orElseThrow();
        Position position = positionRepository.findByAccountsEquals(account).orElse(null);

        details.getAccount().setPosition(position);

        return employeeDTOMapper.map(details);
    }

    public EmployeesDTO getEmployees(PagingSettings settings, Boolean active) {
        var status = Boolean.TRUE.equals(active) ? StatusType.ENABLE : StatusType.EXPIRED;

        var employees = accountRepository.findAllByStatus(status, settings.getPageable());
        var paging = PagingHelper.getPaging(settings, employees);
        var sorting = PagingHelper.getSorting(settings);

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
    public UUID updateEmployee(EmployeeUpdatePayloadDTO payloadDTO) throws ParseException {
        var account = accountRepository.findById(payloadDTO.id()).orElseThrow();
        var accountDetails = accountDetailsRepository.findByAccount(account).orElseThrow();
        Sex sex = sexRepository.findByName(payloadDTO.sex().name()).orElseThrow();
        Country country = countryRepository
                .findByName(payloadDTO.country().name())
                .orElseGet(() -> {
                    var c = Country.builder()
                            .id(payloadDTO.country().id())
                            .name(payloadDTO.country().name())
                            .build();

                    countryRepository.save(c);

                    return c;
                });
        ContractType contractType = contractTypeRepository.findByName(payloadDTO.contractType().name()).orElseThrow();

        setAccountFields(payloadDTO, account);
        setAccountDetailsFields(payloadDTO, accountDetails, sex, country, contractType);
        //var accountDetails = employeeDTOMapper.mapToDetails(payloadDTO); //check mappers fith instance

        accountRepository.save(account);
        accountDetailsRepository.save(accountDetails);

        return account.getId();
    }

    @Transactional
    public UUID updateEmployeePassword(PasswordUpdatePayloadDTO payloadDTO){
        var account = accountRepository.findById(payloadDTO.id()).orElseThrow();

        account.setPass(passwordEncoder.encode(payloadDTO.password()));

        accountRepository.save(account);

        return account.getId();
    }

    public EmployeeProjectsDTO getEmployeeProjects(UUID id, PagingSettings pagingSettings) {
        if (!accountRepository.existsById(id)) {
            throw new UsernameNotFoundException("employee not found");
        }

        var account = accountRepository.findById(id).orElseThrow();
        var accountProjects = accountProjectRepository.findAllByAccountId(account.getId(), pagingSettings.getPageable()).orElseThrow();

        var paging = PagingHelper.getPaging(pagingSettings, accountProjects);
        var sorting = PagingHelper.getSorting(pagingSettings);

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
                        TaskStatus.LOGGED,
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
    public void sendProjectsToApprove(EmployeeProjectsApproveTemp dto, Date startDate, Date endDate) {
        var tasks = taskRepository
                .findByAccountIdAndStatusAndDateFromBetween(
                        dto.id(),
                        TaskStatus.LOGGED,
                        startDate,
                        endDate
                )
                .orElseThrow();

        dto.projects().forEach(projectId -> {
            tasks.stream()
                    .filter(task -> task.getForm().getProject().getId().compareTo(projectId) == 0)
                    .forEach(task -> task.setStatus(TaskStatus.PENDING));

            taskRepository.saveAll(tasks);

        });

    }

    public EmployeeTasksDTO getEmployeeTasks(UUID employeeId, Date startDate, Date endDate, PagingSettings settings) {
        if (settings == null) {
            settings = new PagingSettings();
        }

        var tasks = taskRepository.findByAccountIdAndDateFromBetween(employeeId, startDate, endDate, settings.getPageable()).orElseThrow();

        var paging = PagingHelper.getPaging(settings, tasks);

        var sorting = PagingHelper.getSorting(settings);

        return EmployeeTasksDTO.builder()
                .tasks(employeeTasksDTOMapper.map(tasks.stream().toList()))
                .paging(paging)
                .sorting(sorting)
                .build();
    }

    public EmployeeTaskDTO getTask(UUID employeeId, UUID taskId) {
        var task = taskRepository.findById(taskId).orElseThrow();

        if (task.getAccount().getId().compareTo(employeeId) != 0){
            throw new RuntimeException("Task doesnt belong to employee with this positionId");
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
    public UUID updateTask(EmployeeTaskUpdatePayloadDTO payloadDTO) {
        var account = accountRepository.findById(payloadDTO.employeeId()).orElseThrow();
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

    @Transactional
    public void deleteTask(UUID employeeId, UUID taskId) {
        var account = accountRepository.findById(employeeId).orElseThrow();

        var task = taskRepository.findById(taskId).orElseThrow();

        if (!account.getTasks().contains(task)){
            throw new RuntimeException("this task doesn't belong to user");
        }
        if (!(task.getStatus().compareTo(TaskStatus.LOGGED) == 0 || task.getStatus().compareTo(TaskStatus.REJECTED) == 0)){
            throw new RuntimeException("this task cannot be deleted!");
        }

        account.getTasks().removeIf(t -> t.getId().compareTo(task.getId()) == 0);

        accountRepository.save(account);

        taskRepository.deleteById(task.getId());
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

    public UUID getEmployeeIdByProjectEmployee(UUID projectEmployeeId){
        var accountProject = accountProjectRepository.findById(projectEmployeeId).orElseThrow();

        return accountProject.getAccount().getId();

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

    public List<ContractDTO> getContractTypes(){
        var contractTypes = contractTypeRepository.findAll();

        return contractTypes.stream()
                .map(this::getContractDTO)
                .toList();
    }

    private ContractDTO getContractDTO(ContractType contractType) {
        return ContractDTO.builder()
                .id(contractType.getId())
                .name(contractType.getName())
                .build();
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
        var lDate = LocalDate.parse(sdf.format(lastDay));
        var endOfDay = LocalDateTime.of(lDate, LocalTime.MAX);
        var zdt = ZonedDateTime.of(endOfDay, ZoneId.systemDefault());
        lastDay = Date.from(zdt.toInstant());

        return List.of(
                firstDay,
                lastDay
        );

    }
    public Date getMonth(Date date) throws ParseException {
        var monthFormat  = new SimpleDateFormat("MM-yyyy");
        monthFormat.setTimeZone(TimeZone.getTimeZone("UTC"));

        return monthFormat.parse(monthFormat.format(date));
    }

    private Date getSimplyDate(Date date) {
        try {
            return DateUtils.truncate(format.parse(format.format(date)), Calendar.DATE);
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }
    }

    private void setAccountDetailsFields(EmployeeUpdatePayloadDTO payloadDTO, AccountDetails accountDetails, Sex sex, Country country, ContractType contractType) throws ParseException {
        accountDetails.setName(payloadDTO.firstName());
        accountDetails.setSurname(payloadDTO.lastName());
        accountDetails.setMiddleName(payloadDTO.middleName());
        accountDetails.setSex(sex);
        accountDetails.setBirthDate(formatDate(payloadDTO.birthDate()));
        accountDetails.setBirthPlace(payloadDTO.birthPlace());
        accountDetails.setEmploymentDate(formatDate(payloadDTO.employmentDate()));
        accountDetails.setExitDate(formatDate(payloadDTO.exitDate()));
        accountDetails.setApartmentNumber(payloadDTO.apartmentNumber());
        accountDetails.setHouseNumber(payloadDTO.houseNumber());
        accountDetails.setStreet(payloadDTO.street());
        accountDetails.setCity(payloadDTO.city());
        accountDetails.setPostalCode(payloadDTO.postalCode());
        accountDetails.setCountry(country);
        accountDetails.setPesel(payloadDTO.pesel());
        accountDetails.setTaxNumber(payloadDTO.accountNumber());
        accountDetails.setIdCardNumber(payloadDTO.idCardNumber());
        accountDetails.setPhoneNumber(payloadDTO.phoneNumber());
        accountDetails.setPrivateEmail(payloadDTO.privateEmail());
        accountDetails.setWorkingTime(payloadDTO.workingTime());
        accountDetails.setWage(payloadDTO.wage());
        accountDetails.setPayday(payloadDTO.payday());
        accountDetails.setContractType(contractType);
    }

    private Date formatDate(Date date) throws ParseException {
        if (date != null) {
            return format.parse(format.format(date));
        }
        return null;
    }

    private void setAccountFields(EmployeeUpdatePayloadDTO payloadDTO, Account account) {
        if(payloadDTO.active() != null){
            if (Boolean.TRUE.equals(payloadDTO.active())) {
                account.setStatus(StatusType.ENABLE);
            }
            else {
                account.setStatus(StatusType.EXPIRED);
            }
        }
        if (payloadDTO.position().id() != null){
            var position = positionRepository.findById(payloadDTO.position().id()).orElseThrow();
            account.setPosition(position);
        }
        if (payloadDTO.email() != null){
            account.setEmail(payloadDTO.email());
        }
    }
}