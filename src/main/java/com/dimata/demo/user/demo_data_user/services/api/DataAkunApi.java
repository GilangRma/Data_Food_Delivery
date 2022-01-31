package com.dimata.demo.user.demo_data_user.services.api;


import com.dimata.demo.user.demo_data_user.core.search.CommonParam;
import com.dimata.demo.user.demo_data_user.core.search.SelectQBuilder;
import com.dimata.demo.user.demo_data_user.core.search.WhereQuery;
import com.dimata.demo.user.demo_data_user.forms.DataAkunForm;
import com.dimata.demo.user.demo_data_user.models.table.DataAkun;
import com.dimata.demo.user.demo_data_user.services.crude.DataAkunCrude;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.r2dbc.core.R2dbcEntityTemplate;
import org.springframework.stereotype.Service;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service

public class DataAkunApi {
    
    @Autowired
    private DataAkunCrude dataAkunCrude;
    @Autowired
	private R2dbcEntityTemplate template;

    public Mono<DataAkun> createDataAkun(DataAkunForm form) {
        return Mono.just(form)
        .flatMap(f -> {
            DataAkunCrude.Option option = DataAkunCrude.initOption(f.convertNewRecord());
            return Mono.just(option);
        })
        .flatMap(dataAkunCrude::create);
    }

    public  Flux<DataAkun> getAllDataAkun(CommonParam param) {
        var sql = SelectQBuilder.builderWithCommonParam(DataAkun.TABLE_NAME, param)
            .build();
        return template.getDatabaseClient()
            .sql(sql)
            .map(DataAkun::fromRow)
            .all();
    }

    public Mono<DataAkun> getDataAkun(Long id) {
        var sql = SelectQBuilder.emptyBuilder(DataAkun.TABLE_NAME)
            .addWhere(WhereQuery.when(DataAkun.ID_COL).is(id))
            .build();
        System.out.println(sql);
        return template.getDatabaseClient()
            .sql(sql)
            .map(DataAkun::fromRow)
            .one();
    }

    public Mono<DataAkun> updateDataAkun(Long id, DataAkunForm form) {
        return Mono.zip(Mono.just(id), Mono.just(form))
            .map(z -> {
                z.getT2().setId(z.getT1());
                return z.getT2();
            })
            .flatMap(d -> {
                DataAkunCrude.Option option = DataAkunCrude.initOption(d.convertNewRecord());
                return Mono.just(option);
            })
            .flatMap(dataAkunCrude::updateRecord);
    }

      
}
