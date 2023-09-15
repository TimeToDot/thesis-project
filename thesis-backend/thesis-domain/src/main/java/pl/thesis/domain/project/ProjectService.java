package pl.thesis.domain.project;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pl.thesis.data.account.AccountRepository;
import pl.thesis.data.account.model.Account;
import pl.thesis.data.account.model.StatusType;
import pl.thesis.data.position.PositionRepository;
import pl.thesis.data.project.AccountProjectRepository;
import pl.thesis.data.project.BillingPeriodRepository;
import pl.thesis.data.project.ProjectDetailsRepository;
import pl.thesis.data.project.ProjectRepository;
import pl.thesis.data.project.model.*;
import pl.thesis.data.role.RoleRepository;
import pl.thesis.data.role.model.Role;
import pl.thesis.data.role.model.RoleType;
import pl.thesis.data.task.TaskFormDetailsRepository;
import pl.thesis.data.task.TaskFormRepository;
import pl.thesis.data.task.TaskRepository;
import pl.thesis.domain.employee.EmployeeCalendarService;
import pl.thesis.domain.employee.EmployeeService;
import pl.thesis.domain.employee.mapper.EmployeeTasksDTOMapper;
import pl.thesis.domain.paging.PagingSettings;
import pl.thesis.domain.position.PositionMapperDTO;
import pl.thesis.domain.project.mapper.BillingPeriodDTOMapper;
import pl.thesis.domain.project.mapper.ProjectDTOMapper;
import pl.thesis.domain.project.model.ProjectCreatePayloadDTO;
import pl.thesis.domain.project.model.ProjectDTO;
import pl.thesis.domain.project.model.ProjectUpdatePayloadDTO;
import pl.thesis.domain.project.model.ProjectsDTO;
import pl.thesis.domain.project.model.approval.ProjectCalendarDTO;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Slf4j
@AllArgsConstructor
@Service
public class ProjectService {

    private final ProjectRepository projectRepository;
    private final ProjectDetailsRepository projectDetailsRepository;
    private final AccountRepository accountRepository;
    private final AccountProjectRepository accountProjectRepository;
    private final EmployeeService employeeService;
    private final EmployeeCalendarService employeeCalendarService;
    private final RoleRepository roleRepository;
    private final BillingPeriodRepository billingPeriodRepository;
    private final ProjectDTOMapper projectDTOMapper;

    public ProjectDTO getProject(Long projectId){
        var project = projectRepository.findById(projectId).orElseThrow();
        return getProjectDTO(project);
    }

    @Transactional
    public Long addProject(ProjectCreatePayloadDTO payloadDTO){
        var owner = accountRepository.findById(payloadDTO.moderatorId()).orElseThrow();
        var project = getProject(payloadDTO, owner);

        projectRepository.save(project);

        var projectDetails = getProjectDetails(payloadDTO, project);

        projectDetailsRepository.save(projectDetails);

        var projectAdminRole = roleRepository.findByName(RoleType.ROLE_PROJECT_ADMIN).orElseThrow();
        var projectModRole = roleRepository.findByName(RoleType.ROLE_PROJECT_MODERATOR).orElseThrow();
        var adminList = getAdmins();

        assignToProject(project, projectAdminRole, adminList);
        assignToProject(project, projectModRole, List.of(owner));

        return project.getId();
    }

    @Transactional
    public Long updateProject(ProjectUpdatePayloadDTO payloadDTO){
        var project = projectRepository.findById(payloadDTO.projectId()).orElseThrow();
        var projectDetails = projectDetailsRepository.findByProject(project).orElseThrow();
        var newOwner = accountRepository.findById(payloadDTO.moderatorId()).orElseThrow();
        var oldOwner = accountRepository.findById(project.getOwner().getId()).orElseThrow();
        ProjectType status;

        status = payloadDTO.active() ? ProjectType.ACTIVE : ProjectType.INACTIVE;
        boolean isPermissionChangeNeeded = project.getOwner().getId().compareTo(newOwner.getId()) != 0;

        project.setOwner(newOwner);
        project.setName(payloadDTO.name());
        project.setDescription(payloadDTO.description());
        project.setStatus(status);

        projectRepository.save(project);

        projectDetails.setBillingPeriod(
                BillingPeriod.builder()
                        .id(payloadDTO.billingPeriod().id())
                        .name(payloadDTO.name())
                        .build()
        );
        projectDetails.setBonusModifier(payloadDTO.bonusModifier());
        projectDetails.setOvertimeModifier(payloadDTO.overtimeModifier());
        projectDetails.setBonusModifier(payloadDTO.bonusModifier());
        projectDetails.setHolidayModifier(payloadDTO.holidayModifier());
        projectDetails.setNightModifier(payloadDTO.nightModifier());

        if (status.compareTo(ProjectType.INACTIVE) == 0) projectDetails.setArchiveDate(new Date());

        projectDetailsRepository.save(projectDetails);

        if (isPermissionChangeNeeded){
            changeOwners(project, newOwner, oldOwner);
        }

        return project.getId();
    }

