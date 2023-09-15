package pl.thesis.domain.project;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import pl.thesis.data.project.BillingPeriodRepository;
import pl.thesis.domain.employee.model.BillingPeriodDTO;
import pl.thesis.domain.project.mapper.BillingPeriodDTOMapper;

import java.util.List;

@Slf4j
@AllArgsConstructor
@Service
public class BillingPeriodService {

    private final BillingPeriodRepository billingPeriodRepository;
    private final BillingPeriodDTOMapper billingPeriodDTOMapper;

    public List<BillingPeriodDTO> getBillingPeriods(){
        var billingPeriods = billingPeriodRepository.findAll();

        return billingPeriods.stream()
                .map(billingPeriodDTOMapper::map)
                .toList();
    }

}
