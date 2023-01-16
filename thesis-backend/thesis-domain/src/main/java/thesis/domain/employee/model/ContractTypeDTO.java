package thesis.domain.employee.model;

public enum ContractTypeDTO {
    EMPLOYMENT_CONTRACT("Employment contract"),
    COMMISSION_CONTRACT("Commission contract"),
    SPECIFIC_TASK_CONTRACT("Specific-task contract"),
    B2B("B2B");

    public final String label;

    private ContractTypeDTO(String label) {
        this.label = label;
    }
}
