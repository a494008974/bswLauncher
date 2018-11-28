package com.mylove.tvlauncher.mvp.ui;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.mylove.tvlauncher.R;

import me.jessyan.armscomponent.commonsdk.core.RouterHub;

@Route(path = RouterHub.TVLAUNCHER_MOREACTIVITY)
public class MoreActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.tv_launcher_activity_more);
    }
}
