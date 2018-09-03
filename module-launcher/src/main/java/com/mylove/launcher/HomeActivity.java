package com.mylove.launcher;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.os.Bundle;
import android.util.TypedValue;
import android.view.KeyEvent;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.jess.arms.base.BaseActivity;
import com.jess.arms.di.component.AppComponent;
import com.jess.arms.utils.ArmsUtils;
import com.mylove.launcher.app.utils.BeanUtils;
import com.mylove.launcher.di.component.DaggerHomeComponent;
import com.mylove.launcher.focus.FocusBorder;
import com.mylove.launcher.mvp.contract.HomeContract;
import com.mylove.launcher.mvp.presenter.HomePresenter;
import com.mylove.launcher.mvp.ui.adapter.CommonRecyclerViewAdapter;
import com.mylove.launcher.mvp.ui.adapter.CommonRecyclerViewHolder;
import com.owen.tvrecyclerview.widget.SimpleOnItemListener;
import com.owen.tvrecyclerview.widget.TvRecyclerView;

import java.util.List;

import butterknife.BindView;
import me.jessyan.armscomponent.commonsdk.core.RouterHub;
import me.jessyan.armscomponent.commonservice.launcher.bean.Element;

@Route(path = RouterHub.LAUNCHER_HOMEACTIVITY)
public class HomeActivity extends BaseActivity<HomePresenter> implements HomeContract.View,View.OnKeyListener{

    @BindView(R2.id.tv_recycler_view)
    TvRecyclerView mTvRecyclerView;

    List<Element> elements;
    private CommonRecyclerViewAdapter mAdapter;

    private long mPressedTime;

    @BindView(R2.id.home_main)
    RelativeLayout mHomeMain;

    protected FocusBorder mFocusBorder;

    @Override
    public void setupActivityComponent(@NonNull AppComponent appComponent) {
        DaggerHomeComponent
                .builder()
                .view(this)
                .appComponent(appComponent)
                .build()
                .inject(this);
    }

    @Override
    public int initView(@Nullable Bundle savedInstanceState) {
        return R.layout.launcher_activity_main;
    }

    private void initFocusBorder() {
        if(null == mFocusBorder) {
            mFocusBorder = new FocusBorder.Builder()
                    .asColor()
                    .borderColor(getResources().getColor(R.color.public_white))
                    .borderWidth(TypedValue.COMPLEX_UNIT_DIP, 3)
                    .shadowColor(getResources().getColor(R.color.public_white))
                    .animDuration(180L)
                    .build(this);
        }
    }

    @Override
    public void initData(@Nullable Bundle savedInstanceState) {
        initFocusBorder();
        initListener();

        mAdapter = new CommonRecyclerViewAdapter<Element>(this) {
            @Override
            public int getItemLayoutId(int viewType) {
                return R.layout.launcher_home_menu;
            }

            @Override
            public void onBindItemHolder(CommonRecyclerViewHolder helper, Element item, int position) {

            }
        };

        elements = BeanUtils.initSize(20);
        mAdapter.setDatas(elements);
        mTvRecyclerView.setSpacingWithMargins(5,5);
        mTvRecyclerView.setAdapter(mAdapter);

        if(mPresenter != null){
            mPresenter.fetchHomeData();
            mPresenter.fetchDao(this);
        }
    }

    protected void onMoveFocusBorder(View focusedView, float scale, float roundRadius) {
        if(null != mFocusBorder) {
            mFocusBorder.onFocus(focusedView, FocusBorder.OptionsFactory.get(scale, scale, roundRadius));
        }
    }

    private void initListener() {

        if(mHomeMain != null){
            for (int i = 0; i< mHomeMain.getChildCount(); i++){
                View view = mHomeMain.getChildAt(i);
                view.setTag("10"+i);
                view.setOnFocusChangeListener(new View.OnFocusChangeListener() {
                    @Override
                    public void onFocusChange(View v, boolean hasFocus) {
                        if (hasFocus){
                            onMoveFocusBorder(v, 1.05f, 0);
                        }
                    }
                });
                view.setOnKeyListener(this);
            }
        }

        mTvRecyclerView.setOnItemListener(new SimpleOnItemListener() {

            @Override
            public void onItemSelected(TvRecyclerView parent, View itemView, int position) {
                onMoveFocusBorder(itemView, 1.05f, 0);
            }

            @Override
            public void onItemClick(TvRecyclerView parent, View itemView, int position) {

            }
        });
    }

