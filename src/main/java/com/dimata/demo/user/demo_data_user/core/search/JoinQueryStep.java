package com.dimata.demo.user.demo_data_user.core.search;

public interface JoinQueryStep {
    String result();
    JoinOperationStep leftJoin(String tableName);
    JoinOperationStep rightJoin(String tableName);
    JoinOperationStep innerJoin(String tableName);
    JoinQueryStep merge(JoinQueryStep join);

}
