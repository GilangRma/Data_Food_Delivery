package com.dimata.demo.user.demo_data_user.controllers;

import com.dimata.demo.user.demo_data_user.core.search.CommonParam;
import com.dimata.demo.user.demo_data_user.forms.ChekUserAndPasswordForm;
import com.dimata.demo.user.demo_data_user.forms.DataAkunForm;
import com.dimata.demo.user.demo_data_user.models.table.DataAkun;
import com.dimata.demo.user.demo_data_user.models.table.DataUser;
import com.dimata.demo.user.demo_data_user.services.api.DataAkunApi;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping(produces = MediaType.APPLICATION_JSON_VALUE)
public class DataAkunControler {
   
    @Autowired
    private DataAkunApi dataAkunApi;

    private static final String BASE_URL = "/maintainer/v1";

    @PostMapping(path = BASE_URL + "/data_akun", consumes = MediaType.APPLICATION_JSON_VALUE)
    public Mono<DataAkun> maintainerAddDataAkun(@RequestBody DataAkunForm form) {
     
        return dataAkunApi.createDataAkun(form);
    }

    @GetMapping(path = BASE_URL + "/data_akun")
    public Flux<DataAkun> maintainerGetAllDataAkun(CommonParam param) {
        
        return dataAkunApi.getAllDataAkun(param);
    }

    @GetMapping(path = BASE_URL + "/data_akun/{id_akun}")
    public Mono<DataAkun> maintainerGetDataAkun(@PathVariable("id_akun") Long id_akun) {
        
        return dataAkunApi.getDataAkun(id_akun);
    }

    @PutMapping(path = BASE_URL + "/data_akun/{id_akun}")
    public Mono<DataAkun> maintainerUpdateDataAkun(@PathVariable("id_akun") long id_akun, @RequestBody DataAkunForm form) {
        return dataAkunApi.updateDataAkun(id_akun, form);
    }

    @PostMapping(path = BASE_URL + "/user/login")
    public Mono<DataUser> maintainerLoginUser(@RequestBody ChekUserAndPasswordForm form) {
        return dataAkunApi.checkAvailebleData(form)
            .flatMap(f -> dataAkunApi.getUserDetail(f.getEmail()));
    }
}
