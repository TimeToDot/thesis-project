package thesis.data.account.model;

public enum BillingPeriod {

    WEEK("Week"),
    TWO_WEEKS("2 Weeks"),
    MONTH("Month"),
    SEASON("Season");

    public final String label;

    private BillingPeriod(String label) {
        this.label = label;
    }
}
