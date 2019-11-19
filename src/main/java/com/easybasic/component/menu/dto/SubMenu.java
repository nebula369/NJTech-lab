package com.easybasic.component.menu.dto;

import com.thoughtworks.xstream.annotations.XStreamAlias;
import com.thoughtworks.xstream.annotations.XStreamAsAttribute;

import java.io.Serializable;

@XStreamAlias("SubMenu")
public class SubMenu implements Serializable {

    @XStreamAsAttribute
    private String name;
    @XStreamAsAttribute
    private String href;
    @XStreamAsAttribute
    private String icon;
    private int linkTarget;

    private int menuId;
    private int userType;

    public int getUserType() {
        return userType;
    }

    public void setUserType(int userType) {
        this.userType = userType;
    }

    public int getMenuId() {
        return menuId;
    }

    public void setMenuId(int menuId) {
        this.menuId = menuId;
    }

    public int getLinkTarget() {
        return linkTarget;
    }

    public void setLinkTarget(int linkTarget) {
        this.linkTarget = linkTarget;
    }

    public String getName() {
        return name;
    }

    public String getHref() {
        return href;
    }

    public String getIcon() {
        return icon;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setHref(String href) {
        this.href = href;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }
}
