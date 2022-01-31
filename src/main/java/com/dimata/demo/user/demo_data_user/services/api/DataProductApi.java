package com.dimata.demo.user.demo_data_user.services.api;

import com.dimata.demo.user.demo_data_user.core.search.CommonParam;
import com.dimata.demo.user.demo_data_user.core.search.SelectQBuilder;
import com.dimata.demo.user.demo_data_user.core.search.WhereQuery;
import com.dimata.demo.user.demo_data_user.forms.DataProductForm;
import com.dimata.demo.user.demo_data_user.models.table.DataProduct;
import com.dimata.demo.user.demo_data_user.services.crude.DataProductCrude;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.r2dbc.core.R2dbcEntityTemplate;
import org.springframework.stereotype.Service;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
public class DataProductApi {
    
    @Autowired
    private DataProductCrude dataProductCrude;
    @Autowired
	private R2dbcEntityTemplate template;

    public Mono<DataProduct> createDataProduct(DataProductForm form) {
        return Mono.just(form)
        .flatMap(f -> {
            DataProductCrude.Option option = DataProductCrude.initOption(f.convertNewRecord());
            return Mono.just(option);
        })
        .flatMap(f -> {
            return dataProductCrude.create(f);
        });
    }

    public Flux<DataProduct> getAllDataProduct(CommonParam param) {
        var sql = SelectQBuilder.builderWithCommonParam(DataProduct.TABLE_NAME, param)
            .build();
        return template.getDatabaseClient()
            .sql(sql)
            .map(DataProduct::fromRow)
            .all();
    }

    public Mono<DataProduct> getDataProduct(Long id) {
        var sql = SelectQBuilder.emptyBuilder(DataProduct.TABLE_NAME)
            .addWhere(WhereQuery.when(DataProduct.ID_COL).is(id))
            .build();
        System.out.println(sql);
        return template.getDatabaseClient()
            .sql(sql)
            .map(DataProduct::fromRow)
            .one();
    }

    public Mono<DataProduct> updateDataProduct(Long id, DataProductForm form) {
        return Mono.zip(Mono.just(id), Mono.just(form))
            .map(z -> {
                z.getT2().setId(z.getT1());
                return z.getT2();
            })
            .flatMap(d -> {
                DataProductCrude.Option option = DataProductCrude.initOption(d.convertNewRecord());
                return Mono.just(option);
            })
            .flatMap(dataProductCrude::updateRecord);
    }

   
}
