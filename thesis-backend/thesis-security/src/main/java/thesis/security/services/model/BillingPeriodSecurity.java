package thesis.security.services.model;

public enum BillingPeriodSecurity {
    WEEK("Week"),
    TWO_WEEKS("2 Weeks"),
    MONTH("Month"),
    SEASON("Season");

    public final String label;

    private BillingPeriodSecurity(String label) {
        this.label = label;
    }
}
