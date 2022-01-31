package com.dimata.demo.user.demo_data_user.models.table;

import com.dimata.demo.user.demo_data_user.core.api.UpdateAvailable;
import com.dimata.demo.user.demo_data_user.core.util.GenerateUtil;
import com.dimata.demo.user.demo_data_user.core.util.ManipulateUtil;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;

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
public class UserMain implements UpdateAvailable<UserMain>, Persistable<Long>{
    
    public static final String TABLE_NAMA = "user_main";
    public static final String ID_COL = "id_main";
    public static final String ID_USER_COL = "id_user";
    public static final String ID_PRODUCT_COL = "id_product";

    @Accessors(fluent = true)
    @Setter
    public static class Builder {

        private Long id;
        private Long id_user;
        private Long id_product;

        @Setter(AccessLevel.PRIVATE)        private boolean newRecord = false;

        public static Builder createNewRecord(){
            return new Builder().newRecord(true);
               
        }

        public static Builder updateBuilder(UserMain oldRecord, UserMain newRecord){
            return new Builder()
                .id(oldRecord.getId())
                .id_user(changeItOrNot(newRecord.getId_user(),oldRecord.getId_user()))
                .id_product(changeItOrNot(newRecord.getId_product(),oldRecord.getId_product()));

        }

        public static Builder empetyBuilder(){
            return new Builder();
        }

        public UserMain build(){
            UserMain result = new UserMain();

            result.setId(id);
            result.setId_user(id_user);
            result.setId_product(id_product);
            return result;
        }
    }

    @Id
    @Column(ID_COL)
    private Long id;
    private Long id_user;
    private Long id_product;

    @Transient
    @JsonIgnore
    private Long insertId;

    public static UserMain fromRow(Row row) {
        var result = new UserMain();
        result.setId(ManipulateUtil.parseRow(row, ID_COL, Long.class));
        result.setId_user(ManipulateUtil.parseRow(row, ID_USER_COL, Long.class));
        result.setId_product(ManipulateUtil.parseRow(row, ID_PRODUCT_COL, Long.class));
        
        return result;
    }

    public static Long changeItOrNot(Long id_user2, Long id_user3) {
        return null;
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
    public UserMain update(UserMain newData) {
        return Builder.updateBuilder(this, newData).build();
    }


}
