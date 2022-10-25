package thesis.domain.account;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import thesis.data.account.AccountDetailsRepository;
import thesis.data.account.AccountRepository;
import thesis.domain.account.model.AccountData;

@AllArgsConstructor
@Getter
@Service
public class AccountService implements UserDetailsService {

  private final AccountRepository accountRepository;
  private final AccountMapper mapper;

  public AccountData getAccountDataByLogin(String login){
    var account = accountRepository.findByLogin(login).orElseThrow(() -> new UsernameNotFoundException("Account not found with this login: " + login));
    return mapper.map(account, account.getDetails());
  }

  @Override
  public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
    return null;
  }
}