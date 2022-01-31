package com.dimata.demo.user.demo_data_user.forms;


import com.dimata.demo.user.demo_data_user.core.api.RecordAdapter;
import com.dimata.demo.user.demo_data_user.models.table.DataProduct;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class DataProductForm implements RecordAdapter<DataProduct> {

    private Long id;
    private String nama_product;
    private String harga_product;
    private String delivery_info;
    private String return_policy;
    private String image_product;

    @Override
    public DataProduct convertToRecord() {
        return DataProduct.Builder.emptyBuilder()
            .id(id)
            .nama_product(nama_product)
            .harga_product(harga_product)
            .delivery_info(delivery_info)
            .return_policy(return_policy)
            .image_product(image_product)
            .build();
    }

    @Override
    public DataProduct convertNewRecord() {
        return DataProduct.Builder.createNewRecord(nama_product, harga_product, delivery_info,return_policy,image_product)
        .id(id)
        .build();
    }
}
