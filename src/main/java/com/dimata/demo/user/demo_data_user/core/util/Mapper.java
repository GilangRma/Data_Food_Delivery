package com.dimata.demo.user.demo_data_user.core.util;

@FunctionalInterface
public interface Mapper<T> {
    T mapping();
}
