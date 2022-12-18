package thesis.domain.paging;

import lombok.Builder;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

@Slf4j
@Data
@Builder
public class PagingSettings {

    @Valid
    @NotNull
    private Integer page = 0;

    @Valid
    @NotNull
    private Integer size = 2;

    @Valid
    private String direction = "dsc";

    @Valid
    private String key = "";

    public Sort buildSort() {
        switch (direction) {
            case "dsc":
                return Sort.by(key).descending();
            case "asc":
                return Sort.by(key).ascending();
            default:
                log.warn("Invalid direction provided in PageSettings, using descending direction as default value");
                return Sort.by(key).descending();
        }
    }

    public Pageable getPageable(){
        if (key == null || key.isEmpty()){
            return PageRequest.of(page, size);
        }
        return PageRequest.of(page, size, buildSort());
    }
}
