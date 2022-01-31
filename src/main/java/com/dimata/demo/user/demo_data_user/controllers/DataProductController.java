package com.dimata.demo.user.demo_data_user.controllers;

import com.dimata.demo.user.demo_data_user.core.search.CommonParam;
import com.dimata.demo.user.demo_data_user.forms.DataProductForm;
import com.dimata.demo.user.demo_data_user.models.table.DataProduct;
import com.dimata.demo.user.demo_data_user.services.api.DataProductApi;

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
public class DataProductController {

    @Autowired
    private DataProductApi dataProductApi;

    private static final String BASE_URL = "/maintainer/v1";

    @PostMapping(path = BASE_URL + "/data_product", consumes = MediaType.APPLICATION_JSON_VALUE)
    public Mono<DataProduct> maintainerAddDataProduct(@RequestBody DataProductForm form) {
       
        return dataProductApi.createDataProduct(form);
    }

    @GetMapping(path = BASE_URL + "/data_product")
    public Flux<DataProduct> maintainerGetAllDataProduct(CommonParam param) {
       
        return dataProductApi.getAllDataProduct(param);
    }

    @GetMapping(path = BASE_URL + "/data_product/{id_product}")
    public Mono<DataProduct> maintainerGetDataProduct(@PathVariable("id_product") Long id_product) {
       
        return dataProductApi.getDataProduct(id_product);
    }

    @PutMapping(path = BASE_URL + "/data_product/{id_product}")
    public Mono<DataProduct> maintainerUpdateDataProduct(@PathVariable("id_product") long id_product, @RequestBody DataProductForm form) {
        return dataProductApi.updateDataProduct(id_product, form);
    }

}
