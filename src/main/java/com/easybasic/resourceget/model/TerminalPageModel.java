package com.easybasic.resourceget.model;

import java.io.Serializable;
import java.util.List;

public class TerminalPageModel implements Serializable {

    private List<Terminal> list;
    private Integer total;
    private Integer pageNum;
    private Integer pageSize;

    public List<Terminal> getList() {
        return list;
    }
    public void setList(List<Terminal> list) {
        this.list = list;
    }

    public Integer getTotal() {
        return total;
    }
    public void setTotal(Integer total) {
        this.total = total;
    }

    public Integer getPageNum() {
        return pageNum;
    }
    public void setPageNum(Integer pageNum) {
        this.pageNum = pageNum;
    }

    public Integer getPageSize() {
        return pageSize;
    }
    public void setPageSize(Integer pageSize) {
        this.pageSize = pageSize;
    }
}
