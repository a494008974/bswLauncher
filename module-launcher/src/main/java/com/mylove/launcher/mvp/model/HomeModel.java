package com.mylove.launcher.mvp.model;

import android.content.Context;

import com.jess.arms.di.scope.ActivityScope;
import com.jess.arms.integration.IRepositoryManager;
import com.jess.arms.mvp.BaseModel;
import com.jess.arms.utils.DataHelper;
import com.mylove.launcher.app.utils.AppUtils;
import com.mylove.launcher.mvp.contract.HomeContract;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.inject.Inject;

import me.jessyan.armscomponent.commonservice.CommonApp;
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

    }

    @Override
    public List<String> fetchHomeShortCut(Context context) {
        String homeShortCut = DataHelper.getStringSF(context,"Home_Shortcut");
        if(homeShortCut == null){
            String shortCutDefault = fetchDefault(context);
            String[] shorts = shortCutDefault.split(":");
            if(shorts.length > 1){
                homeShortCut = shorts[1];
                DataHelper.setStringSF(context,"Home_Shortcut",homeShortCut);
            }else{
                DataHelper.setStringSF(context,"Home_Shortcut","");
            }
        }
        List<String> lists = new ArrayList<String>();
        if(homeShortCut != null){
            String[] homes = homeShortCut.split(";");
            for (String s:homes) {
                if(AppUtils.isAppInstalled(context,s)){
                    lists.add(s);
                }
            }
        }
        return lists;
    }

    public String fetchDefault(Context context){
        StringBuffer sb = new StringBuffer();
        try {
            InputStream inputStream = context.getAssets().open("default_shortcut");
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
            String data = null;
            while((data = bufferedReader.readLine())!=null)
            {
                sb.append(data);
            }
            bufferedReader.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return sb.toString();
    }

}
