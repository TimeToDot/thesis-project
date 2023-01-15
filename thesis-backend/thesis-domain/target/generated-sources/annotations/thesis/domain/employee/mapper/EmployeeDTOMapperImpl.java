package thesis.domain.employee.mapper;

import java.text.SimpleDateFormat;
import java.util.UUID;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;
import thesis.data.account.model.Account;
import thesis.data.account.model.AccountDetails;
import thesis.data.position.model.Position;
import thesis.domain.employee.model.EmployeeDTO;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2023-01-15T17:16:22+0100",
    comments = "version: 1.5.3.Final, compiler: javac, environment: Java 17.0.5 (Private Build)"
)
@Component
public class EmployeeDTOMapperImpl implements EmployeeDTOMapper {

    @Override
    public EmployeeDTO map(AccountDetails details) {
        if ( details == null ) {
            return null;
        }

        String firstName = null;
        String lastName = null;
        String email = null;
        String password = null;
        String city = null;
        String postalCode = null;
        String street = null;
        String accountNumber = null;
        String employmentDate = null;
        String phoneNumber = null;
        UUID positionId = null;
        String sex = null;

        firstName = details.getName();
        lastName = details.getSurname();
        email = detailsAccountEmail( details );
        password = detailsAccountPass( details );
        city = details.getCity();
        postalCode = details.getPostalCode();
        street = details.getStreet();
        accountNumber = details.getTaxNumber();
        if ( details.getCreatedAt() != null ) {
            employmentDate = new SimpleDateFormat().format( details.getCreatedAt() );
        }
        phoneNumber = details.getPhoneNumber();
        positionId = detailsAccountPositionId( details );
        sex = details.getSex();

        String image = null;
        String birthDate = null;
        String country = null;

        EmployeeDTO employeeDTO = new EmployeeDTO( firstName, lastName, email, password, positionId, employmentDate, image, sex, birthDate, phoneNumber, city, street, postalCode, country, accountNumber );

        return employeeDTO;
    }

    private String detailsAccountEmail(AccountDetails accountDetails) {
        if ( accountDetails == null ) {
            return null;
        }
        Account account = accountDetails.getAccount();
        if ( account == null ) {
            return null;
        }
        String email = account.getEmail();
        if ( email == null ) {
            return null;
        }
        return email;
    }

    private String detailsAccountPass(AccountDetails accountDetails) {
        if ( accountDetails == null ) {
            return null;
        }
        Account account = accountDetails.getAccount();
        if ( account == null ) {
            return null;
        }
        String pass = account.getPass();
        if ( pass == null ) {
            return null;
        }
        return pass;
    }

    private UUID detailsAccountPositionId(AccountDetails accountDetails) {
        if ( accountDetails == null ) {
            return null;
        }
        Account account = accountDetails.getAccount();
        if ( account == null ) {
            return null;
        }
        Position position = account.getPosition();
        if ( position == null ) {
            return null;
        }
        UUID id = position.getId();
        if ( id == null ) {
            return null;
        }
        return id;
    }
}
