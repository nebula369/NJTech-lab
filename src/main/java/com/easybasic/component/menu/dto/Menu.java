package com.easybasic.component.menu.dto;

import com.thoughtworks.xstream.annotations.XStreamAlias;
import com.thoughtworks.xstream.annotations.XStreamAsAttribute;
import com.thoughtworks.xstream.annotations.XStreamImplicit;

import java.io.Serializable;
import java.util.List;

@XStreamAlias("Menu")
public class Menu implements Serializable {

    @XStreamAsAttribute
    private String name;
    @XStreamAsAttribute
    private String icon;
    @XStreamImplicit
    private List<SubMenu> subMenuList;

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

    public String getName() {
        return name;
    }

    public String getIcon() {
        return icon;
    }

    public List<SubMenu> getSubMenuList() {
        return subMenuList;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    public void setSubMenuList(List<SubMenu> subMenuList) {
        this.subMenuList = subMenuList;
    }
}
