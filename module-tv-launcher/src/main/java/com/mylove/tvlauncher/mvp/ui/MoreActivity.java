package com.mylove.tvlauncher.mvp.ui;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.util.TypedValue;
import android.view.View;
import android.view.ViewGroup;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.mylove.tvlauncher.R;
import com.mylove.tvlauncher.app.utils.AppUtils;
import com.mylove.tvlauncher.app.utils.SystemUtils;
import com.mylove.tvlauncher.focus.FocusBorder;
import com.mylove.tvlauncher.mvp.ui.adapter.CommonRecyclerViewAdapter;
import com.mylove.tvlauncher.mvp.ui.adapter.CommonRecyclerViewHolder;
import com.mylove.tvlauncher.mvp.ui.fragment.MoreFragment;
import com.owen.tvrecyclerview.widget.SimpleOnItemListener;
import com.owen.tvrecyclerview.widget.TvRecyclerView;

import java.util.ArrayList;
import java.util.List;

import me.jessyan.armscomponent.commonsdk.core.RouterHub;

@Route(path = RouterHub.TVLAUNCHER_MOREACTIVITY)
public class MoreActivity extends AppCompatActivity {

    private List<PackageInfo> packageInfos = new ArrayList<PackageInfo>();

    TvRecyclerView tvRecyclerView;

    protected FocusBorder mFocusBorder;
    private CommonRecyclerViewAdapter mAdapter;
    private PackageManager packageManager;

    public static final int DATAS = 0x111;
    private Handler mHandler = new Handler(){

        @Override
        public void handleMessage(Message msg) {
            // TODO Auto-generated method stub
            super.handleMessage(msg);
            switch (msg.what) {
                case DATAS:
                    if(mAdapter != null && tvRecyclerView != null){
                        mAdapter.setDatas(packageInfos);
                        tvRecyclerView.setAdapter(mAdapter);
                        if(mAdapter.getItemCount() > 0){
                            tvRecyclerView.setSelection(0);
                        }
                    }
                    break;
            }
        }

    };
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.tv_launcher_more_fragment);
        packageManager = getPackageManager();
        tvRecyclerView = (TvRecyclerView)findViewById(R.id.more_recycle_view);
        tvRecyclerView.setSpacingWithMargins(10, 10);
        mAdapter = new CommonRecyclerViewAdapter<PackageInfo>(this) {
            @Override
            public int getItemLayoutId(int viewType) {
                return R.layout.tv_launcher_more_item;
            }

            @Override
            public void onBindItemHolder(CommonRecyclerViewHolder helper, PackageInfo item, int position) {
                helper.getHolder().setImageDrawable(R.id.launcher_item_icon,packageManager.getApplicationIcon(item.applicationInfo));
                String name = (String) packageManager.getApplicationLabel(item.applicationInfo);
                helper.getHolder().setText(R.id.launcher_item_name,name);

            }
        };
        mAdapter.setDatas(packageInfos);
        initFocusBorder(this);
        setListener();
        new Thread(){
            public void run() {
                packageInfos = SystemUtils.getAllApps(MoreActivity.this,3,false);
                mHandler.sendEmptyMessage(DATAS);
            };
        }.start();
        register();
    }

    private void setListener() {
        tvRecyclerView.setOnItemListener(new SimpleOnItemListener() {
            @Override
            public void onItemSelected(TvRecyclerView parent, View itemView, int position) {
                onMoveFocusBorder(itemView, 1.05f, 10);
            }

            @Override
            public void onItemClick(TvRecyclerView parent, View itemView, int position) {
                PackageInfo info = (PackageInfo)mAdapter.getItem(position);
                SystemUtils.openApk(MoreActivity.this,info.applicationInfo.packageName);
            }
        });

        tvRecyclerView.setOnLongClickListener(new TvRecyclerView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(TvRecyclerView parent, View itemView, int position) {
                PackageInfo info = (PackageInfo)mAdapter.getItem(position);
                AppUtils.uninstall(MoreActivity.this,info.applicationInfo.packageName);
                return true;
            }
        });
    }

    protected void onMoveFocusBorder(View focusedView, float scale, float roundRadius) {
        if(null != mFocusBorder) {
            mFocusBorder.onFocus(focusedView, FocusBorder.OptionsFactory.get(scale, scale, roundRadius));
        }
    }

    private void initFocusBorder(Activity context) {
        if(null == mFocusBorder) {
            mFocusBorder = new FocusBorder.Builder()
                    .asColor()
                    .borderColor(getResources().getColor(R.color.public_white))
                    .borderWidth(TypedValue.COMPLEX_UNIT_DIP, 3)
                    .shadowColor(getResources().getColor(R.color.public_white))
                    .animDuration(180L)
                    .build(context);
            mFocusBorder.setVisible(true);
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unregister();
    }

    //=======================广播====================
    public void register() {
        mAppReceiver = new AppReceiver();
        IntentFilter filterAPP = new IntentFilter(Intent.ACTION_PACKAGE_ADDED);
        filterAPP.addDataScheme("package");
        filterAPP.addAction(Intent.ACTION_PACKAGE_REMOVED);
        registerReceiver(mAppReceiver, filterAPP);
    }

    public void unregister() {
        if (mAppReceiver != null) {
            unregisterReceiver(mAppReceiver);
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
            if(packageInfos != null){
                packageInfos.clear();
            }

            packageInfos = SystemUtils.getAllApps(MoreActivity.this,3,false);
            mHandler.sendEmptyMessage(DATAS);
        }
    }
}
