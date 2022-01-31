package com.dimata.demo.user.demo_data_user.core.util;

import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.Duration;
import java.util.Date;

@Component
public class GenerateUtil {

    public static final int APP_IDX = 3;

    public long generateOID(int appId) {
        var now = new Date();
        return now.getTime() + (0x01000000000000L * appId);
    }
    
    public long generateOID() {
        var now = new Date();
        return now.getTime() + (0x01000000000000L * APP_IDX);
    }
    
    public long generatePaymentReq() {
    	var now = new Date();
    	return now.getTime() + (0x010000000L * APP_IDX);
    }

    public Mono<Long> generateOidPipe() {
        return generateOidPipe(APP_IDX);
    }

    public Mono<Long> generateOidPipe(int appId) {
        return Mono.just(appId)
                .delayElement(Duration.ofMillis(1))
                .map(this::generateOID);
    }

    public Flux<Long> generatedOidPipe(Flux<?> dataFlow) {
        return dataFlow.count()
                .flux()
                .flatMap(c -> generateOidPipe().repeat(c));
    }

    public Sort createSort(String sortBy, boolean isDescending) {
        Sort sort = Sort.by(sortBy);
        if (isDescending) {
            sort.descending();
        } else {
            sort.ascending();
        }
        return sort;
    }

    public String genSecretApp(int length) {
        return RandomStringUtils.randomAlphanumeric(length);
    }

    public class generateOID {
    }
}
