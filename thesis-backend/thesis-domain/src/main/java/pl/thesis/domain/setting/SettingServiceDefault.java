package pl.thesis.domain.setting;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import pl.thesis.data.account.ContractTypeRepository;
import pl.thesis.data.account.model.ContractType;
import pl.thesis.domain.employee.model.ContractDTO;

import java.util.List;

@AllArgsConstructor
@Service
public class SettingServiceDefault implements SettingService {

    private final ContractTypeRepository contractTypeRepository;

    @Override
    public List<ContractDTO> getContractTypes(){
        var contractTypes = contractTypeRepository.findAll();

        return contractTypes.stream()
                .map(this::getContractDTO)
                .toList();
    }

    private ContractDTO getContractDTO(ContractType contractType) {
        return ContractDTO.builder()
                .id(contractType.getId())
                .name(contractType.getName())
                .build();
    }
}
