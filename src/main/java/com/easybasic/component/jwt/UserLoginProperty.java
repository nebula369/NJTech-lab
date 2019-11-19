package com.easybasic.component.jwt;


import com.easybasic.basic.model.Unit;
import com.easybasic.basic.model.User;
import java.io.Serializable;
import java.util.Date;

//用户登录后，一些权限类的信息保存
public class UserLoginProperty implements Serializable {

    public User CurrentUser;
    public String LoginIP;
    public Date LoginTime;

    private boolean isUnitManager;
    private Unit manageUnit;

    public boolean isUnitManager() {
        return isUnitManager;
    }

    public void setUnitManager(boolean unitManager) {
        isUnitManager = unitManager;
    }


    public void setManageUnit(Unit manageUnit) {
        this.manageUnit = manageUnit;
    }


    public int getCurrentUserManageUnitId()
    {
        if(isUnitManager)
        {
            return manageUnit.getPkid();
        }
        return 1;
    }
}
