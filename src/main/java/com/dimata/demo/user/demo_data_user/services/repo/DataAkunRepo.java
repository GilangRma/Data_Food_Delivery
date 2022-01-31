package com.dimata.demo.user.demo_data_user.services.repo;

import com.dimata.demo.user.demo_data_user.models.table.DataAkun;

import org.springframework.data.r2dbc.repository.R2dbcRepository;

import reactor.core.publisher.Mono;

public interface DataAkunRepo extends R2dbcRepository<DataAkun, Long> {
    Mono<DataAkun> findById(long id);
    
}
