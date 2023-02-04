package thesis.domain.employee.mapper;

import java.text.SimpleDateFormat;
import java.util.List;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;
import thesis.data.account.model.Account;
import thesis.data.account.model.AccountDetails;
import thesis.data.position.model.Position;
import thesis.domain.employee.model.EmployeeDTO;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2023-02-04T17:16:36+0100",
    comments = "version: 1.5.3.Final, compiler: Eclipse JDT (IDE) 3.33.0.v20221215-1352, environment: Java 17.0.5 (Eclipse Adoptium)"
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
        List<String> positionIds = null;
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
        List<Position> positions = detailsAccountPositions( details );
        positionIds = map( positions );
        sex = details.getSex();

        String image = null;
        String birthDate = null;
        String country = null;

        EmployeeDTO employeeDTO = new EmployeeDTO( firstName, lastName, email, password, positionIds, employmentDate, image, sex, birthDate, phoneNumber, city, street, postalCode, country, accountNumber );

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

    private List<Position> detailsAccountPositions(AccountDetails accountDetails) {
        if ( accountDetails == null ) {
            return null;
        }
        Account account = accountDetails.getAccount();
        if ( account == null ) {
            return null;
        }
        List<Position> positions = account.getPositions();
        if ( positions == null ) {
            return null;
        }
        return positions;
    }
}
