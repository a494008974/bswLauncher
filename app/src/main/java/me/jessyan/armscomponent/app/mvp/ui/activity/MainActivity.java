/*
 * Copyright 2018 JessYan
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package me.jessyan.armscomponent.app.mvp.ui.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.TypedValue;
import android.view.View;

import com.alibaba.android.arouter.facade.annotation.Autowired;
import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.launcher.ARouter;
import com.jess.arms.base.BaseActivity;
import com.jess.arms.di.component.AppComponent;
import com.jess.arms.utils.ArmsUtils;
import com.owen.tvrecyclerview.widget.SimpleOnItemListener;
import com.owen.tvrecyclerview.widget.TvRecyclerView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import me.jessyan.armscomponent.app.R;
import me.jessyan.armscomponent.commonres.adapter.CommonRecyclerViewAdapter;
import me.jessyan.armscomponent.commonres.adapter.CommonRecyclerViewHolder;
import me.jessyan.armscomponent.commonres.focus.FocusBorder;
import me.jessyan.armscomponent.commonsdk.core.RouterHub;
import me.jessyan.armscomponent.commonsdk.utils.Utils;
import me.jessyan.armscomponent.commonservice.base.BaseInfo;
import me.jessyan.armscomponent.commonservice.gank.service.GankInfoService;
import me.jessyan.armscomponent.commonservice.hotel.service.HotelInfoService;
import me.jessyan.armscomponent.commonservice.launcher.service.LauncherInfoService;

/**
 * ================================================
 * 宿主 App 的主页
 *
 * @see <a href="https://github.com/JessYanCoding/ArmsComponent/wiki">ArmsComponent wiki 官方文档</a>
 * Created by JessYan on 19/04/2018 16:10
 * <a href="mailto:jess.yan.effort@gmail.com">Contact me</a>
 * <a href="https://github.com/JessYanCoding">Follow me</a>
 * ================================================
 */
@Route(path = RouterHub.APP_MAINACTIVITY)
public class MainActivity extends BaseActivity {

    @BindView(R.id.main_recycle_view)
    TvRecyclerView tvRecyclerView;


    @Autowired(name = RouterHub.GANK_SERVICE_GANKINFOSERVICE)
    GankInfoService mGankInfoService;

    @Autowired(name = RouterHub.LAUNCHER_SERVICE_LAUNCHERINFOSERVICE)
    LauncherInfoService mLauncherInfoService;

    @Autowired(name = RouterHub.HOTEL_SERVICE_HOTELINFOSERVICE)
    HotelInfoService mHotelInfoService;

    private long mPressedTime;

    protected FocusBorder mFocusBorder;
    private CommonRecyclerViewAdapter mAdapter;
    private List<BaseInfo> baseInfos;

    @Override
    public void setupActivityComponent(@NonNull AppComponent appComponent) {

    }

    @Override
    public int initView(@Nullable Bundle savedInstanceState) {
        return R.layout.activity_main;
    }

    private void initFocusBorder(Activity activity) {
        if(null == mFocusBorder) {
            mFocusBorder = new FocusBorder.Builder()
                    .asColor()
                    .borderColor(getResources().getColor(R.color.public_white))
                    .borderWidth(TypedValue.COMPLEX_UNIT_DIP, 3)
                    .shadowColor(getResources().getColor(R.color.public_black))
                    .shadowWidth(TypedValue.COMPLEX_UNIT_DIP, 20)
                    .animDuration(180L)
                    .build(activity);
        }
    }

    public void initInfo(){
        baseInfos = new ArrayList<BaseInfo>();
        if (mHotelInfoService != null) {
            baseInfos.add(mHotelInfoService.getInfo());
        }

        if (mLauncherInfoService != null) {
            baseInfos.add(mLauncherInfoService.getInfo());
        }

        if (mGankInfoService != null) {
            baseInfos.add(mGankInfoService.getInfo());
        }

    }

    @Override
    public void initData(@Nullable Bundle savedInstanceState) {
        ARouter.getInstance().inject(this);
        initFocusBorder(this);
        initInfo();

        tvRecyclerView.setSpacingWithMargins(10, 10);
        setListener();

        mAdapter = new CommonRecyclerViewAdapter<BaseInfo>(this) {
            @Override
            public int getItemLayoutId(int viewType) {
                return R.layout.activity_item;
            }

            @Override
            public void onBindItemHolder(CommonRecyclerViewHolder helper, BaseInfo item, int position) {
                helper.getHolder().setText(R.id.tv_view_name,item.getName());
            }
        };
        mAdapter.setDatas(baseInfos);
        tvRecyclerView.setAdapter(mAdapter);


    }

    private void setListener() {
        tvRecyclerView.setOnItemListener(new SimpleOnItemListener() {
            @Override
            public void onItemSelected(TvRecyclerView parent, View itemView, int position) {
                onMoveFocusBorder(itemView, 1.0f, 0);
            }

            @Override
            public void onItemClick(TvRecyclerView parent, View itemView, int position) {
                BaseInfo info = (BaseInfo)mAdapter.getItem(position);
                Utils.navigation(MainActivity.this, info.getUrl());
            }
        });
    }

    protected void onMoveFocusBorder(View focusedView, float scale, float roundRadius) {
        if(null != mFocusBorder) {
            mFocusBorder.onFocus(focusedView, FocusBorder.OptionsFactory.get(scale, scale, roundRadius));
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

}
