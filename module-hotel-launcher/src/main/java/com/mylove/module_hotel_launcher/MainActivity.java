package com.mylove.module_hotel_launcher;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.jess.arms.base.BaseActivity;
import com.jess.arms.di.component.AppComponent;

import me.jessyan.armscomponent.commonres.service.CoreService;
import me.jessyan.armscomponent.commonsdk.core.RouterHub;

/**
 * Skeleton of an Android Things activity.
 * */
@Route(path = RouterHub.HOTEL_HOMEACTIVITY)
public class MainActivity extends BaseActivity {

    @Override
    public void setupActivityComponent(@NonNull AppComponent appComponent) {

    }

    @Override
    public int initView(@Nullable Bundle savedInstanceState) {
        return R.layout.hotel_activity_main;
    }

    @Override
    public void initData(@Nullable Bundle savedInstanceState) {
        Intent intent = new Intent();
        intent.setClass(this,CoreService.class);
        startService(intent);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Intent intent = new Intent();
        intent.setClass(this,CoreService.class);
        stopService(intent);
    }
}
