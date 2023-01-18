package thesis.data.account.model;

import java.util.Arrays;

public enum BillingPeriodType {

    WEEK("Week"),
    TWO_WEEKS("2 Weeks"),
    MONTH("Month"),
    SEASON("Season");

    public final String label;

    private BillingPeriodType(String label) {
        this.label = label;
    }

    public static BillingPeriodType fromValue(String value){
        var billingPeriodTypes = BillingPeriodType.values();

        return Arrays.stream(billingPeriodTypes).filter(contractTypeDTO -> contractTypeDTO.label.equals(value)).findFirst().orElseThrow();
    }
}
