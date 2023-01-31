package pl.thesis.data.account;

import org.springframework.data.jpa.repository.JpaRepository;
import pl.thesis.data.account.model.Sex;

import java.util.Optional;

public interface SexRepository extends JpaRepository<Sex, Integer> {

    Optional<Sex> findByName(String name);
}
