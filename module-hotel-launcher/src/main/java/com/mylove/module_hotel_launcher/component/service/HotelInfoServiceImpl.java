package com.mylove.module_hotel_launcher.component.service;

import android.content.Context;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.jess.arms.utils.ArmsUtils;
import com.mylove.module_hotel_launcher.R;

import me.jessyan.armscomponent.commonsdk.core.RouterHub;
import me.jessyan.armscomponent.commonservice.hotel.bean.HotelInfo;
import me.jessyan.armscomponent.commonservice.hotel.service.HotelInfoService;

/**
 * Created by zhou on 2018/11/5.
 */
@Route(path = RouterHub.HOTEL_SERVICE_HOTELINFOSERVICE, name = "酒店桌面")
public class HotelInfoServiceImpl implements HotelInfoService {
    private Context mContext;
    @Override
    public HotelInfo getInfo() {
        return new HotelInfo(ArmsUtils.getString(mContext, R.string.public_name_hotel));
    }

    @Override
    public void init(Context context) {
        mContext = context;
    }
}
