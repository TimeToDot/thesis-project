package thesis.domain.task.model;

import com.fasterxml.jackson.annotation.JsonValue;

public enum TaskStatusDTO {
    PENDING ("pending"),
    REJECTED ("rejected"),
    LOGGED ("logged"),
    APPROVED ("approved"),
    NONE ("none");

    private String value;

    private TaskStatusDTO(String value){
        this.value = value;
    }

    @JsonValue
    public String getValue() {
        return value;
    }
}
