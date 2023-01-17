package thesis.api.global;

import java.util.Map;

public record PayloadGlobalSettings(
        Boolean requireConfirmationOnTaskSubmission,
        Boolean showDashboards,
        Boolean showProjects,
        Map<String, String> defaultPage,
        String companyName,
        String regon,
        String nip,
        String krs,
        String image,
        String description,
        String street,
        String houseNumber,
        String apartmentNumber,
        String city,
        String postalCode,
        Map<String, String> country,
        String phoneNumber,
        String email,
        String website
) {
}
