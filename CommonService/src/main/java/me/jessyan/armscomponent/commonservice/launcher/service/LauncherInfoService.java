package me.jessyan.armscomponent.commonservice.launcher.service;

import com.alibaba.android.arouter.facade.template.IProvider;

import me.jessyan.armscomponent.commonservice.launcher.bean.LauncherInfo;

/**
 * Created by zhou on 2018/11/5.
 */

public interface LauncherInfoService extends IProvider {
    LauncherInfo getInfo();
}
