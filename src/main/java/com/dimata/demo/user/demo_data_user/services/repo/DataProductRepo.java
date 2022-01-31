package com.dimata.demo.user.demo_data_user.services.repo;

import com.dimata.demo.user.demo_data_user.models.table.DataProduct;

import org.springframework.data.r2dbc.repository.R2dbcRepository;

import reactor.core.publisher.Mono;

public interface DataProductRepo extends R2dbcRepository<DataProduct, Long> {
    Mono<DataProduct> findById(long id);
    
}
