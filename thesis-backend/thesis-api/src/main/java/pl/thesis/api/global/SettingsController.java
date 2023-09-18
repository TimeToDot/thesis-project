package pl.thesis.api.global;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import pl.thesis.api.ThesisController;
import pl.thesis.api.global.model.BillingPeriodResponse;
import pl.thesis.api.global.model.ContractResponse;
import pl.thesis.domain.project.BillingPeriodService;
import pl.thesis.domain.setting.SettingService;

import java.util.List;

@Slf4j
@AllArgsConstructor
@RestController
@RequestMapping("/api")
public class SettingsController extends ThesisController {

    private final GlobalSettings globalSettings;
    private final SettingService settingService;
    private final BillingPeriodService billingPeriodService;
    private final GlobalMapper globalMapper;

    @PreAuthorize("hasAuthority('CAN_ADMIN_SETTINGS')")
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
    public ResponseEntity<List<ContractResponse>> getContractTypes() {
        var dto = settingService.getContractTypes();
        var response = dto.stream().map(globalMapper::mapToContractTypeResponse).toList();

        return ResponseEntity.ok(response);
    }

    @PreAuthorize("hasAuthority('CAN_ADMIN_USERS')")
    @GetMapping("/billing-periods")
    public ResponseEntity<List<BillingPeriodResponse>> getBillingPeriods() {
        var dto = billingPeriodService.getBillingPeriods();
        var response = dto.stream().map(globalMapper::mapToBillingPeriodResponse).toList();

        return ResponseEntity.ok(response);
    }
}
