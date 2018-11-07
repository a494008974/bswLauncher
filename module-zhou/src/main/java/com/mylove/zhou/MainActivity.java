package com.mylove.zhou;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.alibaba.android.arouter.facade.annotation.Route;

import me.jessyan.armscomponent.commonsdk.core.RouterHub;
import me.jessyan.armscomponent.commonservice.dao.User;
import me.jessyan.armscomponent.commonservice.zhou.dao.ZhouDao;

@Route(path = RouterHub.ZHOU_HOMEACTIVITY)
public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.zhou_activity_main);

        User user = new User();
        user.setUsername("zhou");
        user.setPassword("1234aaaaa");
        ZhouDao.insert(user);
    }
}
