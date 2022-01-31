package com.dimata.demo.user.demo_data_user.services.repo;

import com.dimata.demo.user.demo_data_user.models.table.UserMain;

import org.springframework.data.r2dbc.repository.R2dbcRepository;

import reactor.core.publisher.Mono;

public interface UserMainRepo extends R2dbcRepository<UserMain, Long> {
    Mono<UserMain> findById(long id);
    
}
