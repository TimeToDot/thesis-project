package thesis.domain.project.model.task;

import thesis.domain.paging.Paging;
import thesis.domain.paging.Sorting;

import java.util.List;

public record ProjectTasksDTO(
        List<ProjectTaskDTO> tasks,
        Paging paging,
        Sorting sorting
){
}
