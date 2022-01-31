package com.dimata.demo.user.demo_data_user.forms;

import com.dimata.demo.user.demo_data_user.core.api.RecordAdapter;
import com.dimata.demo.user.demo_data_user.models.table.UserMain;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class UserMainForm implements RecordAdapter<UserMain> {

    private Long id;
    private Long id_user;
    private Long id_product;

    @Override
    public UserMain convertToRecord() {
        return UserMain.Builder.empetyBuilder()
            .id_user(id_user)
            .id_product(id_product)
            .build();
    }

    @Override
    public UserMain convertNewRecord() {

        return UserMain.Builder.createNewRecord()
        .id_user(id_user)
        .id_product(id_product)
        .id(id)
            .build();
    }
}