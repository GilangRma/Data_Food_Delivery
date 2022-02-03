package com.dimata.demo.user.demo_data_user.services.api;

import com.dimata.demo.user.demo_data_user.core.search.CommonParam;
import com.dimata.demo.user.demo_data_user.core.search.SelectQBuilder;
import com.dimata.demo.user.demo_data_user.core.search.WhereQuery;
import com.dimata.demo.user.demo_data_user.forms.DataUserForm;
import com.dimata.demo.user.demo_data_user.models.table.DataUser;
import com.dimata.demo.user.demo_data_user.services.crude.DataUserCrude;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.r2dbc.core.R2dbcEntityTemplate;
import org.springframework.stereotype.Service;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
public class DataUserApi {
    
    @Autowired
    private DataUserCrude dataUserCrude;
    @Autowired
	private R2dbcEntityTemplate template;

    public Mono<DataUser> createDataUser(DataUserForm form) {
        return Mono.just(form)
        .flatMap(f -> {
            DataUserCrude.Option option = DataUserCrude.initOption(f.convertNewRecord());
            return Mono.just(option);
        })
        .flatMap(dataUserCrude::create);
    }

    public Flux<DataUser> getAllDataUser(CommonParam param) {
        var sql = SelectQBuilder.builderWithCommonParam(DataUser.TABLE_NAME, param)
            .build();
        return template.getDatabaseClient()
            .sql(sql)
            .map(DataUser::fromRow)
            .all();
    }

    public Mono<DataUser> getDataUser(Long id) {
        var sql = SelectQBuilder.emptyBuilder(DataUser.TABLE_NAME)
            .addWhere(WhereQuery.when(DataUser.ID_COL).is(id))
            .build();
        System.out.println(sql);
        return template.getDatabaseClient()
            .sql(sql)
            .map(DataUser::fromRow)
            .one();
    }

    public Mono<DataUser> updateDataUser(Long id, DataUserForm form) {
        return Mono.zip(Mono.just(id), Mono.just(form))
            .map(z -> {
                z.getT2().setId(z.getT1());
                return z.getT2();
            })
            .flatMap(d -> {
                DataUserCrude.Option option = DataUserCrude.initOption(d.convertNewRecord());
                return Mono.just(option);
            })
            .flatMap(dataUserCrude::updateRecord);
    }

    public Mono<DataUser> getDataUserByEmail(String email) {
        var sql = SelectQBuilder.emptyBuilder(DataUser.TABLE_NAME)
            .addWhere(WhereQuery.when(DataUser.EMAIL_COL).is(email))
            .build();
        System.out.println(sql);
        return template.getDatabaseClient()
            .sql(sql)
            .map(DataUser::fromRow)
            .one();
    }

   
}
