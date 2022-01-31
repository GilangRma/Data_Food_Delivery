package com.dimata.demo.user.demo_data_user.services.crude;

import com.dimata.demo.user.demo_data_user.models.table.DataProduct;
import com.dimata.demo.user.demo_data_user.services.dbHandler.DataProductDbhandler;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import lombok.AccessLevel;
import lombok.Data;
import lombok.Setter;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Component
public class DataProductCrude {
    
    @Autowired
    private DataProductDbhandler dataProductDbHandler;

    public static Option initOption(DataProduct record) {
        return new Option(record);
    }

    public Mono<DataProduct> create(Option option) {
        return Mono.just(option)
            .flatMap(this::createRecord)
            .map(o -> o.getRecord());
    }

    public Flux<DataProduct> createInBatch(Flux<Option> option) {
		return option
			.flatMap(this::create);
	}

    private Mono<Option> createRecord(Option option) {
		return Mono.just(option)
			.flatMap(o -> {
				Mono<DataProduct> savedRecord = dataProductDbHandler.create(o.getRecord());
				
				return Mono.zip(savedRecord, Mono.just(o))
					.map(z -> z.getT2().setIdRecord(z.getT1().getId()));
			});
	}

    public Mono<DataProduct> updateRecord(Option option) {
        return dataProductDbHandler.updateOnly(option.getRecord(), option.getRecord().getId());
    }

    @Data
    @Setter(AccessLevel.NONE)
    public static class Option {
        private final DataProduct record;
        
        public Option(DataProduct record) {
            this.record = record;
        }

        public Option setIdRecord(long id) {
            record.setId(id);
            return this;
        }
    }
}
