package com.mylove.launcher.mvp.model;

import android.content.Context;

import com.jess.arms.di.scope.ActivityScope;
import com.jess.arms.integration.IRepositoryManager;
import com.jess.arms.mvp.BaseModel;
import com.mylove.launcher.mvp.contract.HomeContract;

import javax.inject.Inject;

import me.jessyan.armscomponent.commonservice.CommonApp;
import me.jessyan.armscomponent.commonservice.dao.ElementDao;
import me.jessyan.armscomponent.commonservice.launcher.bean.Element;


/**
 * Created by Administrator on 2018/8/30.
 */

@ActivityScope
public class HomeModel extends BaseModel implements HomeContract.Model {

    @Inject
    public HomeModel(IRepositoryManager repositoryManager) {
        super(repositoryManager);
    }

//    @Override
//    public Observable<GankBaseResponse<List<GankItemBean>>> getGirlList(int num, int page) {
//        return mRepositoryManager
//                .obtainRetrofitService(GankService.class)
//                .getGirlList(num, page);
//    }
    public String fetchHomeData(){
        System.out.println("HomeModel fetchHomeData ---");
        return "fetchHomeData from HomeModel ===";
    }

    @Override
    public void fetchDao(Context context) {
        Element element = new Element();
        element.setTag("33304444");
        element.setPkg(context.getPackageName());
        CommonApp.getInstance().getDaoSession().getElementDao().insertOrReplace(element);
    }
}
