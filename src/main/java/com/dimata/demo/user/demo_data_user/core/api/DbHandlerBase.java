package com.dimata.demo.user.demo_data_user.core.api;

import java.time.Duration;

import com.dimata.demo.user.demo_data_user.core.exception.DataNotFoundException;
import com.dimata.demo.user.demo_data_user.core.search.CommonParam;
import com.dimata.demo.user.demo_data_user.core.util.CheckUtil;
import com.dimata.demo.user.demo_data_user.core.util.GenerateUtil;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Sort;
import org.springframework.data.r2dbc.repository.R2dbcRepository;
import org.springframework.stereotype.Component;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;
import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.util.retry.Retry;

/**
 * Db Handler merupakan sebuah component yang berfungsi sebagai jembatan antara
 * java dengan database.
 * <p>
 * Db Handler Base merupakan template yang memiliki fungsi - fungsi basic CRUDE
 * database. Db Base Handler dibuat khusus untuk di gunakan sebagai super class.
 *
 * @param <T> Object yang menyimpan informasi tabel pada database.
 * @param <I> Tipe Id pada database tersebut.
 */
@Component
@ToString
@EqualsAndHashCode
@Slf4j
public abstract class DbHandlerBase<T, I> {
    @Autowired
    @Getter
    private CheckUtil checkUtil;
    @Autowired
    @Getter
    GenerateUtil generateUtil;
    
    /**
     * Setting a repository.
     * 
     * @return repository;
     */
    protected abstract R2dbcRepository<T, I> getRepository();
    
    /**
     * Lakukan sesuatu sebelum di {@link #save(Object) save}.
     * <p>
     * Fungsi ini akan digunakan pada {@link #save(Object) save}.
     */
    protected Mono<T> doBeforeSave(T record) {
        return Mono.just(record);
    }

    /**
     * Lakukan sesuatu sebelum di {@link #saveOnly(Object) saveOnly}.
     *
     * @param record Record yang akan disimpan.
     * @return Record yang tersimpan.
     */
    protected Mono<T> doBeforeSaveOnly(T record) {
        return Mono.just(record);
    }

    /**
     * Membuat dan menyisipkan id yang digenerate kedalam record.
     *
     * @param record Record yang ingin disisipkan id generate.
     * @return Record yang sudah disisipkan id.
     */
    protected abstract Mono<T> setGenerateId(T record);

    /**
     * Sama dengan ini {@link #setGenerateId(Object) setGenerateId}, hanya saja ini Batch.
     *
     * @param records List record yang ingin disisipkan id.
     * @return List records yang sudah disisipkan id.
     */
    protected abstract Flux<T> setGenerateIdBatch(Flux<T> records);

    /**
     * Melakukan pengecekan yang dibutuhkan pada record baru sebelum ditimpa ke
     * record lama.
     *
     * @param newRecord record baru.
     * @return record yang sudah dicheck.
     */
    protected Mono<T> doDeepCheckNewRecordBeforeUpdate(T newRecord) {
        return Mono.just(newRecord);
    }

    // ***************************** PUBLIC METHOD *******************************

    /**
     * Sama dengan {@link #create(Object) create}, hanya saja ini dalam bentuk batch. <br>
     * Override {@link #setGenerateIdBatch(Flux) setGenerateIdBatch} untuk implementasi id.
     *
     * @param records List records yang ingin disimpan.
     * @return Hasil records yang sudah disimpan.
     * @see #createBatchOnly(Flux)
     */
    public Flux<T> createBatch(Flux<T> records) {
        return setGenerateIdBatch(records)
            .flatMap(this::save)
            .retryWhen(Retry.backoff(2, Duration.ofMillis(10))
    			.filter(this::isErrorCauseDuplicatePK)
			);
    }

