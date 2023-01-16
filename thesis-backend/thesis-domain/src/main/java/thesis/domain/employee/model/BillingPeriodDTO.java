package thesis.domain.employee.model;

import java.util.Arrays;

public enum BillingPeriodDTO {

    WEEK("Week"),
    TWO_WEEKS("2 Weeks"),
    MONTH("Month"),
    SEASON("Season");

    public final String label;

    private BillingPeriodDTO(String label) {
        this.label = label;
    }

    public static BillingPeriodDTO fromValue(String value){
        var contractTypes = BillingPeriodDTO.values();

        return Arrays.stream(contractTypes).filter(contractTypeDTO -> contractTypeDTO.label.equals(value)).findFirst().orElseThrow();
    }
}
