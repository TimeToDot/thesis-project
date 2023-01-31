package pl.thesis.api.project.model.task;

import lombok.Builder;
import pl.thesis.domain.paging.Paging;
import pl.thesis.domain.paging.Sorting;

import java.util.List;

@Builder
public record ProjectTasksDetails(
        List<ProjectTaskDetails> tasks,
        Paging paging,
        Sorting sorting
){
}
