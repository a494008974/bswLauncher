package com.mylove.module_hotel_launcher;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.widget.ImageView;
import android.widget.TextView;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.google.zxing.WriterException;
import com.jess.arms.base.BaseActivity;
import com.jess.arms.di.component.AppComponent;
import com.mylove.module_hotel_launcher.app.utils.EncodingHandler;

import org.simple.eventbus.Subscriber;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import me.jessyan.armscomponent.commonres.service.CoreService;
import me.jessyan.armscomponent.commonsdk.core.RouterHub;
import me.jessyan.armscomponent.commonservice.dao.DaoHelper;
import me.jessyan.armscomponent.commonservice.dao.HotelEntity;

/**
 * Skeleton of an Android Things activity.
 * */
@Route(path = RouterHub.HOTEL_HOMEACTIVITY)
public class MainActivity extends BaseActivity {
    private static final String TAG = "MainActivity";

    @BindView(R2.id.qrcode)
    ImageView qrcode;
    @BindView(R2.id.web_url)
    TextView webUrl;

    @Override
    public void setupActivityComponent(@NonNull AppComponent appComponent) {

    }

    @Override
    public int initView(@Nullable Bundle savedInstanceState) {
        return R.layout.hotel_activity_main;
    }

    @Override
    public void initData(@Nullable Bundle savedInstanceState) {
        initConfig();
        Intent intent = new Intent();
        intent.setClass(this,CoreService.class);
        startService(intent);
    }

    private void initConfig() {
        for (int i=1; i<20; i++){
            HotelEntity hotelEntity = new HotelEntity();
            if(i/10 < 1){
                hotelEntity.setId("10"+i);
            }else{
                hotelEntity.setId("1"+i);
            }
            hotelEntity.setUrl("");
            hotelEntity.setPath("");
            hotelEntity.setPkg("");
            hotelEntity.setAction("");
            hotelEntity.setIsAct("");
            hotelEntity.setIsNet("");
            hotelEntity.setCreateTime("");
            DaoHelper.insert(hotelEntity);
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Intent intent = new Intent();
        intent.setClass(this,CoreService.class);
        stopService(intent);
    }

    @Subscriber(tag = "serverStart")
    private void serverStart(String ip) {
        try {
            String mRootUrl = String.format("http://%s:%d/",ip,CoreService.PORT);
            webUrl.setText(mRootUrl);
            Bitmap bitmap = EncodingHandler.createQRCode(mRootUrl,300);
            qrcode.setImageBitmap(bitmap);
        } catch (WriterException e) {
            e.printStackTrace();
        }
    }
}
