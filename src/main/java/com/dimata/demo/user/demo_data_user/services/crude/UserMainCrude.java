package com.dimata.demo.user.demo_data_user.services.crude;

import com.dimata.demo.user.demo_data_user.models.table.UserMain;
import com.dimata.demo.user.demo_data_user.services.dbHandler.UserMainDbhandler;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import lombok.AccessLevel;
import lombok.Data;
import lombok.Setter;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Component
public class UserMainCrude {
    
    @Autowired
    private UserMainDbhandler UserMainDbHandler;

    public Option initOption(UserMain record) {
        return new Option(record);
    }

    public Mono<UserMain> create(Option option) {
        return Mono.just(option)
            .flatMap(this::createRecord)
            .map(o -> o.getRecord());
    }

    public Flux<UserMain> createInBatch(Flux<Option> option) {
		return option
			.flatMap(this::create);
	}

    private Mono<Option> createRecord(Option option) {
		return Mono.just(option)
			.flatMap(o -> {
				Mono<UserMain> savedRecord = UserMainDbHandler.create(o.getRecord());
				
				return Mono.zip(savedRecord, Mono.just(o))
					.map(z -> z.getT2().setIdRecord(z.getT1().getId()));
			});
	}

    public Mono<UserMain> updateRecord(Option option) {
        return UserMainDbHandler.updateOnly(option.getRecord(), option.getRecord().getId());
    }

    @Data
    @Setter(AccessLevel.NONE)
    public static class Option {
        private final UserMain record;
        
        public Option(UserMain record) {
            this.record = record;
        }

        public Option setIdRecord(long id) {
            record.setId(id);
            return this;
        }
    }
}
