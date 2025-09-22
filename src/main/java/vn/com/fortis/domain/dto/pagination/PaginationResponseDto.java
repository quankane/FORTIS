package vn.com.fortis.domain.dto.pagination;

import lombok.*;
import lombok.experimental.FieldDefaults;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@NoArgsConstructor
@Setter
@Getter
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class PaginationResponseDto<T> {

    PaginationCustom pageCustom;
    List<T> items;

    public PaginationResponseDto(PaginationCustom pageCustom, List<T> items) {
        this.pageCustom = pageCustom;

        if (items == null) {
            this.items = null;
        } else {
            this.items = Collections.unmodifiableList(items);
        }
    }

    public List<T> getItems() {
        return items == null ? null : new ArrayList<>(items);
    }
}
