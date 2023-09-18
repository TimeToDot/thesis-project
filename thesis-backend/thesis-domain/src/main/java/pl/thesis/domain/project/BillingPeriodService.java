package pl.thesis.domain.project;

import pl.thesis.domain.employee.model.BillingPeriodDTO;

import java.util.List;

public interface BillingPeriodService {
    List<BillingPeriodDTO> getBillingPeriods();
}
