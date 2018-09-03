package me.jessyan.armscomponent.commonservice.dao;

import me.jessyan.armscomponent.commonservice.CommonApp;
import me.jessyan.armscomponent.commonservice.launcher.bean.Element;

/**
 * Created by Administrator on 2018/9/3.
 */

public class ElementDao {
    public static void insert(Element element){
        CommonApp.getInstance().getDaoSession().getElementDao().insertOrReplace(element);
    }
}
