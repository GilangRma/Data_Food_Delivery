package com.dimata.demo.user.demo_data_user.core.search;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class SortQuery {
    private String sortBy;
    private boolean asc;
}
