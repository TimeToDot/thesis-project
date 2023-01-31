package pl.thesis.security.services.model;

import com.fasterxml.jackson.annotation.JsonValue;

import java.util.Arrays;

public enum ContractTypeDTO {
    EMPLOYMENT_CONTRACT("Employment contract"),
    COMMISSION_CONTRACT("Commission contract"),
    SPECIFIC_TASK_CONTRACT("Specific-task contract"),
    B2B("B2B");

    public final String label;

    ContractTypeDTO(String label) {
        this.label = label;
    }

    @JsonValue
    public String getLabel(){
        return label;
    }

    public static ContractTypeDTO fromValue(String value){
        var contractTypes = ContractTypeDTO.values();

        return Arrays.stream(contractTypes).filter(contractTypeDTO -> contractTypeDTO.label.equals(value)).findFirst().orElseThrow();
    }
}
