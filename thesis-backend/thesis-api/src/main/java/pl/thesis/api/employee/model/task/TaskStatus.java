package pl.thesis.api.employee.model.task;

import com.fasterxml.jackson.annotation.JsonValue;

public enum TaskStatus {
    PENDING ("pending"),
    REJECTED ("rejected"),
    LOGGED ("logged"),
    APPROVED ("approved"),
    NONE ("none");

    private String value;

    private TaskStatus(String value){
        this.value = value;
    }

    @JsonValue
    public String getValue() {
        return value;
    }
}
