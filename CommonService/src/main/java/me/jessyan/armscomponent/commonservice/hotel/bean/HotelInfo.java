package me.jessyan.armscomponent.commonservice.hotel.bean;

import me.jessyan.armscomponent.commonsdk.core.RouterHub;
import me.jessyan.armscomponent.commonservice.BaseInfo;

/**
 * Created by zhou on 2018/11/5.
 */

public class HotelInfo implements BaseInfo {
    private String name;
    @Override
    public String getName() {
        return name;
    }

    @Override
    public String getUrl() {
        return RouterHub.HOTEL_HOMEACTIVITY;
    }

    public HotelInfo(String name) {
        this.name = name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
