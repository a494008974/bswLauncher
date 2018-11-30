package com.mylove.tvlauncher.mvp.contract;

import android.app.Activity;
import android.content.Context;

import com.jess.arms.mvp.IModel;
import com.jess.arms.mvp.IView;
import com.mylove.tvlauncher.mvp.model.entity.HomeResponse;

import java.util.List;

import io.reactivex.Observable;
import me.jessyan.armscomponent.commonservice.dao.InfoBean;

/**
 * Created by Administrator on 2018/8/30.
 */

public interface HomeContract {
    interface View extends IView {
        Activity getActivity();
        void showHomeShortCut(List<String> shortcuts);
        void updateHome();
    }
    //Model层定义接口,外部只需关心Model返回的数据,无需关心内部细节,如是否使用缓存
    interface Model extends IModel {
        Observable<HomeResponse> fetchHomeData();
        List<InfoBean> fetchInfoBeans();
        List<String> fetchHomeShortCut(Context context);
    }
}
