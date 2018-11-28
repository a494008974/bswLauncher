package com.mylove.tvlauncher.component.service;

import android.content.Context;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.jess.arms.utils.ArmsUtils;
import com.mylove.tvlauncher.R;

import me.jessyan.armscomponent.commonsdk.core.RouterHub;
import me.jessyan.armscomponent.commonservice.tvlauncher.bean.TVLauncherInfo;
import me.jessyan.armscomponent.commonservice.tvlauncher.service.TVLauncherInfoService;

/**
 * Created by zhou on 2018/11/5.
 */
@Route(path = RouterHub.TVLAUNCHER_SERVICE_LAUNCHERINFOSERVICE, name = "TV桌面")
public class TVLauncherInfoServiceImpl implements TVLauncherInfoService {
    private Context mContext;
    @Override
    public TVLauncherInfo getInfo() {
        return new TVLauncherInfo(ArmsUtils.getString(mContext, R.string.public_name_tvlauncher));
    }

    @Override
    public void init(Context context) {
        mContext = context;
    }
}

