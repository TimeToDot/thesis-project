package thesis.data.account.model;

import java.util.Arrays;

public enum ContractType {
    EMPLOYMENT_CONTRACT("Employment contract"),
    COMMISSION_CONTRACT("Commission contract"),
    SPECIFIC_TASK_CONTRACT("Specific-task contract"),
    B2B("B2B");

    public final String label;

    private ContractType(String label) {
        this.label = label;
    }

    public static ContractType fromValue(String value){
        var contractTypes = ContractType.values();

        return Arrays.stream(contractTypes).filter(contractTypeDTO -> contractTypeDTO.label.equals(value)).findFirst().orElseThrow();
    }
}
