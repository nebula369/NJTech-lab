package com.easybasic.resourceget.model;

import java.io.Serializable;

public class VideoCategory implements Serializable {
    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column basic_ds.pkid
     *
     * @mbggenerated
     */
    private Integer pkid;
    private String name;
    private String parentid;
    private Integer showorder;
    private Integer level;
    private String isLast;
    private Integer terminalCount;

    public Integer getPkid() {
        return pkid;
    }
    public void setPkid(Integer pkid) {
        this.pkid = pkid;
    }

    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name == null ? null : name.trim();
    }

    public String getParentid() {
        return parentid;
    }
    public void setParentid(String parentid) {
        this.parentid = parentid == null ? null : parentid.trim();
    }

    public Integer getShoworder() {
        return showorder;
    }
    public void setShoworder(Integer showorder) {
        this.showorder = showorder;
    }

    public Integer getLevel() {
        return level;
    }
    public void setLevel(Integer level) {
        this.level = level;
    }

    public Integer getTerminalCount() {
        return terminalCount;
    }
    public void setTerminalCount(Integer terminalCount) {
        this.terminalCount = terminalCount;
    }

    public String getIsLast() {
        return isLast;
    }
    public void setIsLast(String isLast) {
        this.isLast = isLast == null ? null : isLast.trim();
    }
}