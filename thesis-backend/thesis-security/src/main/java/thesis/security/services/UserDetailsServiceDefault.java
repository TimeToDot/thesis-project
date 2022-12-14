package thesis.security.services;

import lombok.AllArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import thesis.data.account.AccountRepository;
import thesis.data.account.model.Account;
import thesis.security.services.mapper.UserDetailsMapper;

@AllArgsConstructor
@Service
public class UserDetailsServiceDefault implements UserDetailsService {

    private final AccountRepository accountRepository;
    private final UserDetailsMapper userDetailsMapper;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        var ifExist = accountRepository.existsByLogin(username);

        Account account = accountRepository
                .findByLogin(username)
                .orElseThrow(() -> new UsernameNotFoundException("User not found with username: " + username));

        var userDetails = userDetailsMapper.map(account); // TODO: 02/12/2022 unifying after test

        return userDetails;
    }
}
