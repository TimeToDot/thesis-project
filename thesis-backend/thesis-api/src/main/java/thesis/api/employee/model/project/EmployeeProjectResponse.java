package thesis.api.employee.model.project;

import java.util.UUID;

public record EmployeeProjectResponse(
        UUID id,
        String name,
        String imagePath
){

}
