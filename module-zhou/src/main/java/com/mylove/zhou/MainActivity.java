package com.mylove.zhou;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.alibaba.android.arouter.facade.annotation.Route;

import me.jessyan.armscomponent.commonsdk.core.RouterHub;
import me.jessyan.armscomponent.commonservice.CommonApp;
import me.jessyan.armscomponent.commonservice.launcher.bean.Element;

@Route(path = RouterHub.ZHOU_MAINACTIVITY)
public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        System.out.println(RouterHub.ZHOU_MAINACTIVITY);
        setContentView(R.layout.zhou_activity_main);

        Element element = new Element();
        element.setTag("6666666");
        element.setPkg(this.getPackageName());
        CommonApp.getInstance().getDaoSession().getElementDao().insert(element);

//        ZhouApp.getInstance().getDaoSession().getElementDao().insertOrReplace(element);
    }
}
