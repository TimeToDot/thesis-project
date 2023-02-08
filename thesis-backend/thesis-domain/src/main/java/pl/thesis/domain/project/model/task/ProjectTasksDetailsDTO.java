package pl.thesis.domain.project.model.task;

import lombok.Builder;
import pl.thesis.domain.paging.Paging;
import pl.thesis.domain.paging.Sorting;

import java.util.List;

@Builder
public record ProjectTasksDetailsDTO(
        List<ProjectTaskDetailsDTO> tasks,
        Paging paging,
        Sorting sorting
){
}
