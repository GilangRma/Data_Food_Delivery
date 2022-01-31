package com.dimata.demo.user.demo_data_user.services.crude;

import com.dimata.demo.user.demo_data_user.models.table.DataAkun;
import com.dimata.demo.user.demo_data_user.services.dbHandler.DataAkunDbhandler;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import lombok.AccessLevel;
import lombok.Data;
import lombok.Setter;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Component
public class DataAkunCrude {
    @Autowired
    private DataAkunDbhandler dataAkunDbHandler;

    public static Option initOption(DataAkun record) {
        return new Option(record);
    }

    public Mono<DataAkun> create(Option option) {
        return Mono.just(option)
            .flatMap(this::createRecord)
            .map(o -> o.getRecord());
    }

    public Flux<DataAkun> createInBatch(Flux<Option> option) {
		return option
			.flatMap(this::create);
	}

    private Mono<Option> createRecord(Option option) {
		return Mono.just(option)
			.flatMap(o -> {
				Mono<DataAkun> savedRecord = dataAkunDbHandler.create(o.getRecord());
				
				return Mono.zip(savedRecord, Mono.just(o))
					.map(z -> z.getT2().setIdRecord(z.getT1().getId()));
			});
	}

    public Mono<DataAkun> updateRecord(Option option) {
        return dataAkunDbHandler.update(option.getRecord(), option.getRecord().getId());
    }

    @Data
    @Setter(AccessLevel.NONE)
    public static class Option {
        private final DataAkun record;
        
        public Option(DataAkun record) {
            this.record = record;
        }

        public Option setIdRecord(long id) {
            record.setId(id);
            return this;
        }
    }
}
