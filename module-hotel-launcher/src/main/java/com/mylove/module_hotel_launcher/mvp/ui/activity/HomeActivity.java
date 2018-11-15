package com.mylove.module_hotel_launcher.mvp.ui.activity;

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
import com.jess.arms.utils.ArmsUtils;

import com.mylove.module_hotel_launcher.R2;
import com.mylove.module_hotel_launcher.app.utils.EncodingHandler;
import com.mylove.module_hotel_launcher.di.component.DaggerHomeComponent;
import com.mylove.module_hotel_launcher.di.module.HomeModule;
import com.mylove.module_hotel_launcher.mvp.contract.HomeContract;
import com.mylove.module_hotel_launcher.mvp.presenter.HomePresenter;

import com.mylove.module_hotel_launcher.R;


import org.simple.eventbus.Subscriber;

import butterknife.BindView;
import me.jessyan.armscomponent.commonres.service.CoreService;
import me.jessyan.armscomponent.commonsdk.core.RouterHub;
import me.jessyan.armscomponent.commonservice.dao.DaoHelper;
import me.jessyan.armscomponent.commonservice.dao.HotelEntity;

import static com.jess.arms.utils.Preconditions.checkNotNull;

@Route(path = RouterHub.HOTEL_HOMEACTIVITY)
public class HomeActivity extends BaseActivity<HomePresenter> implements HomeContract.View {

    @BindView(R2.id.qrcode)
    ImageView qrcode;
    @BindView(R2.id.web_url)
    TextView webUrl;

    @Override
    public void setupActivityComponent(@NonNull AppComponent appComponent) {
        DaggerHomeComponent //如找不到该类,请编译一下项目
                .builder()
                .appComponent(appComponent)
                .homeModule(new HomeModule(this))
                .build()
                .inject(this);
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
    public int initView(@Nullable Bundle savedInstanceState) {
        return R.layout.activity_home; //如果你不需要框架帮你设置 setContentView(id) 需要自行设置,请返回 0
    }

    @Override
    public void initData(@Nullable Bundle savedInstanceState) {
        initConfig();
        Intent intent = new Intent();
        intent.setClass(this,CoreService.class);
        startService(intent);
    }

    @Override
    public void showLoading() {

    }

    @Override
    public void hideLoading() {

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
            Bitmap bitmap = EncodingHandler.createQRCode(mRootUrl,350);
            qrcode.setImageBitmap(bitmap);
        } catch (WriterException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void showMessage(@NonNull String message) {
        checkNotNull(message);
        ArmsUtils.snackbarText(message);
    }

    @Override
    public void launchActivity(@NonNull Intent intent) {
        checkNotNull(intent);
        ArmsUtils.startActivity(intent);
    }

    @Override
    public void killMyself() {
        finish();
    }
}
