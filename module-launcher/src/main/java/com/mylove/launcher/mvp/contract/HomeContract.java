package com.mylove.launcher.mvp.contract;

import android.app.Activity;
import android.content.Context;

import com.jess.arms.mvp.IModel;
import com.jess.arms.mvp.IView;

/**
 * Created by Administrator on 2018/8/30.
 */

public interface HomeContract {
    interface View extends IView {
        Activity getActivity();
    }
    //Model层定义接口,外部只需关心Model返回的数据,无需关心内部细节,如是否使用缓存
    interface Model extends IModel {
//        Observable<GankBaseResponse<List<GankItemBean>>> getGirlList(int num, int page);
        String fetchHomeData();
        void fetchDao(Context context);
    }
}
