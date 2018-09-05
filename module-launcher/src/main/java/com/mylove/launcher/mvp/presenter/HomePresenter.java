package com.mylove.launcher.mvp.presenter;

import android.content.Context;

import com.jess.arms.mvp.BasePresenter;
import com.jess.arms.utils.DataHelper;
import com.mylove.launcher.app.utils.AppUtils;
import com.mylove.launcher.app.utils.SystemUtils;
import com.mylove.launcher.mvp.contract.HomeContract;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.inject.Inject;

import me.jessyan.armscomponent.commonservice.CommonApp;

/**
 * Created by Administrator on 2018/8/30.
 */

public class HomePresenter extends BasePresenter<HomeContract.Model,HomeContract.View> {

    @Inject
    public HomePresenter(HomeContract.Model model, HomeContract.View rootView) {
        super(model, rootView);
    }

    public void fetchHomeData(){
        if(mModel != null){
            String data = mModel.fetchHomeData();
        }
    }

    public void fetchDao(Context context){
        if(mModel != null){
            mModel.fetchDao(context);
        }
    }

    public void fetchHomeShortCut(Context context) {
        if(mModel != null){
            List<String> shortcuts =  mModel.fetchHomeShortCut(context);
            shortcuts.add(CommonApp.getInstance().getPackageName());
            if(mRootView != null){
                mRootView.showHomeShortCut(shortcuts);
            }
        }
    }

    public void addHomeShortCut(Context context,String pkg){
        String homeShortCut = DataHelper.getStringSF(context,"Home_Shortcut");
        if(!homeShortCut.contains(pkg+";")){
            homeShortCut = homeShortCut+pkg+";";
            DataHelper.setStringSF(context,"Home_Shortcut",homeShortCut);
        }
    }

    public void removeHomeShortCut(Context context,String pkg){
        String homeShortCut = DataHelper.getStringSF(context,"Home_Shortcut");
        homeShortCut = homeShortCut.replace(pkg+";","");
        DataHelper.setStringSF(context,"Home_Shortcut",homeShortCut);
    }
}
