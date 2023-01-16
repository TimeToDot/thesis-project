package thesis.data.account.model;

public enum ContractType {
    EMPLOYMENT_CONTRACT("Employment contract"),
    COMMISSION_CONTRACT("Commission contract"),
    SPECIFIC_TASK_CONTRACT("Specific-task contract"),
    B2B("B2B");

    public final String label;

    private ContractType(String label) {
        this.label = label;
    }
}
