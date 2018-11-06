package me.jessyan.armscomponent.commonservice.zhou.dao;

import me.jessyan.armscomponent.commonservice.CommonApp;
import me.jessyan.armscomponent.commonservice.launcher.bean.DaoSession;
import me.jessyan.armscomponent.commonservice.zhou.bean.User;

/**
 * Created by zhou on 2018/11/5.
 */

public class ZhouDao {
    public static void insert(User user){
        DaoSession daoSession = CommonApp.getInstance().getDaoSession();
        daoSession.getUserDao().insertOrReplace(user);
    }
}
