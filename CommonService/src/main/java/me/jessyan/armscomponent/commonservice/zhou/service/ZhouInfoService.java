package me.jessyan.armscomponent.commonservice.zhou.service;

import com.alibaba.android.arouter.facade.template.IProvider;

import me.jessyan.armscomponent.commonservice.zhou.bean.ZhouInfo;

/**
 * Created by zhou on 2018/11/5.
 */

public interface ZhouInfoService extends IProvider {
    ZhouInfo getInfo();
}
