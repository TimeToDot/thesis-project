package thesis.domain.account;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;
import thesis.data.account.model.Account;
import thesis.domain.account.model.AccountDto;
import thesis.domain.mapper.MapStructConfig;

@Mapper(config = MapStructConfig.class)
public interface AccountMapper {

  AccountMapper INSTANCE = Mappers.getMapper(AccountMapper.class);
  AccountDto map(Account account);

}
