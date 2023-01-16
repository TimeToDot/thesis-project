package thesis.data.account.model;

import java.util.Arrays;

public enum BillingPeriod {

    WEEK("Week"),
    TWO_WEEKS("2 Weeks"),
    MONTH("Month"),
    SEASON("Season");

    public final String label;

    private BillingPeriod(String label) {
        this.label = label;
    }

    public static BillingPeriod fromValue(String value){
        var contractTypes = BillingPeriod.values();

        return Arrays.stream(contractTypes).filter(contractTypeDTO -> contractTypeDTO.label.equals(value)).findFirst().orElseThrow();
    }
}
