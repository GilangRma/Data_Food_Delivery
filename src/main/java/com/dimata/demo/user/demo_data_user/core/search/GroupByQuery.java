package com.dimata.demo.user.demo_data_user.core.search;

public class GroupByQuery implements GroupBySpec {

    private final StringBuilder builder;

    private GroupByQuery() {
        builder = new StringBuilder();
    }

    public static GroupByQuery builder() {
        return new GroupByQuery();
    } 

    @Override
    public GroupBySpec merge(GroupBySpec groupBy) {
        if(builder.length() <= 0) {
            builder.append(groupBy.getQuery());
        }else {
            builder.append(",")
                .append(groupBy.getQuery());
        }
        return this;
    }

    @Override
    public GroupBySpec append(String group) {
        if(builder.length() <= 0) {
            builder.append(group);
        }else {
            builder.append(",")
                .append(group);
        }
        return this;
        
    }

    @Override
    public String getQuery() {
        return builder.toString();
    }
    
}
