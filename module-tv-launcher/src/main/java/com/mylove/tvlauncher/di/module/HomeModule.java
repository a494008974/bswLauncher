package com.mylove.tvlauncher.di.module;

import com.mylove.tvlauncher.mvp.contract.HomeContract;
import com.mylove.tvlauncher.mvp.model.HomeModel;

import dagger.Binds;
import dagger.Module;

/**
 * Created by Administrator on 2018/8/30.
 */

@Module
public abstract class HomeModule {
    @Binds
    abstract HomeContract.Model bindHomeModel(HomeModel model);
}
