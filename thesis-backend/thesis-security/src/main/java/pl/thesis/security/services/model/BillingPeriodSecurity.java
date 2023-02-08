package pl.thesis.security.services.model;

import com.fasterxml.jackson.annotation.JsonValue;

import java.util.Arrays;

public enum BillingPeriodSecurity {
    WEEK("Week"),
    TWO_WEEKS("2 Weeks"),
    MONTH("Month"),
    SEASON("Season");

    public final String label;

    private BillingPeriodSecurity(String label) {
        this.label = label;
    }

    @JsonValue
    public String getLabel(){
        return label;
    }

    public static BillingPeriodSecurity fromValue(String value){
        var contractTypes = BillingPeriodSecurity.values();

        return Arrays.stream(contractTypes).filter(contractTypeDTO -> contractTypeDTO.label.equals(value)).findFirst().orElseThrow();
    }
}
