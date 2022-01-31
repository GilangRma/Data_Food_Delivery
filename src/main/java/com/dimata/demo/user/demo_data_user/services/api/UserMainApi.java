package com.dimata.demo.user.demo_data_user.services.api;

import com.dimata.demo.user.demo_data_user.core.search.CommonParam;
import com.dimata.demo.user.demo_data_user.core.search.SelectQBuilder;
import com.dimata.demo.user.demo_data_user.core.search.WhereQuery;
import com.dimata.demo.user.demo_data_user.forms.UserMainForm;
import com.dimata.demo.user.demo_data_user.models.table.UserMain;
import com.dimata.demo.user.demo_data_user.services.crude.UserMainCrude;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.r2dbc.core.R2dbcEntityTemplate;
import org.springframework.stereotype.Service;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
public class UserMainApi {
    
    @Autowired
    private UserMainCrude UserMainCrude;
    @Autowired
	private R2dbcEntityTemplate template;

    public Mono<UserMain> createUserMain(UserMainForm form) {
        return Mono.just(form)
        .flatMap(f -> {
            UserMainCrude.Option option = UserMainCrude.initOption(f.convertNewRecord());
            return Mono.just(option);
        })
        .flatMap(UserMainCrude::create);
    }

    public Flux<UserMain> getAllUserMain(CommonParam param) {
        var sql = SelectQBuilder.builderWithCommonParam(UserMain.TABLE_NAMA, param)
            .build();
        return template.getDatabaseClient()
            .sql(sql)
            .map(UserMain::fromRow)
            .all();
    }

    public Mono<UserMain> getUserMain(Long id) {
        var sql = SelectQBuilder.emptyBuilder(UserMain.TABLE_NAMA)
            .addWhere(WhereQuery.when(UserMain.ID_COL).is(id))
            .build();
        System.out.println(sql);
        return template.getDatabaseClient()
            .sql(sql)
            .map(UserMain::fromRow)
            .one();
    }

    public Mono<UserMain> updateUserMain(Long id, UserMainForm form) {
        return Mono.zip(Mono.just(id), Mono.just(form))
            .map(z -> {
                z.getT2().setId(z.getT1());
                return z.getT2();
            })
            .flatMap(d -> {
                UserMainCrude.Option option = UserMainCrude.initOption(d.convertNewRecord());
                return Mono.just(option);
            })
            .flatMap(UserMainCrude::updateRecord);
    }

   
}
