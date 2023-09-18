package pl.thesis.domain.project.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;
import pl.thesis.data.account.model.Account;
import pl.thesis.data.account.model.AccountDetails;
import pl.thesis.data.account.model.StatusType;
import pl.thesis.data.position.model.Position;
import pl.thesis.domain.employee.model.ContractDTO;
import pl.thesis.domain.employee.model.SimplePositionDTO;
import pl.thesis.domain.mapper.MapStructConfig;
import pl.thesis.domain.project.model.employee.EmployeeDTO;

@Mapper(config = MapStructConfig.class)
public interface PEmployeeDTOMapper {

    PEmployeeDTOMapper INSTANCE = Mappers.getMapper(PEmployeeDTOMapper.class);

    @Mapping(target = "employeeId", source = "account.id")
    @Mapping(target = "firstName", source = "name")
    @Mapping(target = "lastName", source = "surname")
    @Mapping(target = "email", source = "account.email")
    @Mapping(target = "imagePath", source = "imagePath")
    @Mapping(target = "position", expression = "java(getPosition(details.getAccount().getPosition()))")
    @Mapping(target = "employmentDate", source = "employmentDate")
    @Mapping(target = "contractType", expression = "java(getContractType(details.getAccount()))")
    @Mapping(target = "workingTime", source = "workingTime")
    @Mapping(target = "wage", source = "wage")
    @Mapping(target = "active", expression = "java(isActive(details.getAccount().getStatus()))")
    EmployeeDTO map(AccountDetails details);

    default boolean isActive(StatusType type){
        return type.compareTo(StatusType.ENABLE) == 0;
    }

    default SimplePositionDTO getPosition(Position position){
        if (position == null){
            return null;
        }
        return SimplePositionDTO.builder()
                .id(position.getId())
                .name(position.getName())
                .build();
    }

    default ContractDTO getContractType(Account account) {
        return ContractDTO.builder()
                .id(account.getDetails().getContractType().getId())
                .name(account.getDetails().getContractType().getName())
                .build();
    }
}
