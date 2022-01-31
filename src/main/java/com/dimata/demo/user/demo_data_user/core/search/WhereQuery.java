package com.dimata.demo.user.demo_data_user.core.search;

import java.util.List;
import java.util.stream.Collectors;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

// ToDo : Lanjutkan Lagi 
@Getter
@ToString
@EqualsAndHashCode
public class WhereQuery implements WhereQueryStep, WhereOperationStep {

    private String leftOperand;
    private StringBuilder queryBuilder;
    private boolean needBracket;

    private WhereQuery(String leftOperand) {
        this.leftOperand = leftOperand;
        queryBuilder = new StringBuilder();
    }

    public static WhereOperationStep when(String operand) {
        WhereQuery result = new WhereQuery(operand);
        result.getQueryBuilder().append(operand);
        result.needBracket = false;
        return result;
    }

    public static WhereOperationStep whenBracket(String operand) {
        WhereQuery result = new WhereQuery(operand);
        result.getQueryBuilder().append("(").append(operand);
        result.needBracket = true;
        return result;
    }

    private String handleQuote(Object value) {
        if(value instanceof Long ||
             value instanceof Double ||
             value instanceof Integer || 
             value instanceof Float || 
             value instanceof FunctionOpt) {
            return String.valueOf(value);
        }else {
            return "'" + String.valueOf(value) + "'";
        }
    }

    //****************** INTERFACE IMPLEMENT *****************/

    @Override
    public String result() {
        if(needBracket){
            queryBuilder.append(")");
        }
       return queryBuilder.toString();
    }

    @Override
    public WhereQueryStep and(WhereQueryStep operand) {
        queryBuilder.append(" AND ").append(operand.result());
        return this;
    }

    @Override
    public WhereQueryStep or(WhereQueryStep operand) {
        queryBuilder.append(" OR ").append(operand.result());
        return this;
    }

    
    @Override
    @SuppressWarnings("all")
    public WhereQueryStep not(WhereQueryStep step) {
        StringBuilder temp = new StringBuilder().append(" NOT ").append(step.result());
        queryBuilder.append(temp);
        return this;
    }

    @Override
    public WhereQueryStep is(Object value) {
        queryBuilder.append(" = ").append(handleQuote(value));
        return this;
    }

    @Override
    public WhereQueryStep greaterThan(Object value) {
        queryBuilder.append(" > ").append(handleQuote(value));
        return this;
    }

    @Override
    public WhereQueryStep lessThan(Object value) {
        queryBuilder.append(" < ").append(handleQuote(value));
        return this;
    }

    @Override
    public WhereQueryStep greaterThanEqual(Object value) {
        queryBuilder.append(" >= ").append(handleQuote(value));
        return this;
    }

    @Override
    public WhereQueryStep lestThanEqual(Object value) {
        queryBuilder.append(" <= ").append(handleQuote(value));
        return this;
    }

    @Override
    public WhereQueryStep notEqualBracket(Object value) {
        queryBuilder.append(" <> ").append(handleQuote(value));
        return this;
    }

    @Override
    public WhereQueryStep notEqual(Object value) {
        queryBuilder.append(" != ").append(handleQuote(value));
        return this;
    }

    @Override
    public WhereQueryStep like(String value) {
        queryBuilder.append(" LIKE ").append(handleQuote(value));
        return this;
    }

    @Override
    public WhereQueryStep notId(List<Object> values) {
        queryBuilder.append(" NOT ");
        return in(values);
    }

    @Override
    public WhereQueryStep in(List<Object> values) {
        queryBuilder.append(" IN ")
            .append("(")
            .append(handleQuote(
                values
                    .stream()
                    .map(this::handleQuote)
                    .collect(Collectors.joining(", "))
            ))
            .append(")");
        return this;
    }

    @Override
    public WhereQueryStep between(Object from, Object to) {
        queryBuilder.append(" BETWEEN ")
            .append(handleQuote(from)).append(" AND ").append(handleQuote(to));
        return this;
    }

    @Override
    public WhereQueryStep notBetWeen(Object from, Object to) {
        queryBuilder.append(" NOT ");
        return between(from, to);
    }
}
