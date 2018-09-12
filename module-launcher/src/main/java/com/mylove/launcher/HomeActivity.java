package com.mylove.launcher;

import android.app.Activity;
import android.app.ActivityManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.graphics.Typeface;
import android.graphics.drawable.AnimationDrawable;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.os.Bundle;
import android.util.TypedValue;
import android.view.KeyEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.jess.arms.base.BaseActivity;
import com.jess.arms.di.component.AppComponent;
import com.mylove.launcher.app.utils.SystemUtils;
import com.mylove.launcher.app.utils.ToastUtils;
import com.mylove.launcher.di.component.DaggerHomeComponent;
import com.mylove.launcher.focus.FocusBorder;
import com.mylove.launcher.mvp.contract.HomeContract;
import com.mylove.launcher.mvp.presenter.HomePresenter;
import com.mylove.launcher.mvp.ui.adapter.CommonRecyclerViewAdapter;
import com.mylove.launcher.mvp.ui.adapter.CommonRecyclerViewHolder;
import com.mylove.launcher.mvp.ui.fragment.MoreFragment;
import com.owen.tvrecyclerview.widget.SimpleOnItemListener;
import com.owen.tvrecyclerview.widget.TvRecyclerView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import me.jessyan.armscomponent.commonsdk.core.RouterHub;

@Route(path = RouterHub.LAUNCHER_HOMEACTIVITY)
public class HomeActivity extends BaseActivity<HomePresenter> implements HomeContract.View,View.OnKeyListener{

    @BindView(R2.id.tv_recycler_view)
    TvRecyclerView mTvRecyclerView;

    List<String> shortcuts = new ArrayList<String>();
    private CommonRecyclerViewAdapter mAdapter;

    private long mPressedTime;

    private MoreFragment moreFragment;
    private List<PackageInfo> packageInfos;

    @BindView(R2.id.home_main)
    RelativeLayout mHomeMain;

    @BindView(R2.id.launcher_net)
    ImageView mLauncherNet;

    @BindView(R2.id.launcher_time)
    TextView mLauncherTime;
    @BindView(R2.id.launcher_statu)
    TextView mLauncherStatu;
    @BindView(R2.id.launcher_date)
    TextView mLauncherDate;
    @BindView(R2.id.launcher_week)
    TextView mLauncherWeek;
    @BindView(R2.id.launcher_focus_tv)
    TextView mLauncherFocusTv;

    private Handler mHandler;

    private ImageView anim;
    private boolean clearDone;
    private PackageManager packageManager;
    protected FocusBorder mFocusBorder;

    private AnimationDrawable animationDrawable;
    private boolean dismiss;
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

    public void initPackageInfo(){
        new Thread(){
            @Override
            public void run() {
                super.run();
                packageInfos = SystemUtils.getAllApps(HomeActivity.this,3,true);
            }
        }.start();
    }

    private void initFocusBorder() {
        if(null == mFocusBorder) {
            mFocusBorder = new FocusBorder.Builder()
                    .asColor()
                    .borderColor(getResources().getColor(R.color.public_white))
                    .borderWidth(TypedValue.COMPLEX_UNIT_DIP, 3)
//                    .shadowColor(getResources().getColor(R.color.public_white))
                    .animDuration(180L)
                    .build(this);
        }
    }

    public void showTime(){
        Typeface typeface = Typeface.createFromAsset(getAssets(),"fonts/Roboto-Thin.ttf");
        mLauncherTime.setTypeface(typeface);
        mLauncherStatu.setTypeface(typeface);
        mLauncherDate.setTypeface(typeface);
        mLauncherWeek.setTypeface(typeface);

        mLauncherTime.setText(SystemUtils.getTime(HomeActivity.this));
        mLauncherStatu.setText(SystemUtils.getStatu());
        mLauncherDate.setText(SystemUtils.getDate());
        mLauncherWeek.setText(SystemUtils.getWeek());
    }

