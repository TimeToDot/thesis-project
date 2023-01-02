package thesis.api.employee.model;

import java.util.UUID;

public record EmployeeProjectResponse(
        UUID id,
        String name,
        String imagePath
){

}
