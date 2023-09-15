package pl.thesis.domain.project.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;
import pl.thesis.data.project.model.Project;
import pl.thesis.data.project.model.ProjectType;
import pl.thesis.domain.mapper.MapStructConfig;
import pl.thesis.domain.project.model.ProjectDTO;

@Mapper(
        config = MapStructConfig.class,
        uses = BillingPeriodDTOMapper.class
)
public interface ProjectDTOMapper {

    ProjectDTOMapper INSTANCE = Mappers.getMapper(ProjectDTOMapper.class);

    @Mapping(target = "id", source = "project.id")
    @Mapping(target = "name", source = "project.name")
    @Mapping(target = "description", source = "project.description")
    @Mapping(target = "billingPeriod", source = "project.details.billingPeriod")
    @Mapping(target = "overtimeModifier", source = "project.details.overtimeModifier")
    @Mapping(target = "bonusModifier", source = "project.details.bonusModifier")
    @Mapping(target = "nightModifier", source = "project.details.nightModifier")
    @Mapping(target = "holidayModifier", source = "project.details.holidayModifier")
    @Mapping(target = "image", source = "project.details.imagePath")
    @Mapping(target = "moderatorId", source = "project.owner.id")
    @Mapping(target = "employeesCount", source = "accountsNumber")
    @Mapping(target = "active", expression = "java(isActive(project.getStatus()))")
    ProjectDTO map(Project project, Integer accountsNumber);

    default boolean isActive(ProjectType type){
        return type.compareTo(ProjectType.ACTIVE) == 0;
    }
}
