package com.mylove.tvlauncher.di.component;

import com.jess.arms.di.component.AppComponent;
import com.jess.arms.di.scope.ActivityScope;
import com.mylove.tvlauncher.mvp.ui.HomeActivity;
import com.mylove.tvlauncher.di.module.HomeModule;
import com.mylove.tvlauncher.mvp.contract.HomeContract;

import dagger.BindsInstance;
import dagger.Component;

/**
 * Created by Administrator on 2018/8/30.
 */

@ActivityScope
@Component(modules = HomeModule.class, dependencies = AppComponent.class)
public interface HomeComponent {
    void inject(HomeActivity activity);
    @Component.Builder
    interface Builder {
        @BindsInstance
        HomeComponent.Builder view(HomeContract.View view);
        HomeComponent.Builder appComponent(AppComponent appComponent);
        HomeComponent build();
    }
}