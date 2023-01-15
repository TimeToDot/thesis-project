package thesis.api.employee.mapper;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.UUID;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;
import thesis.api.employee.model.EmployeeResponse;
import thesis.domain.employee.model.EmployeeDTO;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2023-01-15T17:16:26+0100",
    comments = "version: 1.5.3.Final, compiler: javac, environment: Java 17.0.5 (Private Build)"
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
        UUID positionId = null;
        Date employmentDate = null;
        String sex = null;
        String birthDate = null;
        String phoneNumber = null;
        String city = null;
        String street = null;
        String postalCode = null;
        String country = null;
        String accountNumber = null;

        firstName = employeeDTO.firstName();
        lastName = employeeDTO.lastName();
        email = employeeDTO.email();
        password = employeeDTO.password();
        positionId = employeeDTO.positionId();
        try {
            if ( employeeDTO.employmentDate() != null ) {
                employmentDate = new SimpleDateFormat().parse( employeeDTO.employmentDate() );
            }
        }
        catch ( ParseException e ) {
            throw new RuntimeException( e );
        }
        sex = employeeDTO.sex();
        birthDate = employeeDTO.birthDate();
        phoneNumber = employeeDTO.phoneNumber();
        city = employeeDTO.city();
        street = employeeDTO.street();
        postalCode = employeeDTO.postalCode();
        country = employeeDTO.country();
        accountNumber = employeeDTO.accountNumber();

        String imagePath = null;

        EmployeeResponse employeeResponse = new EmployeeResponse( firstName, lastName, email, password, positionId, employmentDate, imagePath, sex, birthDate, phoneNumber, city, street, postalCode, country, accountNumber );

        return employeeResponse;
    }
}
