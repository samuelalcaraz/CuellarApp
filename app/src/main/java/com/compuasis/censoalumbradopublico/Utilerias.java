package com.compuasis.censoalumbradopublico;

import android.annotation.SuppressLint;
import android.content.Context;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.UUID;

public  class Utilerias {

    public static Gson getGson() {
        return new GsonBuilder()
                .setDateFormat("yyyy-MM-dd'T'HH:mm:ss")
                .create();
    }

    public static String getMacAddress(Context context) {
        WifiManager wifiManager = (WifiManager) context.getSystemService(Context.WIFI_SERVICE);
        WifiInfo wInfo = wifiManager.getConnectionInfo();
        @SuppressLint("HardwareIds") String macAddress = wInfo.getMacAddress();

        return macAddress;
    }

    public static String getUUID() {
        return UUID.randomUUID().toString();
    }


}
