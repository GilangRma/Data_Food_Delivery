package com.dimata.demo.user.demo_data_user.core.search;

import javax.validation.constraints.Min;

import lombok.Data;

@Data
public class CommonParam {
    @Min(value = 1, message = "Minimal 1")
    private int page = 1;
    @Min(value = 1, message = "Minimal 1")
    private int limit = 10;
    private boolean nolmt = false;
    private boolean asc = true;
    private String sortBy;
}
