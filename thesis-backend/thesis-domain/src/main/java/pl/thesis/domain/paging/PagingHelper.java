package pl.thesis.domain.paging;

import org.springframework.data.domain.Page;

public class PagingHelper {

    public static Sorting getSorting(PagingSettings pagingSettings) {
        if (pagingSettings.getKey() == null || pagingSettings.getKey().isEmpty()){
            return null;
        }

        return Sorting.builder()
                .key(pagingSettings.getKey())
                .direction(pagingSettings.getDirection())
                .build();
    }

    public static Paging getPaging(PagingSettings pagingSettings, Page<?> pagingElements) {
        return Paging.builder()
                .page(pagingSettings.getPage())
                .totalElements(pagingElements.getTotalElements())
                .totalPages(pagingElements.getTotalPages())
                .build();
    }
}
