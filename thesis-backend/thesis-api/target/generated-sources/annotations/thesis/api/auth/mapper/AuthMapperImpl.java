package thesis.api.auth.mapper;

import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;
import thesis.api.auth.model.AuthenticationResponse;
import thesis.api.auth.model.AuthorizationRequest;
import thesis.security.services.model.AuthenticationDTO;
import thesis.security.services.model.AuthorizationDTO;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2023-01-15T17:16:26+0100",
    comments = "version: 1.5.3.Final, compiler: javac, environment: Java 17.0.5 (Private Build)"
)
@Component
public class AuthMapperImpl implements AuthMapper {

    @Override
    public AuthenticationResponse map(AuthenticationDTO authenticationDTO) {
        if ( authenticationDTO == null ) {
            return null;
        }

        AuthenticationResponse.AuthenticationResponseBuilder authenticationResponse = AuthenticationResponse.builder();

        authenticationResponse.id( authenticationDTO.id() );
        authenticationResponse.username( authenticationDTO.username() );
        authenticationResponse.email( authenticationDTO.email() );
        List<String> list = authenticationDTO.globalAuthorities();
        if ( list != null ) {
            authenticationResponse.globalAuthorities( new ArrayList<String>( list ) );
        }
        Map<UUID, List<String>> map = authenticationDTO.projectPrivileges();
        if ( map != null ) {
            authenticationResponse.projectPrivileges( new LinkedHashMap<UUID, List<String>>( map ) );
        }

        return authenticationResponse.build();
    }

    @Override
    public AuthorizationDTO mapToAuthorizationDTO(AuthorizationRequest authorizationRequest) {
        if ( authorizationRequest == null ) {
            return null;
        }

        String login = null;
        String email = null;
        List<String> roles = null;
        String password = null;
        String firstName = null;
        String lastName = null;
        String sex = null;
        String birthDate = null;
        String birthPlace = null;
        String idCardNumber = null;
        String pesel = null;
        String street = null;
        String houseNumber = null;
        String apartmentNumber = null;
        String city = null;
        String postalCode = null;
        String country = null;
        String phoneNumber = null;
        String privateEmail = null;
        UUID positionId = null;
        Date employmentDate = null;
        String contractType = null;
        Integer workingTime = null;
        Integer wage = null;
        Integer payday = null;
        String accountNumber = null;

        login = authorizationRequest.getLogin();
        email = authorizationRequest.getEmail();
        List<String> list = authorizationRequest.getRoles();
        if ( list != null ) {
            roles = new ArrayList<String>( list );
        }
        password = authorizationRequest.getPassword();
        firstName = authorizationRequest.getFirstName();
        lastName = authorizationRequest.getLastName();
        sex = authorizationRequest.getSex();
        birthDate = authorizationRequest.getBirthDate();
        birthPlace = authorizationRequest.getBirthPlace();
        idCardNumber = authorizationRequest.getIdCardNumber();
        pesel = authorizationRequest.getPesel();
        street = authorizationRequest.getStreet();
        houseNumber = authorizationRequest.getHouseNumber();
        apartmentNumber = authorizationRequest.getApartmentNumber();
        city = authorizationRequest.getCity();
        postalCode = authorizationRequest.getPostalCode();
        country = authorizationRequest.getCountry();
        phoneNumber = authorizationRequest.getPhoneNumber();
        privateEmail = authorizationRequest.getPrivateEmail();
        positionId = authorizationRequest.getPositionId();
        employmentDate = authorizationRequest.getEmploymentDate();
        contractType = authorizationRequest.getContractType();
        workingTime = authorizationRequest.getWorkingTime();
        wage = authorizationRequest.getWage();
        payday = authorizationRequest.getPayday();
        accountNumber = authorizationRequest.getAccountNumber();

        AuthorizationDTO authorizationDTO = new AuthorizationDTO( login, email, roles, password, firstName, lastName, sex, birthDate, birthPlace, idCardNumber, pesel, street, houseNumber, apartmentNumber, city, postalCode, country, phoneNumber, privateEmail, positionId, employmentDate, contractType, workingTime, wage, payday, accountNumber );

        return authorizationDTO;
    }
}
