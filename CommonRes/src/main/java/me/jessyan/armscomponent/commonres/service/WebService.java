package me.jessyan.armscomponent.commonres.service;

import android.app.Service;
import android.content.Intent;
import android.os.Environment;
import android.os.IBinder;
import android.util.Log;

import java.io.File;

import cn.hotapk.fhttpserver.FHttpManager;
import me.jessyan.armscomponent.commonres.controller.WebController;

public class WebService extends Service {
    private static final String TAG = "WebService";
    private FHttpManager fHttpManager;
    private static final int PORT = 9999;
    private static final String DIR = "webservice";
    public static String path;

    public WebService() {
        path = Environment.getExternalStorageDirectory().getPath() + File.separator + DIR + File.separator;
        Log.e(TAG,"WebService ............"+path);
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
            fHttpManager = FHttpManager.init(this, WebController.class);
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
