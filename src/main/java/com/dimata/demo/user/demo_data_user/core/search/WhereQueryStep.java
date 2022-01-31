package com.dimata.demo.user.demo_data_user.core.search;

public interface WhereQueryStep {
    String result();
    WhereQueryStep and(WhereQueryStep step);
    WhereQueryStep or(WhereQueryStep step);
    WhereQueryStep not(WhereQueryStep step);
}
