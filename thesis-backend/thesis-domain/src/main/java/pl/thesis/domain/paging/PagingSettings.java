package pl.thesis.domain.paging;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

@Slf4j
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PagingSettings {

    @Valid
    @NotNull
    private Integer page = 1;

    @Valid
    @NotNull
    private Integer size = Integer.MAX_VALUE;

    @Valid
    private String direction = "dsc";

    @Valid
    private String key = "";

    @JsonIgnore
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

    @JsonIgnore
    public Pageable getPageable(){
        if (key == null || key.isEmpty()){
            return PageRequest.of(page - 1, size);
        }
        return PageRequest.of(page, size, buildSort());
    }
}
