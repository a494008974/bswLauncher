package me.jessyan.armscomponent.commonservice.hotel.service;

import com.alibaba.android.arouter.facade.template.IProvider;

import me.jessyan.armscomponent.commonservice.hotel.bean.HotelInfo;

/**
 * Created by zhou on 2018/11/5.
 */

public interface HotelInfoService extends IProvider {
    HotelInfo getInfo();
}
