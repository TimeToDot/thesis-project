package thesis.data.account;

import org.springframework.data.jpa.repository.JpaRepository;
import thesis.data.account.model.Country;

import java.util.Optional;

public interface CountryRepository extends JpaRepository<Country, Integer> {

    Optional<Country> findByName(String name);
}
