package thesis.security.services.model;

public enum ContractTypeSecurity {

    EMPLOYMENT_CONTRACT("Employment contract"),
    COMMISSION_CONTRACT("Commission contract"),
    SPECIFIC_TASK_CONTRACT("Specific-task contract"),
    B2B("B2B");

    public final String label;

    private ContractTypeSecurity(String label) {
        this.label = label;
    }
}
