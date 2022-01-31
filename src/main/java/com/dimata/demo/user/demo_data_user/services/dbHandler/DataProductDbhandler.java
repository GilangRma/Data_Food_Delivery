package com.dimata.demo.user.demo_data_user.services.dbHandler;

import com.dimata.demo.user.demo_data_user.core.api.DbHandlerBase;
import com.dimata.demo.user.demo_data_user.models.table.DataProduct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.r2dbc.repository.R2dbcRepository;
import org.springframework.stereotype.Component;

import lombok.EqualsAndHashCode;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Component
@EqualsAndHashCode(callSuper = true)
public class DataProductDbhandler extends DbHandlerBase<DataProduct, Long>{
   
    @Autowired
    private R2dbcRepository<DataProduct, Long> repo;

   

    @Override
    protected R2dbcRepository<DataProduct, Long> getRepository() {
        return repo;
    }

    @Override
    protected Mono<DataProduct> setGenerateId(DataProduct record) {
        return Mono.just(record)
            .map(z -> {
                long id = getGenerateUtil().generateOID();
                z.setInsertId(id);
                return z;
            });
    }

    @Override
    protected Flux<DataProduct> setGenerateIdBatch(Flux<DataProduct> records) {
        return records
            .map(rec ->{
                long id = getGenerateUtil().generateOID();
                rec.setInsertId(id);
                return rec;
            });
    }
    
}
