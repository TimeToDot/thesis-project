package thesis.data.account;

import org.springframework.data.jpa.repository.JpaRepository;
import thesis.data.account.model.Contract;
import thesis.data.account.model.Sex;

import java.util.Optional;

public interface ContractRepository extends JpaRepository<Contract, Integer> {

    Optional<Contract> findByName(String name);
}
