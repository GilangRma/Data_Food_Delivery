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

public class DataProduct implements UpdateAvailable<DataProduct>, Persistable<Long>{
    
    public static final String TABLE_NAME = "data_product";
    public static final String ID_COL = "id_product";
    public static final String NAMA_PRODUCT_COL = "nama_product";
    public static final String HARGA_PRODUCT_COL = "harga_product";
    public static final String DELIVERY_INFO_COL = "delivery_info";
    public static final String RETURN_POLICY_COL = "return_policy";
    public static final String IMAGE_PRODUCT_COL = "image_product";

    @Accessors(fluent = true)
    @Setter
    public static class Builder {

        private Long id;
        private String nama_product;
        private String harga_product;
        private String delivery_info;
        private String return_policy;
        private String image_product;
       
        @Setter(AccessLevel.PRIVATE)
        private boolean newRecord = false;

        public static Builder createNewRecord(String nama_product, String harga_product, String delivery_info, String return_policy, String image_product) {
            return new Builder().newRecord(true)
                .nama_product(Objects.requireNonNull(nama_product, "Nama Product diperlukan"))
                .harga_product(Objects.requireNonNull(harga_product, "Harga Product tidak boleh kosong"))
                .delivery_info(Objects.requireNonNull(delivery_info, "Delivery info tidak boleh kosong"))
                .return_policy(Objects.requireNonNull(return_policy, "Return Policy tidak boleh kosong"))
                .image_product(Objects.requireNonNull(image_product, "Image Product tidak boleh kosong"));

        }

        public static Builder updateBuilder(DataProduct oldRecord, DataProduct newRecord) {
            return new Builder()
                .id(oldRecord.getId())
                .nama_product(changeItOrNot(newRecord.getNama_product(), oldRecord.getNama_product()))
                .harga_product(changeItOrNot(newRecord.getHarga_product(), oldRecord.getHarga_product()))
                .delivery_info(changeItOrNot(newRecord.getDelivery_info(), oldRecord.getDelivery_info()))
                .return_policy(changeItOrNot(newRecord.getReturn_policy(), oldRecord.getReturn_policy()))
                .image_product(changeItOrNot(newRecord.getImage_product(), oldRecord.getImage_product()));
        }

        public static Builder emptyBuilder() {
            return new Builder();
        }

        public DataProduct build() {
            DataProduct result = new DataProduct();

            result.setId(id);
            result.setNama_product(nama_product);
            result.setHarga_product(harga_product);
            result.setDelivery_info(delivery_info);
            result.setReturn_policy(return_policy);
            result.setImage_product(image_product);
            return result;
        }
    }

    @Id
    @Column(ID_COL)
    private Long id;
    private String nama_product;
    private String harga_product;
    private String delivery_info;
    private String return_policy;
    private String image_product;
    @JsonSerialize(converter = DateSerialize.class)
  
    @Transient
    @JsonIgnore
    private Long insertId;

    public static DataProduct fromRow(Row row) {
        var result = new DataProduct();
        result.setId(ManipulateUtil.parseRow(row, ID_COL, Long.class));
        result.setNama_product(ManipulateUtil.parseRow(row, NAMA_PRODUCT_COL, String.class));
        result.setHarga_product(ManipulateUtil.parseRow(row, HARGA_PRODUCT_COL, String.class));
        result.setDelivery_info(ManipulateUtil.parseRow(row, DELIVERY_INFO_COL, String.class));
        result.setReturn_policy(ManipulateUtil.parseRow(row, RETURN_POLICY_COL, String.class)); 
        result.setImage_product(ManipulateUtil.parseRow(row, IMAGE_PRODUCT_COL, String.class));    
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
    public DataProduct update(DataProduct newData) {
        return Builder.updateBuilder(this, newData).build();
    }

    public Object getPassword() {
        return null;
    }

    
}
