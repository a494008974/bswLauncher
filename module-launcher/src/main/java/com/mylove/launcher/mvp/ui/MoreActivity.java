package com.mylove.launcher.mvp.ui;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.mylove.launcher.R;

import me.jessyan.armscomponent.commonsdk.core.RouterHub;

@Route(path = RouterHub.LAUNCHER_MOREACTIVITY)
public class MoreActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.launcher_activity_more);
    }
}
