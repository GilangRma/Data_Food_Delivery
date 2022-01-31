package com.dimata.demo.user.demo_data_user.models.table;

import static com.dimata.demo.user.demo_data_user.core.util.ManipulateUtil.changeItOrNot;

import java.util.Objects;

import com.dimata.demo.user.demo_data_user.core.api.UpdateAvailable;
import com.dimata.demo.user.demo_data_user.core.util.GenerateUtil;
import com.dimata.demo.user.demo_data_user.core.util.ManipulateUtil;
import com.dimata.demo.user.demo_data_user.core.util.jackson.DateSerialize;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Transient;
import org.springframework.data.domain.Persistable;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

import io.r2dbc.spi.Row;
import lombok.AccessLevel;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;

@Data
@Table
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@JsonInclude(JsonInclude.Include.NON_NULL)

public class DataUser implements UpdateAvailable<DataUser>, Persistable<Long>{
    
    public static final String TABLE_NAME = "data_User";
    public static final String ID_COL = "id_user";
    public static final String EMAIL_COL = "email";
    public static final String NAMA_COL = "nama";
    public static final String ALAMAT_COL = "alamat";
    public static final String PHONE_NUM_COL = "phone_num";
    public static final String PHOTO_PROFIL_COL = "photo_profil";
   
    @Accessors(fluent = true)
    @Setter
    public static class Builder {

        private Long id;
        private String email;
        private String nama;
        private String alamat;
        private String phone_num;
        private String photo_profil;
       
        @Setter(AccessLevel.PRIVATE)
        private boolean newRecord = false;

        public static Builder createNewRecord(String email, String nama, String alamat, String phone_num, String photo_profil) {
            return new Builder().newRecord(true)
                .email(Objects.requireNonNull(email, "Email diperlukan"))
                .nama(Objects.requireNonNull(nama, "Nama tidak boleh kosong"))
                .alamat(Objects.requireNonNull(alamat, "Alamat tidak boleh kosong"))
                .phone_num(Objects.requireNonNull(phone_num, "Phone_num tidak boleh kosong"));
        }

        public static Builder updateBuilder(DataUser oldRecord, DataUser newRecord) {
            return new Builder()
                .id(oldRecord.getId())
                .alamat(changeItOrNot(newRecord.getAlamat(), oldRecord.getAlamat()))
                .email(changeItOrNot(newRecord.getEmail(), oldRecord.getEmail()))
                .nama(changeItOrNot(newRecord.getNama(), oldRecord.getNama()))
                .phone_num(changeItOrNot(newRecord.getPhone_num(), oldRecord.getPhone_num()))
                .photo_profil(changeItOrNot(newRecord.getPhoto_profil(), oldRecord.getPhoto_profil()));
        }

        public static Builder emptyBuilder() {
            return new Builder();
        }

        public DataUser build() {
            DataUser result = new DataUser();

            result.setId(id);
            result.setAlamat(alamat);
            result.setEmail(email);
            result.setNama(nama);
            result.setPhone_num(phone_num);
            result.setPhoto_profil(photo_profil);
            return result;
        }

        public static Builder createNewRecord(Long id_akun, Long id_user, Long id_product) {
            return null;
        }
    }

    @Id
    @Column(ID_COL)
    private Long id;
    private String email;
    private String nama;
    private String alamat;
    private String phone_num;
    private String photo_profil;
    @JsonSerialize(converter = DateSerialize.class)
  
    @Transient
    @JsonIgnore
    private Long insertId;

    public static DataUser fromRow(Row row) {
        var result = new DataUser();
        result.setId(ManipulateUtil.parseRow(row, ID_COL, Long.class));
        result.setEmail(ManipulateUtil.parseRow(row, EMAIL_COL, String.class));
        result.setNama(ManipulateUtil.parseRow(row, NAMA_COL, String.class));
        result.setPhone_num(ManipulateUtil.parseRow(row, PHONE_NUM_COL, String.class));
        result.setAlamat(ManipulateUtil.parseRow(row, ALAMAT_COL, String.class));
        result.setPhoto_profil(ManipulateUtil.parseRow(row, PHOTO_PROFIL_COL, String.class));
        
        return result;
    }

    @Override
    public boolean isNew() {
        if (id == null && insertId == null) {
            id = new GenerateUtil().generateOID();
            return true;
        } else if (id == null) {
            id = insertId;
            return true;
        }
        return false;
    }

    @Override
    public DataUser update(DataUser newData) {
        return Builder.updateBuilder(this, newData).build();
    }

    public Object getPassword() {
        return null;
    }

    
}
