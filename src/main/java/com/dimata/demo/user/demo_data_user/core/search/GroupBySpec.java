package com.dimata.demo.user.demo_data_user.core.search;

public interface GroupBySpec {
    GroupBySpec merge(GroupBySpec groupBy);
    GroupBySpec append(String group);
    String getQuery();
}
