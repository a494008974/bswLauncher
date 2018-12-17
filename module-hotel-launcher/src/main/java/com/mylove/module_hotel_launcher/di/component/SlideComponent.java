package com.mylove.module_hotel_launcher.di.component;

import dagger.Component;

import com.jess.arms.di.component.AppComponent;

import com.mylove.module_hotel_launcher.di.module.SlideModule;

import com.jess.arms.di.scope.ActivityScope;
import com.mylove.module_hotel_launcher.mvp.ui.activity.SlideActivity;

@ActivityScope
@Component(modules = SlideModule.class, dependencies = AppComponent.class)
public interface SlideComponent {
    void inject(SlideActivity activity);
}