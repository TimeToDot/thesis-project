package thesis.domain.account;

import lombok.Getter;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import thesis.data.account.AccountRepository;
import thesis.domain.account.model.AccountDto;


@Getter
@Service
public class AccountService implements UserDetailsService { // TODO: creating non-default

  private final AccountRepository accountRepository;
  private final AccountMapper mapper;


  public AccountService(AccountRepository accountRepository, AccountMapper accountMapper) {
    this.accountRepository = accountRepository;
    this.mapper = accountMapper;
  }
  public AccountDto getAccountDataByLogin(String login){
    var account = accountRepository.findByLogin(login).orElseThrow(() -> new UsernameNotFoundException("Account not found with this login: " + login));
    return mapper.map(account);
  }

  @Override
  public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
    return null;
  }
}