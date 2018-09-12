package com.mylove.zhou;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.alibaba.android.arouter.facade.annotation.Route;

import me.jessyan.armscomponent.commonsdk.core.RouterHub;

@Route(path = RouterHub.ZHOU_MAINACTIVITY)
public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        MessageToast messageToast = new MessageToast(this);
        messageToast.setContent(R.layout.zhou_message_toast);
        messageToast.display();
        finish();
    }
}
