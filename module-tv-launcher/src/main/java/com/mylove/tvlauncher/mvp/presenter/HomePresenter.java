package com.mylove.tvlauncher.mvp.presenter;

import android.content.Context;
import android.util.Log;
import android.view.View;

import com.jess.arms.mvp.BasePresenter;
import com.jess.arms.utils.DataHelper;
import com.mylove.tvlauncher.R;
import com.mylove.tvlauncher.app.utils.Contanst;
import com.mylove.tvlauncher.app.utils.down.DownloadCallback;
import com.mylove.tvlauncher.app.utils.down.DownloadRecord;
import com.mylove.tvlauncher.app.utils.down.DownloadRequest;
import com.mylove.tvlauncher.app.utils.down.DownloadUtil;
import com.mylove.tvlauncher.mvp.contract.HomeContract;
import com.mylove.tvlauncher.mvp.model.entity.HomeResponse;
import com.mylove.tvlauncher.mvp.ui.widget.GCircleProgress;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import io.reactivex.internal.observers.BlockingBaseObserver;
import io.reactivex.schedulers.Schedulers;
import me.jessyan.armscomponent.commonservice.CommonApp;
import me.jessyan.armscomponent.commonservice.dao.ClsBean;
import me.jessyan.armscomponent.commonservice.dao.DaoHelper;
import me.jessyan.armscomponent.commonservice.dao.InfoBean;

/**
 * Created by Administrator on 2018/8/30.
 */

public class HomePresenter extends BasePresenter<HomeContract.Model,HomeContract.View> {

    @Inject
    public HomePresenter(HomeContract.Model model, HomeContract.View rootView) {
        super(model, rootView);
    }

    public void initConfig(Context context){
        int init = DataHelper.getIntergerSF(context,"init");
        if(init == -1){
            String[] tags = context.getResources().getStringArray(R.array.tv_launcher_tags);
            String[] pkgs = context.getResources().getStringArray(R.array.tv_launcher_pkgs);
            List<InfoBean> infoBeans = new ArrayList<InfoBean>();
            for (int i=0; i<tags.length; i++){
                InfoBean infoBean = new InfoBean();
                infoBean.setTag(tags[i]);
                if (i<pkgs.length){
                    infoBean.setPkg(pkgs[i]);
                }else{
                    infoBean.setPkg("");
                }
                infoBeans.add(infoBean);
            }
            DaoHelper.saveInfo(infoBeans);
            DataHelper.setIntergerSF(context,"init",1);
        }
    }

    public List<InfoBean> fetchInfoBeans(){
        if (mModel != null){
            return mModel.fetchInfoBeans();
        }
        return null;
    }

    public void download(Context context,String url, final GCircleProgress progress){
        DownloadRequest request = DownloadRequest.newBuilder()
                .downloadUrl(url)
                .downloadDir(context.getFilesDir().getPath())
                .downloadName(DownloadUtil.getMD5(url))
                .downloadListener(new DownloadCallback(){
                    @Override
                    public void onProgress(DownloadRecord record) {
                        super.onProgress(record);
                        Log.e("ZZZZ","Progress => "+record.getProgress());
                        progress.setProgress(record.getProgress());
                    }

                    @Override
                    public void onFailed(DownloadRecord record, String errMsg) {
                        super.onFailed(record, errMsg);
                    }

                    @Override
                    public void onFinish(DownloadRecord record) {
                        super.onFinish(record);

                    }
                })
                .build();
        DownloadUtil.get().registerListener(context);
        String requestId = DownloadUtil.get().enqueue(request);
    }

    public void fetchHomeData(){
        if(mModel != null){
            mModel.fetchHomeData().subscribeOn(Schedulers.io())
                    .subscribe(new BlockingBaseObserver<HomeResponse>() {
                        @Override
                        public void onNext(HomeResponse homeResponse) {
                            try{
                                List<ClsBean> clsBeans = homeResponse.getInfo().getData().getCls();
                                DaoHelper.saveCls(clsBeans);
                                List<InfoBean> infoBeans = homeResponse.getInfo().getData().getInfo();
                                DaoHelper.saveInfo(infoBeans);
                                if(mRootView != null){
                                    mRootView.updateHome();
                                }
                            }catch (Exception e){

                            }
                        }

                        @Override
                        public void onError(Throwable e) {

                        }
                    });
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

    public void replaceHomeShortCut(Context context,String pkg,String old){
        String homeShortCut = DataHelper.getStringSF(context,"Home_Shortcut");
        if(homeShortCut.contains(old+";")){
            homeShortCut = homeShortCut.replaceFirst(old+";",pkg+";");
            DataHelper.setStringSF(context,"Home_Shortcut",homeShortCut);
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
