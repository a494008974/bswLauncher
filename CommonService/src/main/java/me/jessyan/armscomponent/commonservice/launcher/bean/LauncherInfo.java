package me.jessyan.armscomponent.commonservice.launcher.bean;

import me.jessyan.armscomponent.commonsdk.core.RouterHub;
import me.jessyan.armscomponent.commonservice.base.BaseInfo;

/**
 * Created by zhou on 2018/11/5.
 */

public class LauncherInfo  implements BaseInfo {
    private String name;
    @Override
    public String getName() {
        return name;
    }

    @Override
    public String getUrl() {
        return RouterHub.LAUNCHER_HOMEACTIVITY;
    }

    public LauncherInfo(String name) {
        this.name = name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
