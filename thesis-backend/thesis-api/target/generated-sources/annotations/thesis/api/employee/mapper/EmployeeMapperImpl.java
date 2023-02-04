package thesis.api.employee.mapper;

import java.util.Date;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;
import thesis.api.employee.model.EmployeeResponse;
import thesis.domain.employee.model.EmployeeDTO;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2023-02-04T17:16:43+0100",
    comments = "version: 1.5.3.Final, compiler: Eclipse JDT (IDE) 3.33.0.v20221215-1352, environment: Java 17.0.5 (Eclipse Adoptium)"
)
@Component
public class EmployeeMapperImpl implements EmployeeMapper {

    @Override
    public EmployeeResponse map(EmployeeDTO employeeDTO) {
        if ( employeeDTO == null ) {
            return null;
        }

        String firstName = null;
        String lastName = null;
        String email = null;
        String password = null;
        String position = null;
        Date employmentDate = null;
        String imagePath = null;
        String sex = null;
        String birthDate = null;
        String phoneNumber = null;
        String city = null;
        String street = null;
        String postalCode = null;
        String country = null;
        String accountNumber = null;

        EmployeeResponse employeeResponse = new EmployeeResponse( firstName, lastName, email, password, position, employmentDate, imagePath, sex, birthDate, phoneNumber, city, street, postalCode, country, accountNumber );

        return employeeResponse;
    }
}