    @Override
    public void initData(@Nullable Bundle savedInstanceState) {
        packageManager = this.getPackageManager();
        mHandler = new Handler();

        initFocusBorder();
        initListener();
        initPackageInfo();

        showTime();

        mAdapter = new CommonRecyclerViewAdapter<String>(this) {
            @Override
            public int getItemLayoutId(int viewType) {
                return R.layout.launcher_home_menu;
            }

            @Override
            public void onBindItemHolder(CommonRecyclerViewHolder helper, String item, int position) {
                if (position == 0){
                    helper.itemView.setId(R.id.launcher_shortcut_start);
                    helper.itemView.setNextFocusLeftId(R.id.launcher_shortcut_start);
                }else if(position == mAdapter.getItemCount() - 1){
                    helper.itemView.setId(R.id.launcher_shortcut_end);
                    helper.itemView.setNextFocusRightId(R.id.launcher_shortcut_end);
                }
                helper.getHolder().setImageResource(R.id.shortcut_icon,R.drawable.launcher_default_icon);
                if(HomeActivity.this.getPackageName().equals(item))return;
                PackageInfo pkgInfo = null;
                try {
                    pkgInfo = packageManager.getPackageInfo(item, PackageManager.GET_PERMISSIONS);
                } catch (PackageManager.NameNotFoundException e) {
                }
                if(pkgInfo == null)return;

                helper.getHolder().setImageDrawable(R.id.shortcut_icon,packageManager.getApplicationIcon(pkgInfo.applicationInfo));

            }
        };

        mAdapter.setDatas(shortcuts);
        mTvRecyclerView.setSpacingWithMargins(5,5);
        mTvRecyclerView.setAdapter(mAdapter);

        if(mPresenter != null){
            mPresenter.fetchHomeData();
            mPresenter.fetchHomeShortCut(this);
//            mPresenter.fetchDao(this);
        }
        register();
    }



    @Override
    protected void onDestroy() {
        super.onDestroy();
        unregister();
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
                view.setTag("10"+(i+1));
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
                String s = (String) mAdapter.getItem(position);
                if(HomeActivity.this.getPackageName().equals(s)){
                    s = getString(R.string.launcher_paste);
                }else{
                    try {
                        PackageInfo pkgInfo = packageManager.getPackageInfo(s, PackageManager.GET_PERMISSIONS);
                        s = (String) packageManager.getApplicationLabel(pkgInfo.applicationInfo);
                    } catch (PackageManager.NameNotFoundException e) {
                    }
                }
                mLauncherFocusTv.setText(s);
                Animation tvAnim = AnimationUtils.loadAnimation(HomeActivity.this, R.anim.launcher_tv_translate_1);
                mLauncherFocusTv.startAnimation(tvAnim);
            }

