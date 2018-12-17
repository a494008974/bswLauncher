package com.mylove.module_hotel_launcher.mvp.ui.activity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.widget.ImageView;

import com.jess.arms.base.BaseActivity;
import com.jess.arms.di.component.AppComponent;
import com.jess.arms.utils.ArmsUtils;

import com.mylove.module_hotel_launcher.di.component.DaggerSlideComponent;
import com.mylove.module_hotel_launcher.di.module.SlideModule;
import com.mylove.module_hotel_launcher.mvp.contract.SlideContract;
import com.mylove.module_hotel_launcher.mvp.presenter.SlidePresenter;

import com.mylove.module_hotel_launcher.R;
import com.mylove.slideanimation.anim.SlideView;


import butterknife.BindView;

import static com.jess.arms.utils.Preconditions.checkNotNull;


public class SlideActivity extends BaseActivity<SlidePresenter> implements SlideContract.View {

    @BindView(R.id.slide_view)
    SlideView slideView;

    @Override
    public void setupActivityComponent(@NonNull AppComponent appComponent) {
        DaggerSlideComponent //如找不到该类,请编译一下项目
                .builder()
                .appComponent(appComponent)
                .slideModule(new SlideModule(this))
                .build()
                .inject(this);
    }

    @Override
    public int initView(@Nullable Bundle savedInstanceState) {
        return R.layout.activity_slide; //如果你不需要框架帮你设置 setContentView(id) 需要自行设置,请返回 0
    }

    @Override
    public void initData(@Nullable Bundle savedInstanceState) {
        ImageView imageView = new ImageView(this);
        imageView.setBackgroundColor(Color.RED);
        slideView.showView(imageView);
        slideView.start();
    }

    @Override
    public void showLoading() {

    }

    @Override
    public void hideLoading() {

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
