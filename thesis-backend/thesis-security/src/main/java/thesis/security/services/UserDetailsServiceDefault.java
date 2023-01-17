package thesis.security.services;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import thesis.data.account.AccountRepository;
import thesis.data.account.model.Account;
import thesis.data.project.AccountProjectRepository;
import thesis.data.project.model.AccountProject;
import thesis.security.services.mapper.UserDetailsMapper;

@Slf4j
@AllArgsConstructor
@Service
public class UserDetailsServiceDefault implements UserDetailsService {

    private final AccountRepository accountRepository;
    private final AccountProjectRepository accountProjectRepository;
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