            @Override
            public void onItemClick(TvRecyclerView parent, View itemView, int position) {
                String pkg = (String) mAdapter.getItem(position);
                if(HomeActivity.this.getPackageName().equals(pkg)){
                    showFragment(MoreFragment.SELECT_FRAGMENT);
                }else{
                    SystemUtils.openApk(HomeActivity.this,pkg);
                }
            }
        });

        mTvRecyclerView.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if(!hasFocus){
                    Animation tvAnim = AnimationUtils.loadAnimation(HomeActivity.this, R.anim.launcher_tv_translate_2);
                    mLauncherFocusTv.startAnimation(tvAnim);
                }
            }
        });
    }

    public void showFragment(String type){
        moreFragment = MoreFragment.newInstance();
        if(moreFragment != null){
            if (!moreFragment.isAdded()){
                if (packageInfos == null) {
                    packageInfos = SystemUtils.getAllApps(HomeActivity.this,3,true);
                }
                moreFragment.setType(type);
                if(MoreFragment.SELECT_FRAGMENT.equals(type)){
                    moreFragment.setSelectListener(new MoreFragment.SelectListener() {
                        @Override
                        public void onItemClick(TvRecyclerView parent, View itemView, int position, Object item) {
                            PackageInfo info = (PackageInfo)item;
                            View view = itemView.findViewById(R.id.launcher_item_check);
                            if(view.getVisibility() == View.VISIBLE){
                                view.setVisibility(View.GONE);
                                mPresenter.removeHomeShortCut(HomeActivity.this,info.applicationInfo.packageName);
                            }else{
                                view.setVisibility(View.VISIBLE);
                                mPresenter.addHomeShortCut(HomeActivity.this,info.applicationInfo.packageName);
                            }
                        }

                        @Override
                        public void onDismiss() {
                            dismiss = true;
                            mPresenter.fetchHomeShortCut(HomeActivity.this);
                        }
                    });
                }

                if(MoreFragment.CHECK_FRAGMENT.equals(type)){
                    moreFragment.setCheckListener(new MoreFragment.CheckListener() {
                        @Override
                        public void onItemClick(TvRecyclerView parent, View itemView, int position, Object item) {

                        }
                    });
                }
                moreFragment.setPackageInfos(packageInfos);
                moreFragment.show(getSupportFragmentManager(),type);
            }
        }
    }

    @Override
    public void onBackPressed() {
        /*
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
        */
    }

    @Override
    public Activity getActivity() {
        return this;
    }

    @Override
    public void showHomeShortCut(List<String> shortcuts) {
        if (shortcuts == null) return;
        final int select = shortcuts.size() - 1;
        mAdapter.setDatas(shortcuts);
        mTvRecyclerView.setAdapter(mAdapter);

        if(mTvRecyclerView != null){
            mHandler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    if(dismiss){
                        mTvRecyclerView.setSelection(select);
                        dismiss = false;
                    }
                }
            },10);
        }
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
                    String tag = String.valueOf(v.getTag());
                    switch (tag){
                        case "101":
                            SystemUtils.openApk(this,"org.xbmc.kodi");
                            break;
                        case "102":
                            SystemUtils.openApk(this,"com.android.vending");
                            break;
                        case "103":
                            SystemUtils.openApk(this,"com.google.android.youtube.tv");
                            break;
                        case "104":
                            SystemUtils.openApk(this,"com.droidlogic.FileBrower");
                            break;
                        case "105":
                            SystemUtils.openApk(this,"com.android.chrome");
                            break;
                        case "106":
                            //settings
                            try{
                                Intent intent = new Intent();
                                intent.setClassName("com.android.tv.settings","com.mylove.tv.settings.MainActivity");
                                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                                HomeActivity.this.startActivity(intent);
                            }catch (Exception e){

                            }
                            break;
                        case "107":
                            //memory recycle
                            View clear = ((ViewGroup)v).getChildAt(0);
                            if(clear != null && clear instanceof ImageView){
                                anim = (ImageView) clear;
                                animationDrawable = (AnimationDrawable)anim.getBackground();
                                animationDrawable.start();
                                mHandler.postDelayed(new Runnable() {
                                    @Override
                                    public void run() {
                                        if(!clearDone){
                                            clearDone = true;
                                            new ClearAsyn().execute();
                                        }
                                    }
                                },1600);
                            }
                            break;
                        case "108":
                            showFragment(MoreFragment.MORE_FRAGMENT);
                            break;
                    }
                    break;
            }
        }
        return false;
    }

    private class ClearAsyn extends AsyncTask<Void,Void,List<ActivityManager.RunningAppProcessInfo>>{

        @Override
        protected List<ActivityManager.RunningAppProcessInfo> doInBackground(Void... voids) {
            List<ActivityManager.RunningAppProcessInfo> mem = SystemUtils.getRunningApp(HomeActivity.this);
            return mem;
        }

        @Override
        protected void onPostExecute(final List<ActivityManager.RunningAppProcessInfo> recycle) {
            super.onPostExecute(recycle);
            if(animationDrawable != null){
                animationDrawable.stop();
            }
            int sum = SystemUtils.clearMemory(HomeActivity.this,recycle);
            String s = String.format(getString(R.string.launcher_mem_clear),sum);
            ToastUtils.showCenter(HomeActivity.this,s);
            clearDone = false;

            new Thread(){
                @Override
                public void run() {
                    super.run();
                    SystemUtils.forceApp(recycle);
                }
            }.start();

        }
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
            initPackageInfo();
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
                showTime();
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
                            mLauncherNet.setImageResource(R.drawable.launcher_iv_wifi);
                            break;
                        case  ConnectivityManager.TYPE_ETHERNET:
                            mLauncherNet.setImageResource(R.drawable.launcher_iv_net);
                            break;
                    }
                }else{
                    mLauncherNet.setImageBitmap(null);
                }
            }
        }
    }
}
