package thesis.domain.account;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import thesis.data.account.model.Account;
import thesis.data.account.model.AccountDetails;
import thesis.domain.account.model.AccountData;
import thesis.domain.mapper.MapStructConfig;

@Mapper(config = MapStructConfig.class)
public interface AccountMapper {

  @Mapping(target = "email", source = "details.email")
  AccountData map(Account account, AccountDetails details);
}
