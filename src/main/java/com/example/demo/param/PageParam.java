package com.example.demo.param;

public class PageParam {
    private Integer pageIndex = 1;
    private Integer pageSize = 10;

    public Integer getPageIndex() {
        return pageIndex;
    }

    public void setPageIndex(Integer pageIndex) {
        this.pageIndex = pageIndex<1?1:pageIndex;
    }

    public Integer getPageSize() {
        return pageSize;
    }

    public void setPageSize(Integer pageSize) {
        this.pageSize = pageSize<1?10:pageSize;
    }
}
