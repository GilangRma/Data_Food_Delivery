package com.dimata.demo.user.demo_data_user.core.search;

public class LimitQuery implements LimitQueryStep {

    private long page;
    private long limit;

    private LimitQuery(long page, long limit) {
        setPage(page);
        setLimit(limit);
    }

    public static LimitQueryStep of(long page, long limit) {
        return new LimitQuery(page, limit);
    }

    @Override
    public String result() {
        if(limit <= 0 && page <= 0) {
            return "";
        }else if(page < 2) {
            return "LIMIT " + getLimit();  
        }else {
            return "LIMIT " + getOffset() + "," + getLimit();
        }
    }

    @Override
    public long getPage() {
        return page;
    }

    @Override
    public long getLimit() {
        return limit;
    }

    @Override
    public long getOffset() {
        return (page - 1) * limit;
    }

    @Override
    public void setPage(long page) {
        this.page = page;

    }

    @Override
    public void setLimit(long limit) {
        this.limit = limit;
    }
    
}
