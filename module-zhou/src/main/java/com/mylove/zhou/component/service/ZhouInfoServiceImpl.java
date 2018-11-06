package com.mylove.zhou.component.service;

import android.content.Context;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.jess.arms.utils.ArmsUtils;
import com.mylove.zhou.R;

import me.jessyan.armscomponent.commonsdk.core.RouterHub;
import me.jessyan.armscomponent.commonservice.zhou.bean.ZhouInfo;
import me.jessyan.armscomponent.commonservice.zhou.service.ZhouInfoService;

/**
 * Created by zhou on 2018/11/5.
 */
@Route(path = RouterHub.ZHOU_SERVICE_ZHOUINFOSERVICE, name = "模块测试")
public class ZhouInfoServiceImpl implements ZhouInfoService {
    private Context mContext;
    @Override
    public ZhouInfo getInfo() {
        return new ZhouInfo(ArmsUtils.getString(mContext, R.string.public_name_zhou));
    }

    @Override
    public void init(Context context) {
        mContext = context;
    }
}
