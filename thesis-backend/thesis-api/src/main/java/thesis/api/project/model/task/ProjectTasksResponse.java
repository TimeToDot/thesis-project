package thesis.api.project.model.task;

import thesis.domain.paging.Paging;
import thesis.domain.paging.Sorting;

import java.util.List;

public record ProjectTasksResponse (
        List<ProjectTaskResponse> tasks,
        Paging paging,
        Sorting sorting
){
}
