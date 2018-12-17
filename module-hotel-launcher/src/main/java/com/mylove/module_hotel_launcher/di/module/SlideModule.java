package com.mylove.module_hotel_launcher.di.module;

import com.jess.arms.di.scope.ActivityScope;

import dagger.Module;
import dagger.Provides;

import com.mylove.module_hotel_launcher.mvp.contract.SlideContract;
import com.mylove.module_hotel_launcher.mvp.model.SlideModel;


@Module
public class SlideModule {
    private SlideContract.View view;

    /**
     * 构建SlideModule时,将View的实现类传进来,这样就可以提供View的实现类给presenter
     *
     * @param view
     */
    public SlideModule(SlideContract.View view) {
        this.view = view;
    }

    @ActivityScope
    @Provides
    SlideContract.View provideSlideView() {
        return this.view;
    }

    @ActivityScope
    @Provides
    SlideContract.Model provideSlideModel(SlideModel model) {
        return model;
    }
}