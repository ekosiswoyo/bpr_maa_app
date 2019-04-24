package com.bprmaa.mobiles.Firebase;

import android.annotation.TargetApi;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Context;
import android.content.ContextWrapper;
import android.graphics.Color;
import android.os.Build;

import com.bprmaa.mobiles.R;

/**
 * Created by cis on 30/07/2018.
 */

public class NotificationHelper extends ContextWrapper {

    public static final String chanel_id ="com.bprmaa.bprmaa.Service.Firebase";
    public static final String chanel_name = "can_creative";
    private NotificationManager manager;
    public NotificationHelper(Context base) {
        super(base);
        createChanels();
    }

    private void createChanels() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel channel = new NotificationChannel(chanel_id,chanel_name, NotificationManager.IMPORTANCE_DEFAULT);
            channel.enableLights(true);
            channel.enableVibration(true);
            channel.setLightColor(Color.GREEN);
            channel.setLockscreenVisibility(Notification.VISIBILITY_PRIVATE);
            
            getManager();
        }
    }

    public NotificationManager getManager() {
        if (manager == null)
            manager = (NotificationManager)getSystemService(Context.NOTIFICATION_SERVICE);
        return manager;
    }

    @TargetApi(Build.VERSION_CODES.O)
    public Notification.Builder getNotife(String title, String body){
            return new Notification.Builder(getApplicationContext(),chanel_id)
                    .setContentText(body)
                    .setContentTitle(title)
                    .setSmallIcon(R.mipmap.ic_launcher)
                    .setAutoCancel(true);
    }
}
