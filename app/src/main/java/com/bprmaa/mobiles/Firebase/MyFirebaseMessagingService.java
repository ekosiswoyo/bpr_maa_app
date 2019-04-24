package com.bprmaa.mobiles.Firebase;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;

import android.net.Uri;
import android.os.Handler;
import android.os.Looper;
import android.provider.Settings;
import android.support.v4.app.NotificationCompat;
import android.support.v4.content.LocalBroadcastManager;
import android.util.Log;

import com.bprmaa.mobiles.Activity.ListActivity;
import com.bprmaa.mobiles.Helper.Config;
import com.bprmaa.mobiles.MainActivity;
import com.bprmaa.mobiles.Product.ProductActivity;
import com.bprmaa.mobiles.R;
import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

import org.json.JSONException;
import org.json.JSONObject;

import static android.content.Intent.FLAG_ACTIVITY_NEW_TASK;

public class MyFirebaseMessagingService extends FirebaseMessagingService {
    private static final String TAG = MyFirebaseMessagingService.class.getSimpleName();

    private NotificationUtils notificationUtils;
    private Intent intent;
    private NotificationManager mNotificationManager;
    private NotificationCompat.Builder mBuilder;
    public static final String NOTIFICATION_CHANNEL_ID = "10001";

    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {
        Log.e(TAG, "From: " + remoteMessage.getFrom());

        if (remoteMessage == null)
            return;

        // Check if message contains a notification payload.
        if (remoteMessage.getNotification() != null) {
            Log.e(TAG, "Notification Body: " + remoteMessage.getNotification().getBody());
            if (remoteMessage.getData().size() > 0) {
                try {
                    JSONObject json = new JSONObject(remoteMessage.getData().toString());
                    JSONObject data = json.getJSONObject("data");
                    String title = data.getString("title");
                    String message = data.getString("message");
                    boolean isBackground = data.getBoolean("is_background");
                    String imageUrl = data.getString("image");
                    String timestamp = data.getString("timestamp");
                    String payload = remoteMessage.getData().toString();
                    handleNotification(message, remoteMessage.getNotification().getLink(), title, imageUrl, payload);
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            } else {
                handleNotification(remoteMessage.getNotification().getBody(), remoteMessage.getNotification().getLink(), remoteMessage.getNotification().getTitle(), "", remoteMessage.getData().toString());

            }
        }

        // Check if message contains a data payload.
        if (remoteMessage.getData() != null) {
            Log.e(TAG, "Data Payload: " + remoteMessage.getData().toString());
            try {
                JSONObject json = new JSONObject(String.valueOf(remoteMessage.getData()));
                handleDataMessage(json);
            } catch (JSONException e) {
                Log.e(TAG, "Exception: " + e.getMessage());
            }
        }
    }

    private void handleNotification(final String message, Uri link, final String title, String imageUrl, final String payload) {
        if (!NotificationUtils.isAppIsInBackground(getApplicationContext())) {
            // app is in foreground, broadcast the push message
            Intent pushNotification = new Intent(Config.PUSH_NOTIFICATION);
            pushNotification.putExtra("message", message);
            pushNotification.putExtra("title", title);
            pushNotification.putExtra("imgUrl", imageUrl);
            pushNotification.putExtra("payload", payload);
            LocalBroadcastManager.getInstance(this).sendBroadcast(pushNotification);
            LocalBroadcastManager.getInstance(this).sendBroadcast(pushNotification);

            Intent resultIntent = new Intent(MyFirebaseMessagingService.this , MainActivity.class);
            resultIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

            PendingIntent resultPendingIntent = PendingIntent.getActivity(MyFirebaseMessagingService.this,
                    0 /* Request code */, resultIntent,
                    PendingIntent.FLAG_UPDATE_CURRENT);

            mBuilder = new NotificationCompat.Builder(MyFirebaseMessagingService.this);
            mBuilder.setSmallIcon(R.mipmap.ic_launcher);
            mBuilder.setContentTitle(title)
                    .setContentText(message)
                    .setAutoCancel(false)
                    .setSound(Settings.System.DEFAULT_NOTIFICATION_URI)
                    .setContentIntent(resultPendingIntent);

            mNotificationManager = (NotificationManager)getSystemService(Context.NOTIFICATION_SERVICE);

            if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O)
            {
                int importance = NotificationManager.IMPORTANCE_HIGH;
                NotificationChannel notificationChannel = new NotificationChannel(NOTIFICATION_CHANNEL_ID, "NOTIFICATION_CHANNEL_NAME", importance);
                notificationChannel.enableLights(true);
                notificationChannel.setLightColor(Color.RED);
                notificationChannel.enableVibration(true);
                notificationChannel.setVibrationPattern(new long[]{100, 200, 300, 400, 500, 400, 300, 200, 400});
                assert mNotificationManager != null;
                mBuilder.setChannelId(NOTIFICATION_CHANNEL_ID);
                mNotificationManager.createNotificationChannel(notificationChannel);
            }
            assert mNotificationManager != null;
            mNotificationManager.notify(0 /* Request Code */, mBuilder.build());

            // TODO penting

            /*
            // play notification sound
            NotificationUtils notificationUtils = new NotificationUtils(getApplicationContext());
            notificationUtils.playNotificationSound();

            NotificationCompat.Builder builder = (NotificationCompat.Builder) new NotificationCompat.Builder(MyFirebaseMessagingService.this)
                    .setSmallIcon(R.mipmap.ic_launcher) //ikon notification
                    .setContentTitle("" + title)//judul konten
//                    .setContentIntent(pendingIntent)//memanggil object pending intent
                    .setAutoCancel(true)//untuk menswipe atau menghapus notification
                    .setContentText("" + message); //isi text
            NotificationManager notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
            notificationManager.notify(NOTIFICATION_ID, builder.build()
            );
            */

        } else {
            // If the app is in background, firebase itself handles the notification
            Intent pushNotification = new Intent(Config.PUSH_NOTIFICATION);
            pushNotification.putExtra("message", message);
            pushNotification.putExtra("title", title);
            pushNotification.putExtra("imgUrl", imageUrl);
            pushNotification.putExtra("payload", payload);
            LocalBroadcastManager.getInstance(this).sendBroadcast(pushNotification);


            // play notification sound
            NotificationUtils notificationUtils = new NotificationUtils(getApplicationContext());
            notificationUtils.playNotificationSound();

            Handler mainHandler = new Handler(Looper.getMainLooper());
            Runnable myRunnable = new Runnable() {
                @Override
                public void run() {
                    Intent resultIntent = new Intent(MyFirebaseMessagingService.this , MainActivity.class);
                    resultIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

                    PendingIntent resultPendingIntent = PendingIntent.getActivity(MyFirebaseMessagingService.this,
                            0 /* Request code */, resultIntent,
                            PendingIntent.FLAG_UPDATE_CURRENT);

                    mBuilder = new NotificationCompat.Builder(MyFirebaseMessagingService.this);
                    mBuilder.setSmallIcon(R.mipmap.ic_launcher);
                    mBuilder.setContentTitle(title)
                            .setContentText(message)
                            .setAutoCancel(false)
                            .setSound(Settings.System.DEFAULT_NOTIFICATION_URI)
                            .setContentIntent(resultPendingIntent);

                    mNotificationManager = (NotificationManager)getSystemService(Context.NOTIFICATION_SERVICE);

                    if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O)
                    {
                        int importance = NotificationManager.IMPORTANCE_HIGH;
                        NotificationChannel notificationChannel = new NotificationChannel(NOTIFICATION_CHANNEL_ID, "NOTIFICATION_CHANNEL_NAME", importance);
                        notificationChannel.enableLights(true);
                        notificationChannel.setLightColor(Color.RED);
                        notificationChannel.enableVibration(true);
                        notificationChannel.setVibrationPattern(new long[]{100, 200, 300, 400, 500, 400, 300, 200, 400});
                        assert mNotificationManager != null;
                        mBuilder.setChannelId(NOTIFICATION_CHANNEL_ID);
                        mNotificationManager.createNotificationChannel(notificationChannel);
                    }
                    assert mNotificationManager != null;
                    mNotificationManager.notify(0 /* Request Code */, mBuilder.build());
//                    String titles = "";
//
//                    //menginisialiasasi intent
////                    PendingIntent pendingIntent = PendingIntent.getActivity(MyFirebaseMessagingService.this, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT);
//                    //untuk memanggil activity di Notification
//                    /*
//                    Menmbangun atau mensetup Notification dengan NotificationCompat.Builder
//                    */
//                    NotificationCompat.Builder builder = (NotificationCompat.Builder) new NotificationCompat.Builder(MyFirebaseMessagingService.this)
//                            .setSmallIcon(R.mipmap.ic_launcher) //ikon notification
//                            .setContentTitle("" + title)//judul konten
//                            //.setContentIntent(pendingIntent)//memanggil object pending intent
//                            .setAutoCancel(true)//untuk menswipe atau menghapus notification
//                            .setContentText("" + message); //isi text
//
//                    /*
//                    Kemudian kita harus menambahkan Notification dengan menggunakan NotificationManager
//                    */
//
//                    NotificationManager notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
//                    notificationManager.notify(NOTIFICATION_ID, builder.build()
//                    );
                }
            };
            mainHandler.post(myRunnable);
        }
    }

    private void handleDataMessage(JSONObject json) {
        Log.e(TAG, "push json: " + json.toString());

        try {
            JSONObject data = json.getJSONObject("data");

            final String title = data.getString("title");
            final String message = data.getString("message");
            boolean isBackground = data.getBoolean("is_background");
            String imageUrl = data.getString("image");
            String timestamp = data.getString("timestamp");
//            JSONObject payload = data.getJSONObject("payload");
            final String payload = data.getString("payload");

            Log.e(TAG, "title: " + title);
            Log.e(TAG, "message: " + message);
            Log.e(TAG, "isBackground: " + isBackground);
            Log.e(TAG, "payload: " + payload.toString());
            Log.e(TAG, "imageUrl: " + imageUrl);
            Log.e(TAG, "timestamp: " + timestamp);

            if (payload.equals("product")) {
                intent = new Intent(getApplicationContext(), ProductActivity.class);
            } else if (payload.equals("layanan")) {
                intent = new Intent(getApplicationContext(), ListActivity.class);
                intent.putExtra("jenis", "layanan");
            } else if (payload.equals("news")) {
                intent = new Intent(getApplicationContext(), ListActivity.class);
                intent.putExtra("jenis", "news");
            } else if (payload.equals("lelang")) {
                intent = new Intent(getApplicationContext(), ListActivity.class);
                intent.putExtra("jenis", "lelang");
            } else if (payload.equals("promo")) {
                intent = new Intent(getApplicationContext(), ListActivity.class);
                intent.putExtra("jenis", "promo");
            } else {
                intent = new Intent(getApplicationContext(), MainActivity.class);
            }
            if (!NotificationUtils.isAppIsInBackground(getApplicationContext())) {

                // app is in foreground, broadcast the push message
                Intent pushNotification = new Intent(Config.PUSH_NOTIFICATION);
                pushNotification.putExtra("message", message);
                pushNotification.putExtra("title", title);
                pushNotification.putExtra("imgUrl", imageUrl);
                pushNotification.putExtra("payload", payload);
                LocalBroadcastManager.getInstance(this).sendBroadcast(pushNotification);


                // play notification sound
                NotificationUtils notificationUtils = new NotificationUtils(getApplicationContext());
                //notificationUtils.playNotificationSound();
                Handler mainHandler = new Handler(Looper.getMainLooper());
                Runnable myRunnable = new Runnable() {
                    @Override
                    public void run() {
                        Intent resultIntent = new Intent(MyFirebaseMessagingService.this , MainActivity.class);
                        resultIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

                        PendingIntent resultPendingIntent = PendingIntent.getActivity(MyFirebaseMessagingService.this,
                                0 /* Request code */, resultIntent,
                                PendingIntent.FLAG_UPDATE_CURRENT);

                        mBuilder = new NotificationCompat.Builder(MyFirebaseMessagingService.this);
                        mBuilder.setSmallIcon(R.mipmap.ic_launcher);
                        mBuilder.setContentTitle(title)
                                .setContentText(message)
                                .setAutoCancel(false)
                                .setSound(Settings.System.DEFAULT_NOTIFICATION_URI)
                                .setContentIntent(resultPendingIntent);

                        mNotificationManager = (NotificationManager)getSystemService(Context.NOTIFICATION_SERVICE);

                        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O)
                        {
                            int importance = NotificationManager.IMPORTANCE_HIGH;
                            NotificationChannel notificationChannel = new NotificationChannel(NOTIFICATION_CHANNEL_ID, "NOTIFICATION_CHANNEL_NAME", importance);
                            notificationChannel.enableLights(true);
                            notificationChannel.setLightColor(Color.RED);
                            notificationChannel.enableVibration(true);
                            notificationChannel.setVibrationPattern(new long[]{100, 200, 300, 400, 500, 400, 300, 200, 400});
                            assert mNotificationManager != null;
                            mBuilder.setChannelId(NOTIFICATION_CHANNEL_ID);
                            mNotificationManager.createNotificationChannel(notificationChannel);
                        }
                        assert mNotificationManager != null;
                        mNotificationManager.notify(0 /* Request Code */, mBuilder.build());

//                        String titles = "";
//
//                        PendingIntent pendingIntent = PendingIntent.getActivity(MyFirebaseMessagingService.this, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT);
//                        NotificationCompat.Builder builder = (NotificationCompat.Builder) new NotificationCompat.Builder(MyFirebaseMessagingService.this)
//                                .setSmallIcon(R.mipmap.ic_launcher) //ikon notification
//                                .setContentTitle("" + title)//judul konten
//                                .setContentIntent(pendingIntent)//memanggil object pending intent
//                                .setAutoCancel(true)//untuk menswipe atau menghapus notification
//                                .setStyle(new NotificationCompat.BigTextStyle().bigText(message))
//                                .setContentText(message); //isi text
//
//                        NotificationManager notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
//                        notificationManager.notify((int) System.currentTimeMillis(), builder.build());
                    }
                };
                mainHandler.post(myRunnable);
            } else {
                // app is in background, show the notification in notification tray
                Handler mainHandler = new Handler(Looper.getMainLooper());
                Runnable myRunnable = new Runnable() {
                    @Override
                    public void run() {
                        Intent resultIntent = new Intent(MyFirebaseMessagingService.this , MainActivity.class);
                        resultIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

                        PendingIntent resultPendingIntent = PendingIntent.getActivity(MyFirebaseMessagingService.this,
                                0 /* Request code */, resultIntent,
                                PendingIntent.FLAG_UPDATE_CURRENT);

                        mBuilder = new NotificationCompat.Builder(MyFirebaseMessagingService.this);
                        mBuilder.setSmallIcon(R.mipmap.ic_launcher);
                        mBuilder.setContentTitle(title)
                                .setContentText(message)
                                .setAutoCancel(false)
                                .setSound(Settings.System.DEFAULT_NOTIFICATION_URI)
                                .setContentIntent(resultPendingIntent);

                        mNotificationManager = (NotificationManager)getSystemService(Context.NOTIFICATION_SERVICE);

                        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O)
                        {
                            int importance = NotificationManager.IMPORTANCE_HIGH;
                            NotificationChannel notificationChannel = new NotificationChannel(NOTIFICATION_CHANNEL_ID, "NOTIFICATION_CHANNEL_NAME", importance);
                            notificationChannel.enableLights(true);
                            notificationChannel.setLightColor(Color.RED);
                            notificationChannel.enableVibration(true);
                            notificationChannel.setVibrationPattern(new long[]{100, 200, 300, 400, 500, 400, 300, 200, 400});
                            assert mNotificationManager != null;
                            mBuilder.setChannelId(NOTIFICATION_CHANNEL_ID);
                            mNotificationManager.createNotificationChannel(notificationChannel);
                        }
                        assert mNotificationManager != null;
                        mNotificationManager.notify(0 /* Request Code */, mBuilder.build());

//                        PendingIntent pendingIntent = PendingIntent.getActivity(MyFirebaseMessagingService.this, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT);
//                        NotificationCompat.Builder builder = (NotificationCompat.Builder) new NotificationCompat.Builder(MyFirebaseMessagingService.this)
//                                .setSmallIcon(R.mipmap.ic_launcher) //ikon notification
//                                .setContentTitle("" + title)//judul konten
//                                .setContentIntent(pendingIntent)//memanggil object pending intent
//                                .setAutoCancel(true)//untuk menswipe atau menghapus notification
//                                .setStyle(new NotificationCompat.BigTextStyle().bigText(message))
//                                .setContentText(message); //isi text
//
//                        NotificationManager notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
//                        notificationManager.notify((int) System.currentTimeMillis(), builder.build());
                    }
                };
                mainHandler.post(myRunnable);
            }
        } catch (JSONException e) {
            Log.e(TAG, "Json Exception: " + e.getMessage());
        } catch (Exception e) {
            Log.e(TAG, "Exception: " + e.getMessage());
        }
    }

    /**
     * Showing notification with text only
     */
    private void showNotificationMessage(Context context, String title, String message, String timeStamp, Intent intent) {
        notificationUtils = new NotificationUtils(context);
        intent.setFlags(FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);

        notificationUtils.showNotificationMessage(title, message, timeStamp, intent);
    }

    /**
     * Showing notification with text and image
     */
    private void showNotificationMessageWithBigImage(Context context, String title, String message, String timeStamp, Intent intent, String imageUrl) {
        notificationUtils = new NotificationUtils(context);

        intent.setFlags(FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        notificationUtils.showNotificationMessage(title, message, timeStamp, intent, imageUrl);
    }
}