    /**
     * Sama dengan {@link #createOnly(Object) crateOnlt}, hanya saja ini dalam bentuk batch. <br>
     * Overide {@link #setGenerateIdBatch(Flux) setGenerateIdBatch} untuk implementasi id.
     *
     * @param records List records yang ingin disimpan.
     * @return Hasil records yang sudah disimpan.
     * @see #createBatch(Flux)
     */
    public Flux<T> createBatchOnly(Flux<T> records) {
        return setGenerateIdBatch(records)
            .flatMap(this::saveOnly)
            .retryWhen(Retry.backoff(2, Duration.ofMillis(10))
    			.filter(this::isErrorCauseDuplicatePK)
			);
    }

    /**
     * Membuat data dengan menggunakan generate id. <br>
     * Override {@link #setGenerateId(Object) setGenerateId} untuk implementasi
     * generate id. <br>
     * Setelah record dibuat maka akan disimpan oleh fungsi ini {@link #save(Object)
     * save}.
     *
     * @param record record yang ingin disimpan.
     * @return Record yang sudah disimpan.
     * @see #createOnly(T)
     */
    public   Mono<T> create(T record) {
        return setGenerateId(record)
    		.flatMap(this::save)
    		.retryWhen(Retry.backoff(2, Duration.ofMillis(10))
    			.filter(this::isErrorCauseDuplicatePK)
			);
    }

    /**
     * Membuat date dengan menggunakan generate id.<br>
     * Override {@link #setGenerateId(Object) setGenerateId} untuk implementasi
     * generate id. <br>
     * Setelah record dibuat makan akan disimpan oleh fungsi ini
     * {@link #save(Object) save}.
     *
     * @param record record yang ingin disimpan.
     * @return Record yang sudah disimpan.
     * @see #create(T)
     */
    public   Mono<T> createOnly(T record) {
        return setGenerateId(record)
    		.flatMap(this::saveOnly)
    		.retryWhen(Retry.backoff(2, Duration.ofMillis(10))
    			.filter(this::isErrorCauseDuplicatePK)
			);
    }

    /**
     * Menyimpan record dengan melakukan deep Checking. Override
     * {@link #doBeforeSave(Object) doDeepCheck}.
     *
     * @param record Record yang ingin disimpan.
     * @return Record yang sudah disimpan.
     */
    public Mono<T> save(T record) {
        return Mono.just(record)
            .filter(checkUtil::validateEntity)
            .onErrorResume(Mono::error)
            .flatMap(this::doBeforeSave)
            .flatMap(getRepository()::save);
    }

    /**
     * Menyimpan record tanpa melakukan deep Checking.
     * Overrid {@link #doBeforeSaveOnly(Object) doBeforeSaveOnly} jika perlu.
     *
     * @param record Record yang ingin disimpan.
     * @return Record yang sudah disimpan.
     */
    public Mono<T> saveOnly(T record) {
        return Mono.just(record)
            .filter(checkUtil::validateEntity)
            .onErrorResume(Mono::error)
            .flatMap(this::doBeforeSaveOnly)
            .flatMap(getRepository()::save);
    }

    /**
     * Melakukan update kepada record lama menggunakan record baru. Sebelumnya
     * record harus mengimplementasi {@link UpdateAvailable}, jika tidak
     * mengimplemtasi tersebut maka record dianggap tidak support update.
     * <p>
     * Pertama yang dilakukan adalah mengambil record lama dengan ID pada tabel.
     * <p>
     * Sebelum record baru menimpa record lama makan akan dilakukan
     * {@link #doDeepCheckNewRecordBeforeUpdate(T) pengecekan} record baru terlebih
     * dahulu. Setelah itu record lama akan ditempa dengan record baru menggunakan
     * method {@link UpdateAvailable#update(T) update}.
     *
     * @param newRecord   Record baru yang akan menimpa record lama.
     * @param oldRecordId Id record lama.
     * @return Record yang sudah diupdate.
     */
    public   Mono<T> update(T newRecord, I oldRecordId) {
        if (newRecord instanceof UpdateAvailable) {
            Mono<T> newRecordChecked = Mono.just(newRecord).flatMap(this::doDeepCheckNewRecordBeforeUpdate);

            return Mono.zip(newRecordChecked, Mono.just(oldRecordId)).flatMap(z -> updateOnly(z.getT1(), z.getT2()));
        } else {
            throw new UnsupportedOperationException("record not support for update");
        }
    }

