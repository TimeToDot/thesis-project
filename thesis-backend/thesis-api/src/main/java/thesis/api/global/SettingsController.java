package thesis.api.global;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import thesis.api.ThesisController;
import thesis.domain.employee.EmployeeService;
import thesis.domain.employee.model.BillingPeriodDTO;
import thesis.domain.project.ProjectService;
import thesis.domain.project.model.task.ProjectTaskCreatePayloadDTO;
import thesis.security.services.model.ContractDTO;
import thesis.security.services.model.ContractTypeDTO;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.stream.Collectors;

@Slf4j
@AllArgsConstructor
@RestController
@RequestMapping("/api")
public class SettingsController extends ThesisController {

    private final GlobalSettings globalSettings;
    private final EmployeeService employeeService;

    private final ProjectService projectService;

    @PreAuthorize("hasAuthority('CAN_READ')")
    @GetMapping("/global-settings")
    public ResponseEntity<GlobalSettings> getGlobalSettings(){

        return ResponseEntity.ok(globalSettings);
    }

    @PreAuthorize("hasAuthority('CAN_ADMIN_SETTINGS')")
    @PutMapping("/global-settings")
    public ResponseEntity<GlobalSettings> updateGlobalSettings(
            @RequestBody PayloadGlobalSettings payload
    ){
        globalSettings.updateSettings(payload);

        return ResponseEntity.ok(globalSettings);

    }

    @PreAuthorize("hasAuthority('CAN_ADMIN_USERS')")
    @GetMapping("/contract-types")
    public ResponseEntity<List<ContractDTO>> getContractTypes(
            @RequestHeader(required = false) UUID employeeId,
            @RequestHeader(required = false) UUID projectId
    ) {
        var response = employeeService.getContractTypes();

        return ResponseEntity.ok(response);
    }

    @PreAuthorize("hasAuthority('CAN_ADMIN_USERS')")
    @GetMapping("/billing-periods")
    public ResponseEntity<List<BillingPeriodDTO>> getBillingPeriods(
            @RequestHeader(required = false) UUID employeeId,
            @RequestHeader(required = false) UUID projectId
    ) {
        var response = projectService.getBillingPeriods();

        return ResponseEntity.ok(response);
    }
}
