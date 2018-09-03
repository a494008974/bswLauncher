package com.mylove.launcher.mvp.presenter;

import android.content.Context;

import com.jess.arms.mvp.BasePresenter;
import com.mylove.launcher.mvp.contract.HomeContract;

import javax.inject.Inject;

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
}
