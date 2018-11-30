package me.jessyan.armscomponent.commonservice.tvlauncher.bean;

import me.jessyan.armscomponent.commonsdk.core.RouterHub;
import me.jessyan.armscomponent.commonservice.base.BaseInfo;

/**
 * Created by zhou on 2018/11/5.
 */

public class TVLauncherInfo  implements BaseInfo {
    private String name;
    @Override
    public String getName() {
        return name;
    }

    @Override
    public String getUrl() {
        return RouterHub.TVLAUNCHER_HOMEACTIVITY;
    }

    public TVLauncherInfo(String name) {
        this.name = name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