    @Override
    public void onBackPressed() {
        //获取第一次按键时间
        long mNowTime = System.currentTimeMillis();
        //比较两次按键时间差
        if ((mNowTime - mPressedTime) > 2000) {
            ArmsUtils.makeText(getApplicationContext(),
                    "再按一次退出" + ArmsUtils.getString(getApplicationContext(), R.string.public_app_name));
            mPressedTime = mNowTime;
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public Activity getActivity() {
        return this;
    }

    @Override
    public void showLoading() {

    }

    @Override
    public void hideLoading() {

    }

    @Override
    public void showMessage(@NonNull String message) {

    }

    @Override
    public void launchActivity(@NonNull Intent intent) {

    }

    @Override
    public void killMyself() {

    }

    @Override
    public boolean onKey(View v, int keyCode, KeyEvent event) {
        if (event.getAction() == KeyEvent.ACTION_DOWN){
            switch (keyCode){
                case KeyEvent.KEYCODE_DPAD_CENTER:
                case KeyEvent.KEYCODE_ENTER:
                    System.out.println("v.getTag = "+v.getTag());
                    Toast.makeText(HomeActivity.this,String.valueOf(v.getTag()),Toast.LENGTH_LONG).show();
                    break;
            }
        }
        return false;
    }

    //=======================广播====================
    public void register() {
        mNetWorkChangeReceiver = new NetWorkChangeReceiver();
        IntentFilter filterNECT = new IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION);
        filterNECT.addAction("android.net.wifi.STATE_CHANGE");
        filterNECT.addAction("android.net.ethernet.STATE_CHANGE");
        registerReceiver(mNetWorkChangeReceiver, filterNECT);

        mTimeReceiver = new TimeReceiver();
        IntentFilter filterTime = new IntentFilter(Intent.ACTION_TIME_TICK);
        registerReceiver(mTimeReceiver, filterTime);

        mAppReceiver = new AppReceiver();
        IntentFilter filterAPP = new IntentFilter(Intent.ACTION_PACKAGE_ADDED);
        filterAPP.addDataScheme("package");
        filterAPP.addAction(Intent.ACTION_PACKAGE_REMOVED);
        registerReceiver(mAppReceiver, filterAPP);

        mUsbAndSDCardBroadcastReceiver = new UsbAndSDCardBroadcastReceiver();
        IntentFilter mUsbAndSDCardFilter = new IntentFilter();
        mUsbAndSDCardFilter.addAction("android.intent.action.MEDIA_MOUNTED");
        mUsbAndSDCardFilter.addAction("android.intent.action.MEDIA_REMOVED");
        mUsbAndSDCardFilter.addAction("android.intent.action.MEDIA_BAD_REMOVAL");
        mUsbAndSDCardFilter.addDataScheme("file");
        registerReceiver(mUsbAndSDCardBroadcastReceiver, mUsbAndSDCardFilter);
    }

    public void unregister() {
        try {
            if (mNetWorkChangeReceiver != null) {
                unregisterReceiver(mNetWorkChangeReceiver);
            }

            if (mTimeReceiver != null) {
                unregisterReceiver(mTimeReceiver);
            }

            if (mAppReceiver != null) {
                unregisterReceiver(mAppReceiver);
            }

            if (mUsbAndSDCardBroadcastReceiver != null) {
                unregisterReceiver(mUsbAndSDCardBroadcastReceiver);
            }
        }catch(Exception e){

        }
    }

    private AppReceiver mAppReceiver;
    public class AppReceiver extends BroadcastReceiver {

        @Override
        public void onReceive(Context context, Intent intent) {
            // TODO Auto-generated method stub
            String packageName = intent.getDataString();
            packageName = packageName.split(":")[1];
            if (intent.getAction().equals("android.intent.action.PACKAGE_ADDED")) {

            }else if(intent.getAction().equals("android.intent.action.PACKAGE_REMOVED")){

            }
        }
    }

    String usbMountedPath = null;
    UsbAndSDCardBroadcastReceiver mUsbAndSDCardBroadcastReceiver;
    class UsbAndSDCardBroadcastReceiver extends BroadcastReceiver {
        @Override
        public void onReceive(Context context, Intent intent) {

            if ((intent.getAction().equals("android.intent.action.MEDIA_REMOVED"))
                    || (intent.getAction().equals("android.intent.action.MEDIA_BAD_REMOVAL"))) {

            }
            if (intent.getAction().equals("android.intent.action.MEDIA_MOUNTED")) {
                usbMountedPath = intent.getData().getPath();
            }
        }
    }

    private TimeReceiver mTimeReceiver;
    public class TimeReceiver extends BroadcastReceiver {
        @Override
        public void onReceive(Context context, Intent intent) {
            String action = intent.getAction();
            if (action.equals(Intent.ACTION_TIME_TICK)) {
//                timeTextView.setText(SystemUtils.getTime(MainActivity.this));
//                weekTextView.setText(SystemUtils.getWeek());
            }
        }
    }

    NetWorkChangeReceiver mNetWorkChangeReceiver;
    public class NetWorkChangeReceiver extends BroadcastReceiver {
        private ConnectivityManager connectivityManager;
        private NetworkInfo info;
        @Override
        public void onReceive(Context context, Intent intent) {
            String action = intent.getAction();
            if (action.equals(ConnectivityManager.CONNECTIVITY_ACTION)) {
                connectivityManager = (ConnectivityManager) HomeActivity.this.getSystemService(Context.CONNECTIVITY_SERVICE);
                NetworkInfo mNetworkInfo = connectivityManager.getActiveNetworkInfo();
                if(mNetworkInfo != null && mNetworkInfo.isAvailable()){
                    switch (mNetworkInfo.getType()) {
                        case  ConnectivityManager.TYPE_WIFI:
//                            netImageView.setImageResource(R.drawable.wifi_normal);
                            break;
                        case  ConnectivityManager.TYPE_ETHERNET:
//                            netImageView.setImageResource(R.drawable.net_normal);
                            break;
                    }
                }else{
//                    netImageView.setImageResource(R.drawable.net_no_normal);
                }
            }
        }
    }
}
