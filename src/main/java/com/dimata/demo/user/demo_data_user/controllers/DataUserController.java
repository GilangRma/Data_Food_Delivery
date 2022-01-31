package com.dimata.demo.user.demo_data_user.controllers;

import com.dimata.demo.user.demo_data_user.core.search.CommonParam;
import com.dimata.demo.user.demo_data_user.forms.DataUserForm;
import com.dimata.demo.user.demo_data_user.models.table.DataUser;
import com.dimata.demo.user.demo_data_user.services.api.DataUserApi;

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
public class DataUserController {

    @Autowired
    private DataUserApi dataUserApi;

    private static final String BASE_URL = "/maintainer/v1";

    @PostMapping(path = BASE_URL + "/data_user", consumes = MediaType.APPLICATION_JSON_VALUE)
    public Mono<DataUser> maintainerAddDataUser(@RequestBody DataUserForm form) {
       
        return dataUserApi.createDataUser(form);
    }

    @GetMapping(path = BASE_URL + "/data_user")
    public Flux<DataUser> maintainerGetAllDataUser(CommonParam param) {
       
        return dataUserApi.getAllDataUser(param);
    }

    @GetMapping(path = BASE_URL + "/data_user/{id_user}")
    public Mono<DataUser> maintainerGetDataUser(@PathVariable("id_user") Long id_user) {
       
        return dataUserApi.getDataUser(id_user);
    }

    @PutMapping(path = BASE_URL + "/data_user/{id_user}")
    public Mono<DataUser> maintainerUpdateDataUser(@PathVariable("id_user") long id_user, @RequestBody DataUserForm form) {
        return dataUserApi.updateDataUser(id_user, form);
    }

}
