package com.dimata.demo.user.demo_data_user.forms;

import com.dimata.demo.user.demo_data_user.core.api.RecordAdapter;
import com.dimata.demo.user.demo_data_user.models.table.DataAkun;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class DataAkunForm implements RecordAdapter<DataAkun> {

    private Long id;
    private String email;
    private String password;

    @Override
    public DataAkun convertToRecord() {
        return DataAkun.Builder.emptyBuilder()
            .email(email)
            .password(password)
            .build();
    }

    @Override
    public DataAkun convertNewRecord() {

        return DataAkun.Builder.createNewRecord(email, password)
            .id(id)
            .build();

    }

}
