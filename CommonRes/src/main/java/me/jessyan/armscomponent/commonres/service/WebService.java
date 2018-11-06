package me.jessyan.armscomponent.commonres.service;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.util.Log;

import cn.hotapk.fhttpserver.FHttpManager;
import me.jessyan.armscomponent.commonres.controller.UserController;

public class WebService extends Service {
    private static final String TAG = "WebService";
    private FHttpManager fHttpManager;
    private static final int PORT = 9999;
    public WebService() {
    }

    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        throw new UnsupportedOperationException("Not yet implemented");
    }

    @Override
    public void onCreate() {
        super.onCreate();
        Log.e(TAG,"onCreate ............");
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Log.e(TAG,"onStartCommand ............");
        if(fHttpManager == null){
            fHttpManager = FHttpManager.init(this, UserController.class);
            fHttpManager.setAllowCross(true);
            fHttpManager.setPort(PORT);
        }
        fHttpManager.startServer();
        return super.onStartCommand(intent, flags, startId);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (fHttpManager != null){
            fHttpManager.stopServer();
        }
        Log.e(TAG,"onDestroy ............");
    }
}