    /**
     * Melakukan update kepada record lama menggunakan record baru. Sebelumnya
     * record harus mengimplementasi {@link UpdateAvailable}, jika tidak
     * mengimplemtasi tersebut maka record dianggap tidak support update.
     * <p>
     * Bedanya dengan {@link #update(Object, Object) update}, {@code updateOnly}
     * tidak melakukan pengecekan {@link #doDeepCheckNewRecordBeforeUpdate(Object)}.
     * Method ini hanya akan mengupdate langsung ke database.
     *
     * @param newRecord   recordBaru
     * @param oldRecordId idRecordLama
     * @return Record yang sudah diupdate.
     */
    @SuppressWarnings("all")
    public   Mono<T> updateOnly(T newRecord, I oldRecordId) {
        if (newRecord instanceof UpdateAvailable) {
            return Mono.zip(getRepository().findById(oldRecordId), Mono.just(newRecord))
                    .switchIfEmpty(Mono.error(new DataNotFoundException("Id not found in db"))).map(z -> {
                        UpdateAvailable<T> recordUpdate = (UpdateAvailable<T>) z.getT1();
                        return recordUpdate.update(z.getT2());
                    }).flatMap(this::saveOnly);
        } else {
            throw new UnsupportedOperationException("Record not support for update");
        }
    }

    /**
     * Mendapatkan semua form.
     *
     * @param form form request.
     * @return Record yang tersimpan.
     */
    public Flux<T> getAllData(CommonParam form) {
        return Flux.just(form)
        .flatMap(f -> {
        	if(f.getSortBy() != null && !f.getSortBy().isBlank()) {
        		Sort sort = generateUtil.createSort(f.getSortBy(), f.isAsc());
        		return getRepository().findAll(sort).skip((f.getPage() - 1) * f.getLimit()).take(f.getLimit());
        	}else {
        		return getRepository().findAll().skip((f.getPage() - 1) * f.getLimit()).take(f.getLimit());
        	}
        });
    }

    /**
     * Check apakah record sudah tersedia. <br>
     * Jika id = NULL, maka akan return false.
     *
     * @param id record id yang ingin dicheck ketersediannya.
     * @return True jika tersedia, False jika tidak tersedia / Id Null.
     */
    public Mono<Boolean> isRecordExist(I id) {
        return Mono.justOrEmpty(id).flatMap(getRepository()::existsById).switchIfEmpty(Mono.just(false));
    }

    /**
     * Mendapatkan record sesuai dengan ID.
     *
     * @param id Id record yang ingin dicari.
     * @return Record yang dicari.
     */
    public Mono<T> getRecord(I id) {
        return getRepository().findById(id);
    }

    /**
     * Sama dengan {@link #getRecord(id)} hanya saja jika tidak ditemukan
     * maka akan throw exception.
     */
    public Mono<T> getRecordWithException(I id) {
        return getRecord(id)
            .switchIfEmpty(Mono.error(new DataNotFoundException("this record " + id + "can't be found")));
    }

    /**
     * Mendapatkan semua record sesuai dengan kumpulan id.
     *
     * @param ids Kumpulan ID record yang ingin dicari.
     * @return Kumpulan record yang dicari.
     */
    public Flux<T> getAllData(Flux<I> ids) {
        return getRepository().findAllById(ids);
    }

    /**
     * Menghapus record.
     *
     * @param id Id record yang ingin dihapus,
     * @return Pesan bahwa record sudah dihapus.
     */
    public Mono<String> deleteRecord(I id) {
        return getRepository().deleteById(id).then(Mono.just("Berhasil dihapus"));
    }
    
    //******************* HELPER ***********************
    
    private boolean isErrorCauseDuplicatePK(Throwable exception) {
    	if(exception instanceof DataIntegrityViolationException) {
    		log.debug("Find error...");
    		var ex = (DataIntegrityViolationException) exception;
    		log.debug(ex.getMessage());
    		return true;
    	}
    	return false;
    }
}
