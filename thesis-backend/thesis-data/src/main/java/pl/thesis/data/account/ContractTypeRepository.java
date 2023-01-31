package pl.thesis.data.account;

import org.springframework.data.jpa.repository.JpaRepository;
import pl.thesis.data.account.model.ContractType;

import java.util.Optional;

public interface ContractTypeRepository extends JpaRepository<ContractType, Integer> {

    Optional<ContractType> findByName(String name);
}
