package pl.thesis.security.services;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pl.thesis.data.account.AccountRepository;
import pl.thesis.data.account.model.Account;
import pl.thesis.security.services.mapper.UserDetailsMapper;

@Slf4j
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

        var resp = userDetailsMapper.map(account);
        log.info(resp.getAuthorities().toString());

        return resp;
    }
}
