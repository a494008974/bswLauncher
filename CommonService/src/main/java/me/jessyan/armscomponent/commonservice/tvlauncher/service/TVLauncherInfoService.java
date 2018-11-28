package me.jessyan.armscomponent.commonservice.tvlauncher.service;

import com.alibaba.android.arouter.facade.template.IProvider;

import me.jessyan.armscomponent.commonservice.tvlauncher.bean.TVLauncherInfo;

/**
 * Created by zhou on 2018/11/5.
 */

public interface TVLauncherInfoService extends IProvider {
    TVLauncherInfo getInfo();
}
