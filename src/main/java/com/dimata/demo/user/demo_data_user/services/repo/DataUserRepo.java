package com.dimata.demo.user.demo_data_user.services.repo;

import com.dimata.demo.user.demo_data_user.models.table.DataUser;

import org.springframework.data.r2dbc.repository.R2dbcRepository;

import reactor.core.publisher.Mono;

public interface DataUserRepo extends R2dbcRepository<DataUser, Long> {
    Mono<DataUser> findById(long id);
    
}
