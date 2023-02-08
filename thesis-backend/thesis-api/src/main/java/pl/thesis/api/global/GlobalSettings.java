package pl.thesis.api.global;

import lombok.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.Map;

@Slf4j
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Component
public class GlobalSettings {

    private Boolean requireConfirmationOnTaskSubmission = true;
    private Boolean showDashboards = true;
    private Boolean showProjects = true;
    private Map<String, String> defaultPage = Map.of("1", "Dashboard");
    private String companyName = "Amazement";
    private String regon = "000000000";
    private String nip = "0000000000";
    private String krs = "0000000000";
    private String image = "";
    private String description = "Example project description";
    private String street = "Podchorążych";
    private String houseNumber = "2";
    private String apartmentNumber = "1";
    private String city = "Kraków";
    private String postalCode = "30-048";
    private Map<String, String> country = Map.of("175", "\uD83C\uDDF5\uD83C\uDDF1 Poland");
    private String phoneNumber = "000-000-000";
    private String email = "company.mail@mail.com";
    private String website = "company.com";

    public void updateSettings(PayloadGlobalSettings settings){
        this.requireConfirmationOnTaskSubmission = settings.requireConfirmationOnTaskSubmission();
        this.showDashboards = settings.showDashboards();
        this.showProjects = settings.showProjects();
        this.defaultPage = settings.defaultPage();
        this.companyName = settings.companyName();
        this.regon = settings.regon();
        this.nip = settings.nip();
        this.krs = settings.krs();
        this.image = settings.image();
        this.description = settings.description();
        this.street = settings.street();
        this.houseNumber = settings.houseNumber();
        this.apartmentNumber = settings.apartmentNumber();
        this.city = settings.city();
        this.postalCode = settings.postalCode();
        this.country = settings.country();
        this.phoneNumber = settings.phoneNumber();
        this.email = settings.email();
        this.website = settings.website();

    }
}
