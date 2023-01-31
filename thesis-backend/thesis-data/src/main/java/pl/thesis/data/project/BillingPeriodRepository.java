package pl.thesis.data.project;

import org.springframework.data.jpa.repository.JpaRepository;
import pl.thesis.data.project.model.BillingPeriod;

public interface BillingPeriodRepository extends JpaRepository<BillingPeriod, Integer> {
}
