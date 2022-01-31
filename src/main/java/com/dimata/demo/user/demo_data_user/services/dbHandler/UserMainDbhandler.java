package com.dimata.demo.user.demo_data_user.services.dbHandler;

import com.dimata.demo.user.demo_data_user.core.api.DbHandlerBase;
import com.dimata.demo.user.demo_data_user.models.table.UserMain;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.r2dbc.repository.R2dbcRepository;
import org.springframework.stereotype.Component;

import lombok.EqualsAndHashCode;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Component
@EqualsAndHashCode(callSuper = true)
public class UserMainDbhandler extends DbHandlerBase<UserMain, Long> {
    
    @Autowired
    private R2dbcRepository<UserMain, Long> repo;

    @Override
    protected R2dbcRepository<UserMain, Long> getRepository() {
        return repo;
    }

    @Override
    protected Mono<UserMain> setGenerateId(UserMain record) {
        return Mono.just(record)
            .map(z -> {
                long id = getGenerateUtil().generateOID();
                z.setInsertId(id);
                return z;
            });
    }

    @Override
    protected Flux<UserMain> setGenerateIdBatch(Flux<UserMain> records) {
        return records
            .map(rec -> {
                long id = getGenerateUtil().generateOID();
                rec.setInsertId(id);
                return rec;
            });
    }

    
    
}
