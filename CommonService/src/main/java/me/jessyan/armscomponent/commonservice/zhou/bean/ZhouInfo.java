package me.jessyan.armscomponent.commonservice.zhou.bean;

import me.jessyan.armscomponent.commonsdk.core.RouterHub;
import me.jessyan.armscomponent.commonservice.base.BaseInfo;

/**
 * Created by zhou on 2018/11/5.
 */

public class ZhouInfo implements BaseInfo{
    private String name;
    @Override
    public String getName() {
        return name;
    }

    @Override
    public String getUrl() {
        return RouterHub.ZHOU_HOMEACTIVITY;
    }

    public ZhouInfo(String name) {
        this.name = name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
