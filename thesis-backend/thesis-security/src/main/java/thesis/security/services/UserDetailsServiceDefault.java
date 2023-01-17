package thesis.security.services;

import lombok.AllArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import thesis.data.account.AccountRepository;
import thesis.data.account.model.Account;
import thesis.security.services.mapper.UserDetailsMapper;

@AllArgsConstructor
@Service
public class UserDetailsServiceDefault implements UserDetailsService {

    private final AccountRepository accountRepository;
    private final UserDetailsMapper userDetailsMapper;

    @Transactional
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        Account account = accountRepository
                .findByEmail(username)
                .orElseThrow(() -> new UsernameNotFoundException("User not found with username: " + username));

        return userDetailsMapper.map(account);
    }
}
