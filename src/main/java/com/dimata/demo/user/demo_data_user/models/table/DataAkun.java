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

public class DataAkun implements UpdateAvailable<DataAkun>, Persistable<Long>{
    
    public static final String TABLE_NAME = "data_akun";
    public static final String ID_COL = "id_akun";
    public static final String EMAIL_COL = "email";
    public static final String PASSWORD_COL = "password";
   
    @Accessors(fluent = true)
    @Setter
    public static class Builder {

        private Long id;
        private String email;
        private String password;
       
        @Setter(AccessLevel.PRIVATE)
        private boolean newRecord = false;

        public static Builder createNewRecord(String email, String password) {
            return new Builder().newRecord(true)
                .email(Objects.requireNonNull(email, "Email diperlukan"))
                .password(Objects.requireNonNull(password, "Password tidak boleh kosong"));
        }

        public static Builder updateBuilder(DataAkun oldRecord, DataAkun newRecord) {
            return new Builder()
                .id(oldRecord.getId())
                .email(changeItOrNot(newRecord.getEmail(), oldRecord.getEmail()))
                .password(changeItOrNot(newRecord.getPassword(), oldRecord.getPassword()));
        }

        public static Builder emptyBuilder() {
            return new Builder();
        }

        public DataAkun build() {
            DataAkun result = new DataAkun();

            result.setId(id);
            result.setEmail(email);
            result.setPassword(password);
            return result;
        }
    }

    @Id
    @Column(ID_COL)
    private Long id;
    private String email;
    private String password;
    
    @JsonSerialize(converter = DateSerialize.class)
  
    @Transient
    @JsonIgnore
    private Long insertId;

    public static DataAkun fromRow(Row row) {
        var result = new DataAkun();
        result.setId(ManipulateUtil.parseRow(row, ID_COL, Long.class));
        result.setEmail(ManipulateUtil.parseRow(row, EMAIL_COL, String.class));
        result.setPassword(ManipulateUtil.parseRow(row, PASSWORD_COL, String.class));
       
        
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
    public DataAkun update(DataAkun newData) {
        return Builder.updateBuilder(this, newData).build();
    }

    
}
