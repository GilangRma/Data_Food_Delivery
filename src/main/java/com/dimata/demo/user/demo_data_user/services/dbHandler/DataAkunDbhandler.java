package com.dimata.demo.user.demo_data_user.services.dbHandler;

import com.dimata.demo.user.demo_data_user.core.api.DbHandlerBase;
import com.dimata.demo.user.demo_data_user.models.table.DataAkun;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.r2dbc.repository.R2dbcRepository;
import org.springframework.stereotype.Component;

import lombok.EqualsAndHashCode;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Component
@EqualsAndHashCode(callSuper = true)
public class DataAkunDbhandler extends DbHandlerBase<DataAkun, Long>{
    
    @Autowired
    private R2dbcRepository<DataAkun, Long> repo;

    @Override
    protected R2dbcRepository<DataAkun, Long> getRepository() {
        return repo;
    }

    @Override
    protected Mono<DataAkun> setGenerateId(DataAkun record) {
        return Mono.just(record)
            .map(z -> {
                long id = getGenerateUtil().generateOID();
                z.setInsertId(id);
                return z;
            });
    }

    @Override
    protected Flux<DataAkun> setGenerateIdBatch(Flux<DataAkun> records) {
        return records
            .map(rec -> {
                long id = getGenerateUtil().generateOID();
                rec.setInsertId(id);
                return rec;
            });
    }
}
