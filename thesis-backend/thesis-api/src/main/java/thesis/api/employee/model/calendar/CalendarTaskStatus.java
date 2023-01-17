package thesis.api.employee.model.calendar;

import com.fasterxml.jackson.annotation.JsonValue;

public enum CalendarTaskStatus {
    PENDING ("pending"),
    REJECTED ("rejected"),
    LOGGED ("logged"),
    APPROVED ("approved"),
    NONE ("none");

    private String value;

    private CalendarTaskStatus(String value){
        this.value = value;
    }

    @JsonValue
    public String getValue() {
        return value;
    }
}
