package com.dimata.demo.user.demo_data_user.core.search;

import java.util.List;

public interface WhereOperationStep {
    WhereQueryStep is(Object value);
    WhereQueryStep greaterThan(Object value);
    WhereQueryStep lessThan(Object value);
    WhereQueryStep greaterThanEqual(Object value);
    WhereQueryStep lestThanEqual(Object value);
    WhereQueryStep notEqualBracket(Object value);
    WhereQueryStep notEqual(Object value);
    WhereQueryStep like(String value);
    WhereQueryStep in(List<Object> values);
    WhereQueryStep notId(List<Object> values);
    WhereQueryStep between(Object from, Object to);
    WhereQueryStep notBetWeen(Object from, Object to);
}
