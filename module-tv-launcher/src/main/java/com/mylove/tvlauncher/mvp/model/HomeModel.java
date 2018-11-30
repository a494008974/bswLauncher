package com.mylove.tvlauncher.mvp.model;

import android.content.Context;

import com.jess.arms.di.scope.ActivityScope;
import com.jess.arms.integration.IRepositoryManager;
import com.jess.arms.mvp.BaseModel;
import com.jess.arms.utils.DataHelper;
import com.mylove.tvlauncher.app.utils.AppUtils;
import com.mylove.tvlauncher.app.utils.DesHelper;
import com.mylove.tvlauncher.mvp.contract.HomeContract;
import com.mylove.tvlauncher.mvp.model.api.service.HomeApi;
import com.mylove.tvlauncher.mvp.model.entity.HomeResponse;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import io.reactivex.Observable;
import me.jessyan.armscomponent.commonservice.dao.DaoHelper;
import me.jessyan.armscomponent.commonservice.dao.InfoBean;


/**
 * Created by Administrator on 2018/8/30.
 */

@ActivityScope
public class HomeModel extends BaseModel implements HomeContract.Model {

    @Inject
    public HomeModel(IRepositoryManager repositoryManager) {
        super(repositoryManager);
    }

    public Observable<HomeResponse> fetchHomeData(){

        JSONObject jm = new JSONObject();
        try {
            jm.put("model", "VA_3128");
            jm.put("path", "DSMB1");
            jm.put("tem_index","happy_video_index");
            jm.put("serial", "0001");
        } catch (JSONException e) {
            // TODO Auto-generated catch block
        }
        String data = DesHelper.encrypt(jm.toString(), "win81688");

        return mRepositoryManager
                 .obtainRetrofitService(HomeApi.class)
                 .getDataList(data);
    }

    @Override
    public List<InfoBean> fetchInfoBeans() {
        return DaoHelper.fetchInfoBeans();
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
