package com.jinva.support.page;

import java.util.Collection;

public class Page<T> {

    private Collection<T> data;

    private long totalCount;

    private int pageSize;

    private int pageNum;

    public Page() {
        super();
    }

    public Page(Collection<T> data, int totalCount, int pageSize, int pageNum) {
        super();
        this.data = data;
        this.totalCount = totalCount;
        this.pageSize = pageSize;
        this.pageNum = pageNum;
    }

    public long getTotalCount() {
        return totalCount;
    }

    public void setTotalCount(long totalCount) {
        this.totalCount = totalCount;
    }

    public int getPageSize() {
        return pageSize;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }

    public int getPageNum() {
        return pageNum;
    }

    public void setPageNum(int pageNum) {
        this.pageNum = pageNum;
    }

    public Collection<T> getData() {
        return data;
    }

    public void setData(Collection<T> data) {
        this.data = data;
    }

}
