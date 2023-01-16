package thesis.domain.project.model.task;

import lombok.Builder;
import thesis.domain.paging.Paging;
import thesis.domain.paging.Sorting;

import java.util.List;

@Builder
public record ProjectTasksDetailsDTO(
        List<ProjectTaskDetailsDTO> tasks,
        Paging paging,
        Sorting sorting
){
}
