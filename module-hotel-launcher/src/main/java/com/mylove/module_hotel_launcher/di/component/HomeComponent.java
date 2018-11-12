package com.mylove.module_hotel_launcher.di.component;

import dagger.Component;

import com.jess.arms.di.component.AppComponent;

import com.mylove.module_hotel_launcher.di.module.HomeModule;

import com.jess.arms.di.scope.ActivityScope;
import com.mylove.module_hotel_launcher.mvp.ui.activity.HomeActivity;

@ActivityScope
@Component(modules = HomeModule.class, dependencies = AppComponent.class)
public interface HomeComponent {
    void inject(HomeActivity activity);
}