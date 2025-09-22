package vn.com.fortis.repository.criteria;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class SearchCriteria {
    private String key;
    private String operation;
    private String value;
}
