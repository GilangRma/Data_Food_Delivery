package com.dimata.demo.user.demo_data_user.core.search;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
@EqualsAndHashCode
public class JoinQuery implements JoinQueryStep, JoinOperationStep{

    private WhereQueryStep operationQuery;
    private StringBuilder scriptBuilder;

    private JoinQuery() {
        scriptBuilder = new StringBuilder();
    }

    public static JoinOperationStep doLeftJoin(String tableName) {
        JoinQuery result = new JoinQuery();
        return result.leftJoin(tableName);
    }

    public static JoinOperationStep doRightJoin(String tableName) {
        JoinQuery result = new JoinQuery();
        return result.rightJoin(tableName);
    }

    public static JoinOperationStep doInnerJoin(String tableName) {
        JoinQuery result = new JoinQuery();
        return result.innerJoin(tableName);
    }

    
    //***************** INTERFACE IMPLEMENT ********************************/

    @Override
    public JoinQueryStep on(WhereQueryStep operationStep) {
        scriptBuilder.append(" ON ").append(operationStep.result().replace("'", ""));
        return this;
    }

    @Override
    public String result() {
        return scriptBuilder.toString();
    }

    @Override
    public JoinOperationStep leftJoin(String tableName) {
        scriptBuilder.append("LEFT JOIN ").append(tableName);
        return this;
    }

    @Override
    public JoinOperationStep rightJoin(String tableName) {
        scriptBuilder.append("RIGHT JOIN ").append(tableName);
        return this;
    }

    @Override
    public JoinOperationStep innerJoin(String tableName) {
        scriptBuilder.append("INNER JOIN ").append(tableName);
        return this;
    }

    @Override
    public JoinQueryStep merge(JoinQueryStep join) {
        scriptBuilder.append(" ").append(join.result());
        return this;
    }
    
}
