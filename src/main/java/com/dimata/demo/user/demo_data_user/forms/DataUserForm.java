package com.dimata.demo.user.demo_data_user.forms;


import com.dimata.demo.user.demo_data_user.core.api.RecordAdapter;
import com.dimata.demo.user.demo_data_user.models.table.DataUser;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class DataUserForm implements RecordAdapter<DataUser> {

    private Long id;
    private String email;
    private String nama;
    private String alamat;
    private String phone_num;
    private String photo_profil;

    @Override
    public DataUser convertToRecord() {
        return DataUser.Builder.emptyBuilder()
            .alamat(alamat)
            .email(email)
            .id(id)
            .nama(nama)
            .phone_num(phone_num)
            .photo_profil(photo_profil)
            .build();
    }

    @Override
    public DataUser convertNewRecord() {
       
        return DataUser.Builder.createNewRecord(email, nama, alamat, phone_num, photo_profil)
        .id(id)
        .build();
    }
}
