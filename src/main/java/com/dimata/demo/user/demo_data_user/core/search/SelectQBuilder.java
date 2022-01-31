package com.dimata.demo.user.demo_data_user.core.search;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

@ToString
@EqualsAndHashCode
public class SelectQBuilder<T> {

    private final StringBuilder query;
    @Getter
    private final T queryRequest;
    @Getter
    private final CommonParam commonParam;

    private final String from;
    private final List<CollumnStep> collumns;
    private final List<WhereQueryStep> whereQuery;
    private final List<JoinQueryStep> joinQuery;
    private final List<GroupBySpec> groupBy;
    private final SortQuery sort;
    private final LimitQueryStep limit;

    private String defaultSort;

    private SelectQBuilder(T queryRequest, String from, CommonParam param) {
        this.queryRequest = queryRequest;
        this.from = from;
        sort = new SortQuery();
        query = new StringBuilder();
        collumns = new ArrayList<>();
        whereQuery = new ArrayList<>();
        joinQuery = new ArrayList<>();
        groupBy = new ArrayList<>();
        commonParam = param;
        if(commonParam != null) {
            sort.setSortBy(param.getSortBy());
            sort.setAsc(param.isAsc());
            limit = LimitQuery.of(param.getPage(), param.getLimit());
        }else {
        	limit = LimitQuery.of(0, 0);
        }
    }
    
    public static SelectQBuilder<Void> emptyBuilder(String from) {
    	return new SelectQBuilder<>(null, from, null);
    }
    
    public static <T extends CommonParam> SelectQBuilder<T> builderWithCommonParam(String from, T queryRequest) {
        return new SelectQBuilder<>(queryRequest, from, queryRequest);
    }

    public static <T> SelectQBuilder<T> builder(String from, T queryRequest, CommonParam commonParam) {
        return new SelectQBuilder<>(queryRequest, from, commonParam);
    }

    public static <T> SelectQBuilder<T> builder(String from, T queryRequest) {
        return new SelectQBuilder<>(queryRequest, from, null);
    }

    public SelectQBuilder<T> addJoin(JoinQueryStep join) {
        joinQuery.add(Objects.requireNonNull(join, "Join can't be null"));
        return this;
    }

    public SelectQBuilder<T> addColumn(CollumnStep collumn) {
        collumns.add(Objects.requireNonNull(collumn, "collumn can't be null"));
        return this;
    }

    public SelectQBuilder<T> addColumn(String collumn) {
        return addColumn(CollumnQuery.add(collumn));
    }

    public SelectQBuilder<T> addColumns(List<CollumnStep> collumns) {
        this.collumns.addAll(Objects.requireNonNull(collumns, "collumns can't be null"));
        return this;
    }

    public SelectQBuilder<T> addWhere(WhereQueryStep where) {
        this.whereQuery.add(Objects.requireNonNull(where, "Where can't be null"));
        return this;
    }

    public SelectQBuilder<T> addGroupBy(GroupBySpec groupBy) {
        this.groupBy.add(Objects.requireNonNull(groupBy, "groupBy can't be null"));
        return this;
    }

    public SelectQBuilder<T> setDefaultSort(String defaultSort) {
        this.defaultSort = defaultSort;
        return this;
    }

    public SelectQBuilder<T> setSort(String sortBy) {
        sort.setSortBy(sortBy);
        return this;
    }

    public SelectQBuilder<T> setSort(String sortBy, boolean ascending) {
        sort.setSortBy(sortBy);
        sort.setAsc(ascending);
        return this;
    }

    public SelectQBuilder<T> setLimit(LimitQueryStep step) {
        limit.setLimit(step.getLimit());
        limit.setPage(step.getPage());
        return this;
    }

    public SelectQBuilder<T> noLimit() {
        limit.setLimit(0);
        limit.setPage(0);
        return this;
    }

    public String build() {
        query.append("SELECT");
        query.append(" ").append(addCollumn());
        query.append(" FROM ").append(from);
        if (!joinQuery.isEmpty()) {
            query.append(" ");
            query.append(joinQuery
                .stream()
                .map(JoinQueryStep::result)
                .collect(Collectors.joining(" "))
            );
        }
        if (!whereQuery.isEmpty()) {
            query.append(" WHERE ");
            query.append(whereQuery
                .stream()
                .map(r -> "(" + r.result() + ")")
                .collect(Collectors.joining(" AND "))
            );
        }

        if(groupBy != null && !groupBy.isEmpty()) {
            query.append(" GROUP BY ")
                .append(groupBy
                    .stream()
                    .map(GroupBySpec::getQuery)
                    .collect(Collectors.joining(","))
                );
        }

        if(sort.getSortBy() != null || defaultSort != null){
            query.append(" ORDER BY ")
            .append(Objects.requireNonNullElse(sort.getSortBy(), defaultSort))
            .append((sort.isAsc() ? " ASC" : " DESC"));
        }
        
        if(!limit.result().isBlank()) {
            query.append(" ").append(limit.result());
        }
        return query.toString();
    }

    // HELPER

    private String addCollumn() {
        if (collumns.isEmpty()) {
            return "*";
        } else {
            return collumns
                .stream()
                .map(CollumnStep::result)
                .collect(Collectors.joining(", "));
        }
    }
}
