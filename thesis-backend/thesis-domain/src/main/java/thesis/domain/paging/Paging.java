package thesis.domain.paging;

import lombok.Builder;

@Builder
public record Paging(
        Integer page,
        Long totalElements,
        Integer totalPages
) {
}
