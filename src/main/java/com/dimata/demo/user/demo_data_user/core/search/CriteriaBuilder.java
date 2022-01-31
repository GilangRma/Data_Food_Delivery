package com.dimata.demo.user.demo_data_user.core.search;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import com.dimata.demo.user.demo_data_user.core.util.CheckUtil;
import com.dimata.demo.user.demo_data_user.core.util.GenerateUtil;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.r2dbc.core.R2dbcEntityTemplate;
import org.springframework.data.relational.core.query.Criteria;
import org.springframework.data.relational.core.query.Query;

import lombok.Getter;

/**
 * Criteria Builder merupakan kelas wrap untuk {@link Criteria}. Dengan builder
 * ini mempermudah dalam menyusun criteria dan bisa langsung membuat
 * {@link Query} yang digunakan untuk eksekusi pada {@link R2dbcEntityTemplate}.
 * <p>
 * Kelas ini disusun untuk digabungkan bersama {@link QueryFactory} dan
 * {@link SearchExecutor}.
 * 
 * @param T Sebuah informasi yang menyimpan request filter untuk query. 
 *          Contoh : Filter SKU sesuai dengan request.
 * 
 * @author Hariyogi
 * @since 1.0
 */
@Getter
public class CriteriaBuilder<T extends CommonParam> {
    /**
     * Default sort akan disisipkan kedalam query SORT BY ?.
     */
    private String defaultSort;
    private List<Criteria> criterias;
    private final T queryRequest;
    private final GenerateUtil generateUtil;

    public CriteriaBuilder(T queryCommand, String defaultSort) {
        this.defaultSort = defaultSort;
        this.queryRequest = queryCommand;
        this.generateUtil = new GenerateUtil();
        criterias = new ArrayList<>();
    }

    /**
     * Tambah criteria baru.
     * 
     * @param criteria Criteria yang ditambahkan tidak boleh null.
     * @return Diri sendiri.
     */
    public CriteriaBuilder<T> appendCriteria(Criteria criteria) {
        this.criterias.add(Objects.requireNonNull(criteria));
        return this;
    }

    /**
     * Tambah kumpulan criteria kedalam criteria ini.
     * 
     * @param criterias List criteria yang dimana tidak boleh null.
     * @return
     */
    public CriteriaBuilder<T> appendCriterias(List<Criteria> criterias) {
        this.criterias.addAll(criterias);
        return this;
    }

    /**
     * Gabungkan semua criteria yang disimpan menjadi satu criteria.
     * 
     * @return Criteria
     */
    public Criteria getMergeCriteria() {
        return Criteria.from(criterias);
    }

    /**
     * Jika {@link CommonParam#getSortBy() == null} maka defaultSort akan digunakan.
     */
    public Sort getSort() {
        if(CheckUtil.isStringBlank(queryRequest.getSortBy())){
            return generateUtil.createSort(defaultSort, queryRequest.isAsc());
        }else{
            return generateUtil.createSort(queryRequest.getSortBy(), queryRequest.isAsc());
        }
        
    }

    public Pageable getPage() {
        return PageRequest.of(queryRequest.getPage() - 1, queryRequest.getLimit());
    }

    public Query createQuery() {
        return Query.query(getMergeCriteria()).sort(getSort()).with(getPage());
    }
}
