package me.jessyan.armscomponent.commonservice.dao;

import java.util.List;

import me.jessyan.armscomponent.commonservice.CommonApp;

/**
 * Created by zhou on 2018/11/10.
 */

public class DaoHelper {
    public static void insert(HotelEntity hotelDao){
        HotelEntityDao dao = CommonApp.getInstance().getDaoSession().getHotelEntityDao();
        dao.insertOrReplace(hotelDao);
    }

    public static List<HotelEntity> fetchHotelEntity(){
        HotelEntityDao dao = CommonApp.getInstance().getDaoSession().getHotelEntityDao();
        return dao.loadAll();
    }
}
