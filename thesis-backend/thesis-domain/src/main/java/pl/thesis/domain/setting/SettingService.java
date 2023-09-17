package pl.thesis.domain.setting;

import pl.thesis.domain.employee.model.ContractDTO;

import java.util.List;

public interface SettingService {
    List<ContractDTO> getContractTypes();
}
