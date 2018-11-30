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

    public static void saveCls(List<ClsBean> clsBeans){
        ClsBeanDao dao = CommonApp.getInstance().getDaoSession().getClsBeanDao();
        dao.insertOrReplaceInTx(clsBeans);
    }

    public static void saveInfo(List<InfoBean> infoBeans){
        InfoBeanDao dao = CommonApp.getInstance().getDaoSession().getInfoBeanDao();
        dao.insertOrReplaceInTx(infoBeans);
    }

    public static InfoBean fetchInfoBean(String tag){
        InfoBeanDao dao = CommonApp.getInstance().getDaoSession().getInfoBeanDao();
        return dao.load(tag);
    }

    public static List<InfoBean> fetchInfoBeans(){
        InfoBeanDao dao = CommonApp.getInstance().getDaoSession().getInfoBeanDao();
        return dao.queryBuilder().where(InfoBeanDao.Properties.Tag.like("1%")).orderAsc(InfoBeanDao.Properties.Tag).limit(8).list();
    }
}
