package com.mylove.zhou;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;


/**
 * Created by Administrator on 2016/6/20.
 */
public class CoreReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        if (intent != null) {
            final String action = intent.getAction();
            if(Intent.ACTION_BOOT_COMPLETED.equals(action)
                    || "com.mylove.zhou.CoreReceiver".equals(action)){
                System.out.println("Toast ============ ACTION_BOOT_COMPLETED ====");
                Intent intentz = new Intent(context,MainActivity.class);
                context.startActivity(intentz);
            }
        }
    }
}
