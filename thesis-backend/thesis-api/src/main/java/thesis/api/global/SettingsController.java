package thesis.api.global;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import thesis.api.ThesisController;
import thesis.domain.project.model.task.ProjectTaskCreatePayloadDTO;

@Slf4j
@AllArgsConstructor
@RestController
@RequestMapping("/api/global/settings")
public class SettingsController extends ThesisController {

    private final GlobalSettings globalSettings;

    @PreAuthorize("hasAuthority('CAN_READ')")
    @GetMapping
    public ResponseEntity<GlobalSettings> getGlobalSettings(){

        return ResponseEntity.ok(globalSettings);
    }

    @PreAuthorize("hasAuthority('CAN_ADMIN_SETTINGS')")
    @PutMapping
    public ResponseEntity<GlobalSettings> updateGlobalSettings(
            @RequestBody PayloadGlobalSettings payload
    ){
        globalSettings.updateSettings(payload);

        return ResponseEntity.ok(globalSettings);

    }
}
