package thesis.security.services.mapper;

import java.util.Collection;
import java.util.UUID;
import javax.annotation.processing.Generated;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Component;
import thesis.data.account.model.Account;
import thesis.data.account.model.StatusType;
import thesis.security.services.model.UserDetailsDefault;
import thesis.security.services.model.UserStatusType;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2023-02-04T17:16:39+0100",
    comments = "version: 1.5.3.Final, compiler: Eclipse JDT (IDE) 3.33.0.v20221215-1352, environment: Java 17.0.5 (Eclipse Adoptium)"
)
@Component
public class UserDetailsMapperImpl implements UserDetailsMapper {

    @Override
    public UserDetailsDefault map(Account account) {
        if ( account == null ) {
            return null;
        }

        UUID id = null;
        String username = null;
        String password = null;
        String email = null;
        UserStatusType status = null;

        id = account.getId();
        username = account.getLogin();
        password = account.getPass();
        email = account.getEmail();
        status = statusTypeToUserStatusType( account.getStatus() );

        Collection<GrantedAuthority> authorities = getAuthorities(account);

        UserDetailsDefault userDetailsDefault = new UserDetailsDefault( id, username, email, password, authorities, status );

        return userDetailsDefault;
    }

    protected UserStatusType statusTypeToUserStatusType(StatusType statusType) {
        if ( statusType == null ) {
            return null;
        }

        UserStatusType userStatusType;

        switch ( statusType ) {
            case DISABLE: userStatusType = UserStatusType.DISABLE;
            break;
            case ENABLE: userStatusType = UserStatusType.ENABLE;
            break;
            case EXPIRED: userStatusType = UserStatusType.EXPIRED;
            break;
            case LOCKED: userStatusType = UserStatusType.LOCKED;
            break;
            default: throw new IllegalArgumentException( "Unexpected enum constant: " + statusType );
        }

        return userStatusType;
    }
}
