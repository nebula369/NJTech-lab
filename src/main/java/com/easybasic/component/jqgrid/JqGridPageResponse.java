package com.easybasic.component.jqgrid;

import java.util.List;
import java.util.Map;

public class JqGridPageResponse<T> {

    /**
     * 总页数
     */
    private Integer total = 0;

    /**
     * 当前页
     */
    private Integer page = 0;

    /**
     * 本次查询总记录数
     */
    private long records = 0;

    /**
     * 结果集
     */
    private List<T> rows;

    //自定义数据
    private Map<String, Object> userdata;

    public Integer getTotal() {
        return total;
    }

    public void setTotal(Integer total) {
        this.total = total;
    }

    public Integer getPage() {
        return page;
    }

    public void setPage(Integer page) {
        this.page = page;
    }

    public long getRecords() {
        return records;
    }

    public void setRecords(long records) {
        this.records = records;
    }

    public List<T> getRows() {
        return rows;
    }

    public void setRows(List<T> rows) {
        this.rows = rows;
    }

    public Map<String, Object> getUserdata() {
        return userdata;
    }

    public void setUserdata(Map<String, Object> userdata) {
        this.userdata = userdata;
    }
}
