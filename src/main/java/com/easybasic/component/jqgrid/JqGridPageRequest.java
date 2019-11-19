package com.easybasic.component.jqgrid;

public class JqGridPageRequest {

    /**
     * 当前页
     */
    private Integer page;

    /**
     * 每页记录数
     */
    private Integer rows;

    /**
     * 排序字段
     */
    private String sidx;

    /**
     * 排序
     */
    private String sord;

    /**
     * 搜索参数经过mydictionary序列化后的数据
     */
    private String searchParam;

    public Integer getPage() {
        return page;
    }

    public void setPage(Integer page) {
        this.page = page;
    }

    public Integer getRows() {
        return rows;
    }

    public void setRows(Integer rows) {
        this.rows = rows;
    }

    public String getSidx() {
        return sidx;
    }

    public void setSidx(String sidx) {
        this.sidx = sidx;
    }

    public String getSord() {
        return sord;
    }

    public void setSord(String sord) {
        this.sord = sord;
    }

    public String getSearchParam() {
        return searchParam;
    }

    public void setSearchParam(String searchParam) {
        this.searchParam = searchParam;
    }
}
