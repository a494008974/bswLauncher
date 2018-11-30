package com.mylove.tvlauncher.mvp.ui;

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
import android.widget.TextView;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.jess.arms.base.BaseActivity;
import com.jess.arms.di.component.AppComponent;
import com.jess.arms.http.imageloader.ImageLoader;
import com.jess.arms.utils.ArmsUtils;
import com.mylove.tvlauncher.R;
import com.mylove.tvlauncher.R2;
import com.mylove.tvlauncher.app.utils.AppUtils;
import com.mylove.tvlauncher.app.utils.Contanst;
import com.mylove.tvlauncher.app.utils.SystemUtils;
import com.mylove.tvlauncher.app.utils.ToastUtils;

import com.mylove.tvlauncher.app.utils.down.DownloadUtil;
import com.mylove.tvlauncher.di.component.DaggerHomeComponent;
import com.mylove.tvlauncher.focus.FocusBorder;
import com.mylove.tvlauncher.mvp.contract.HomeContract;
import com.mylove.tvlauncher.mvp.presenter.HomePresenter;
import com.mylove.tvlauncher.mvp.ui.adapter.CommonRecyclerViewAdapter;
import com.mylove.tvlauncher.mvp.ui.adapter.CommonRecyclerViewHolder;
import com.mylove.tvlauncher.mvp.ui.fragment.MoreFragment;
import com.mylove.tvlauncher.mvp.ui.widget.GCircleProgress;
import com.owen.tvrecyclerview.widget.SimpleOnItemListener;
import com.owen.tvrecyclerview.widget.SpannableGridLayoutManager;
import com.owen.tvrecyclerview.widget.TvRecyclerView;

import org.simple.eventbus.EventBus;
import org.simple.eventbus.Subscriber;

import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import me.jessyan.armscomponent.commonsdk.core.RouterHub;
import me.jessyan.armscomponent.commonsdk.imgaEngine.config.CommonImageConfigImpl;
import me.jessyan.armscomponent.commonservice.dao.InfoBean;

@Route(path = RouterHub.TVLAUNCHER_HOMEACTIVITY)
public class HomeActivity extends BaseActivity<HomePresenter> implements HomeContract.View,View.OnKeyListener{

    @BindView(R2.id.tv_recycler_home)
    TvRecyclerView mTvRecyclerHome;
    @BindView(R2.id.tv_recycler_view)
    TvRecyclerView mTvRecyclerView;

    List<String> shortcuts = new ArrayList<String>();
    private CommonRecyclerViewAdapter mAdapter;

    List<InfoBean> infoBeans = new ArrayList<InfoBean>();
    private CommonRecyclerViewAdapter homeAdapter;

    private long mPressedTime;

    private MoreFragment moreFragment;
    private List<PackageInfo> packageInfos;

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
    private ImageLoader mImageLoader;

    private String oldpkg;
    @Override
    public void setupActivityComponent(@NonNull AppComponent appComponent) {
        mImageLoader = appComponent.imageLoader();
        DaggerHomeComponent
                .builder()
                .view(this)
                .appComponent(appComponent)
                .build()
                .inject(this);
    }

    @Override
    public int initView(@Nullable Bundle savedInstanceState) {
        return R.layout.tv_launcher_activity_main;
    }

