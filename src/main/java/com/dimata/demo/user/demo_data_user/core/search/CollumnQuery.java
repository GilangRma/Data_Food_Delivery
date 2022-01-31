package com.dimata.demo.user.demo_data_user.core.search;

import lombok.EqualsAndHashCode;
import lombok.ToString;

@ToString
@EqualsAndHashCode
public class CollumnQuery implements CollumnStep {

    private StringBuilder scriptBuilder;

    private CollumnQuery(){
        scriptBuilder = new StringBuilder();
    }

    public static CollumnStep add(String fieldName) {
        CollumnQuery instance = new CollumnQuery();
        instance.scriptBuilder.append(fieldName);
        return instance;
    }

    public static CollumnStep addBracket(String fieldName) {
        CollumnQuery instance = new CollumnQuery();
        instance.scriptBuilder.append("(").append(fieldName).append(")");
        return instance;
    }

    //*************** INTERFACE IMPLEMENTS ***************/

    @Override
    public CollumnStep as(String alias) {
        scriptBuilder.append(" AS ").append(alias.trim().replace(" ", "_"));
        return this;
    }

    @Override
    public String result() {
        return scriptBuilder.toString();
    }
    
}
