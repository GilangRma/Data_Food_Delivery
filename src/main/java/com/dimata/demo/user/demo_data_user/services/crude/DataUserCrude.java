package com.dimata.demo.user.demo_data_user.services.crude;

import com.dimata.demo.user.demo_data_user.models.table.DataUser;
import com.dimata.demo.user.demo_data_user.services.dbHandler.DataUserDbHandler;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import lombok.AccessLevel;
import lombok.Data;
import lombok.Setter;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Component
public class DataUserCrude {
    
    @Autowired
    private DataUserDbHandler dataUserDbHandler;

    public static Option initOption(DataUser record) {
        return new Option(record);
    }

    public Mono<DataUser> create(Option option) {
        return Mono.just(option)
            .flatMap(this::createRecord)
            .map(o -> o.getRecord());
    }

    public Flux<DataUser> createInBatch(Flux<Option> option) {
		return option
			.flatMap(this::create);
	}

    private Mono<Option> createRecord(Option option) {
		return Mono.just(option)
			.flatMap(o -> {
				Mono<DataUser> savedRecord = dataUserDbHandler.create(o.getRecord());
				
				return Mono.zip(savedRecord, Mono.just(o))
					.map(z -> z.getT2().setIdRecord(z.getT1().getId()));
			});
	}

    public Mono<DataUser> updateRecord(Option option) {
        return dataUserDbHandler.updateOnly(option.getRecord(), option.getRecord().getId());
    }

    @Data
    @Setter(AccessLevel.NONE)
    public static class Option {
        private final DataUser record;
        
        public Option(DataUser record) {
            this.record = record;
        }

        public Option setIdRecord(long id) {
            record.setId(id);
            return this;
        }
    }
}
