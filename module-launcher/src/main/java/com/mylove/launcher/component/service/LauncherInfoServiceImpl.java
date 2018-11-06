package com.mylove.launcher.component.service;

import android.content.Context;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.jess.arms.utils.ArmsUtils;
import com.mylove.launcher.R;

import me.jessyan.armscomponent.commonsdk.core.RouterHub;
import me.jessyan.armscomponent.commonservice.launcher.bean.LauncherInfo;
import me.jessyan.armscomponent.commonservice.launcher.service.LauncherInfoService;

/**
 * Created by zhou on 2018/11/5.
 */
@Route(path = RouterHub.LAUNCHER_SERVICE_LAUNCHERINFOSERVICE, name = "应用桌面")
public class LauncherInfoServiceImpl implements LauncherInfoService{
    private Context mContext;
    @Override
    public LauncherInfo getInfo() {
        return new LauncherInfo(ArmsUtils.getString(mContext, R.string.public_name_launcher));
    }

    @Override
    public void init(Context context) {
        mContext = context;
    }
}

