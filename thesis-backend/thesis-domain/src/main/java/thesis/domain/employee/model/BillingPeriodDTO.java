package thesis.domain.employee.model;

public enum BillingPeriodDTO {

    WEEK("Week"),
    TWO_WEEKS("2 Weeks"),
    MONTH("Month"),
    SEASON("Season");

    public final String label;

    private BillingPeriodDTO(String label) {
        this.label = label;
    }
}
