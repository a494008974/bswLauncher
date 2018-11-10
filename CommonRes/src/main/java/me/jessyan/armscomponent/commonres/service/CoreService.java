package me.jessyan.armscomponent.commonres.service;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.util.Log;

import com.yanzhenjie.andserver.AndServer;
import com.yanzhenjie.andserver.Server;

import org.simple.eventbus.EventBus;

import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.util.Enumeration;
import java.util.concurrent.TimeUnit;
import java.util.regex.Pattern;

import me.jessyan.armscomponent.commonsdk.core.EventBusHub;


/**
 * Created by Yan Zhenjie on 2018/6/9.
 */
public class CoreService extends Service {

    private Server mServer;
    private static final String TAG = "CoreService";

    public static final int PORT = 9090;


    @Override
    public void onCreate() {
        mServer = AndServer.serverBuilder()
                .inetAddress(getLocalIPAddress())
                .port(PORT)
                .timeout(10, TimeUnit.SECONDS)
                .listener(new Server.ServerListener() {
                    @Override
                    public void onStarted() {
                        String hostAddress = mServer.getInetAddress().getHostAddress();
                        Log.e(TAG,"AndServer onStarted ip = "+hostAddress);
                        EventBus.getDefault().post(hostAddress,"serverStart");
                    }

                    @Override
                    public void onStopped() {
                        Log.e(TAG,"AndServer onStopped");
                    }

                    @Override
                    public void onException(Exception e) {
                        Log.e(TAG,"AndServer onException");
                    }
                })
                .build();
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        startServer();
        return START_STICKY;
    }

    @Override
    public void onDestroy() {
        stopServer();
        super.onDestroy();
    }

    /**
     * Start server.
     */
    private void startServer() {
        if (!mServer.isRunning()) {
            mServer.startup();
        }
    }

    /**
     * Stop server.
     */
    private void stopServer() {
        mServer.shutdown();
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    /**
     * Ipv4 address check.
     */
    private static final Pattern IPV4_PATTERN = Pattern.compile(
            "^(" + "([0-9]|[1-9][0-9]|1[0-9]{2}|2[0-4][0-9]|25[0-5])\\.){3}" +
                    "([0-9]|[1-9][0-9]|1[0-9]{2}|2[0-4][0-9]|25[0-5])$");


    public static InetAddress getLocalIPAddress() {
        Enumeration<NetworkInterface> enumeration = null;
        try {
            enumeration = NetworkInterface.getNetworkInterfaces();
        } catch (SocketException e) {
            e.printStackTrace();
        }
        if (enumeration != null) {
            while (enumeration.hasMoreElements()) {
                NetworkInterface nif = enumeration.nextElement();
                Enumeration<InetAddress> inetAddresses = nif.getInetAddresses();
                if (inetAddresses != null) {
                    while (inetAddresses.hasMoreElements()) {
                        InetAddress inetAddress = inetAddresses.nextElement();
                        if (!inetAddress.isLoopbackAddress() && isIPv4Address(inetAddress.getHostAddress())) {
                            return inetAddress;
                        }
                    }
                }
            }
        }
        return null;
    }

    public static boolean isIPv4Address(String input) {
        return IPV4_PATTERN.matcher(input).matches();
    }
}