    @Subscriber
    public void refreshData(List<PackageInfo> pis){
        if(moreFragment != null){
            moreFragment.refreshData(pis);
        }
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
        if(mPresenter == null) return;
        DownloadUtil.get().init(this);

        packageManager = this.getPackageManager();
        mHandler = new Handler();
        mPresenter.initConfig(this);
        infoBeans = mPresenter.fetchInfoBeans();
        initFocusBorder();
        initRecycler();
        initPackageInfo();
        showTime();
        mPresenter.fetchHomeData();
        mPresenter.fetchHomeShortCut(this);
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

    public void updateHome(){
        infoBeans = mPresenter.fetchInfoBeans();
        homeAdapter.clearDatas();
        homeAdapter.appendDatas(infoBeans);
        homeAdapter.notifyDataSetChanged();
    }

    private void initRecycler() {
        // home
        homeAdapter = new CommonRecyclerViewAdapter<InfoBean>(this) {
            @Override
            public int getItemLayoutId(int viewType) {
                return R.layout.tv_launcher_home_item;
            }

            @Override
            public void onBindItemHolder(CommonRecyclerViewHolder helper, InfoBean item, int position) {
                final View itemView = helper.itemView;

                if(!"".equals(item.getImg_url())){
                    ImageView imageView = (ImageView)helper.getHolder().getView(R.id.home_img);
                    mImageLoader.loadImage(itemView.getContext(),
                            CommonImageConfigImpl
                                    .builder()
                                    .url(String.format(Contanst.downmain,item.getImg_url()))
                                    .imageView(imageView)
                                    .build());
                }

                final SpannableGridLayoutManager.LayoutParams lp =
                        (SpannableGridLayoutManager.LayoutParams) itemView.getLayoutParams();
                int colSpan = 0;
                int rowSpan = 0;

                switch (position){
                    case 0:
                        colSpan = 5;
                        rowSpan = 8;
                        break;
                    case 7:
                        colSpan = 6;
                        rowSpan = 3;
                        break;
                    case 1:
                    case 4:
                    case 5:
                    case 6:
                        colSpan = 4;
                        rowSpan = 4;
                        break;
                    case 2:
                    case 3:
                        colSpan = 3;
                        rowSpan = 5;
                        break;
                }

                if (lp.rowSpan != rowSpan || lp.colSpan != colSpan) {
                    lp.rowSpan = rowSpan;
                    lp.colSpan = colSpan;
                    itemView.setLayoutParams(lp);
                }
            }
        };

        mTvRecyclerHome.setOnItemListener(new SimpleOnItemListener(){
            @Override
            public void onItemSelected(TvRecyclerView parent, View itemView, int position) {
                onMoveFocusBorder(itemView, 1.05f, 0);
            }

            @Override
            public void onItemClick(TvRecyclerView parent, View itemView, int position) {
                InfoBean infoBean = (InfoBean) homeAdapter.getItem(position);
                String pkg = infoBean.getPkg();
                if(!AppUtils.isAppInstalled(HomeActivity.this,pkg)){
                    // 下载app并安装
                    GCircleProgress progress = (GCircleProgress)itemView.findViewById(R.id.gcircle_progress);
                    mPresenter.download(HomeActivity.this,String.format(Contanst.downmain,infoBean.getUrl()),progress);
                    return;
                }
                Intent intent;
                if("0".equals(infoBean.getWay())){
                    SystemUtils.openApk(HomeActivity.this,pkg);
                }else if("1".equals(infoBean.getWay())){
                    try {
                        intent = Intent.parseUri(infoBean.getWay_val(), 0);
                        startActivity(intent);
                    } catch (URISyntaxException e) {
                        // TODO Auto-generated catch block
                    }
                }else if("2".equals(infoBean.getWay())){
                    try {
                        intent = Intent.parseUri(infoBean.getWay_val(), 0);
                        sendBroadcast(intent);
                    } catch (URISyntaxException e) {
                        // TODO Auto-generated catch block
                    }
                }
            }
        });

        mTvRecyclerHome.setOnItemKeyListener(new TvRecyclerView.OnItemKeyListener() {
            @Override
            public boolean onItemKey(View v, int keyCode, KeyEvent event,int position) {

                return false;
            }
        });

        homeAdapter.setDatas(infoBeans);
        mTvRecyclerHome.setSpacingWithMargins(2,1);
        mTvRecyclerHome.setAdapter(homeAdapter);

        // shortcuts
        mAdapter = new CommonRecyclerViewAdapter<String>(this) {
            @Override
            public int getItemLayoutId(int viewType) {
                return R.layout.tv_launcher_home_menu;
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

        mTvRecyclerView.setOnItemKeyListener(new TvRecyclerView.OnItemKeyListener() {
            @Override
            public boolean onItemKey(View v, int keyCode, KeyEvent event,int position) {
                if(event.getAction() == KeyEvent.ACTION_DOWN){
                    switch (keyCode){
                        case KeyEvent.KEYCODE_MENU:
                            oldpkg = (String) mAdapter.getItem(position);
                            if(!HomeActivity.this.getPackageName().equals(oldpkg)){
                                //showFragment(MoreFragment.CHECK_FRAGMENT);
                            }
                            break;
                    }
                }
                return false;
            }
        });

        mAdapter.setDatas(shortcuts);
        mTvRecyclerView.setSpacingWithMargins(5,5);
        mTvRecyclerView.setAdapter(mAdapter);
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
                            PackageInfo info = (PackageInfo)item;
                            mPresenter.replaceHomeShortCut(HomeActivity.this,info.applicationInfo.packageName,oldpkg);
                            mPresenter.fetchHomeShortCut(HomeActivity.this);
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

    public void   initPackageInfo(){
        new Thread(){
            @Override
            public void run() {
                super.run();
                packageInfos = SystemUtils.getAllApps(HomeActivity.this,3,true);
                EventBus.getDefault().post(packageInfos);
            }
        }.start();
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
