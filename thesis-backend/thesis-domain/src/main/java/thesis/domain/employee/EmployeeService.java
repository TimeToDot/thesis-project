package thesis.domain.employee;

import lombok.Getter;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import thesis.data.account.AccountRepository;
import thesis.domain.employee.model.Employee;


@Getter
@Service
public class EmployeeService {
}