    public ProjectsDTO getProjects(Boolean active){
        var status = Boolean.TRUE.equals(active) ? ProjectType.ACTIVE : ProjectType.INACTIVE;

        var projects = projectRepository.findAllByStatus(status).orElseThrow();
        //paging for production needed

        return ProjectsDTO.builder()
                .projects(projects.stream()
                        .map(this::getProjectDTO)
                        .toList())
                .build();
    }


    public ProjectCalendarDTO getProjectCalendar(Long id, Long projectId, Date date) {

        var employeeId = employeeService.getEmployeeIdByProjectEmployee(id);
        var calendarDTO = employeeCalendarService.getEmployeeCalendar(employeeId, projectId, date);

        return ProjectCalendarDTO.builder()
                .employeeId(employeeId)
                .projectEmployeeId(id)
                .tasks(calendarDTO.tasks())
                .build();
    }

    private ProjectDTO getProjectDTO(Project project) {
        var accountsNumber = project.getAccountProjects().stream().map(AccountProject::getAccount).toList().size();

        return projectDTOMapper.map(project, accountsNumber);
    }

    private void assignToProject(Project project, Role projectRole, List<Account> adminList) {
        adminList.forEach(account -> {
            var adminProject = AccountProject.builder()
                    .account(account)
                    .project(project)
                    .role(projectRole)
                    .joinDate(new Date())
                    .status(ProjectAccountStatus.ACTIVE)
                    .build();

            accountProjectRepository.save(adminProject);
        });
    }

    private ArrayList<Account> getAdmins() {
        var size = 100;
        var page = 1;
        var role = roleRepository.findByName(RoleType.ROLE_GLOBAL_ADMIN).orElseThrow();
        //var jpaRole = List.of(role);

        var settings = PagingSettings.builder().page(page).size(size).build();
        var admins = accountRepository.findAllByRoleAndStatus(role, StatusType.ENABLE, settings.getPageable());
        var adminList = new ArrayList<>(admins.stream().toList());

        while (admins.getTotalPages() > page) {
            page += 1;
            settings = PagingSettings.builder().page(page).size(size).build();
            admins = accountRepository.findAllByRoleAndStatus(role, StatusType.ENABLE, settings.getPageable());
            adminList.addAll(admins.stream().toList());
        }

        return adminList;
    }

    private Project getProject(ProjectCreatePayloadDTO payloadDTO, Account owner) {
        return Project.builder()
                .name(payloadDTO.name())
                .description(payloadDTO.description())
                .owner(owner)
                .status(ProjectType.ACTIVE)
                .build();
    }

    private ProjectDetails getProjectDetails(ProjectCreatePayloadDTO payloadDTO, Project project) {
        var billingPeriod = billingPeriodRepository.findById(payloadDTO.billingPeriod().id()).orElseThrow();

        return ProjectDetails.builder()
                .project(project)
                .createdAt(new Date())
                .imagePath(payloadDTO.image())
                .billingPeriod(billingPeriod)
                .overtimeModifier(payloadDTO.overtimeModifier())
                .bonusModifier(payloadDTO.bonusModifier())
                .holidayModifier(payloadDTO.holidayModifier())
                .nightModifier(payloadDTO.nightModifier())
                .build();
    }

    private void changeOwners(Project project, Account newOwner, Account oldOwner) {
        var accountProject = accountProjectRepository.findByAccountIdAndProjectId(oldOwner.getId(), project.getId()).orElseThrow();
        var projectModRole = roleRepository.findByName(RoleType.ROLE_PROJECT_MODERATOR).orElseThrow();

        accountProject.setStatus(ProjectAccountStatus.EXPIRED_MOD);
        // accountProjectRepository.delete(accountProject); currently not necessary
        accountProjectRepository.save(accountProject);

        var newAccountProject = AccountProject.builder()
                .project(project)
                .account(newOwner)
                .joinDate(new Date())
                .status(ProjectAccountStatus.MOD)
                .role(projectModRole)
                .build();

        accountProjectRepository.save(newAccountProject);
    }
}
