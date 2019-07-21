package com.disha.radiobulbul.services;

import android.app.Notification;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.os.IBinder;

import androidx.annotation.Nullable;
import androidx.core.app.NotificationCompat;

import com.disha.radiobulbul.R;
import com.disha.radiobulbul.activity.MainActivity;

import static android.app.PendingIntent.FLAG_UPDATE_CURRENT;
import static com.disha.radiobulbul.utils.PlayerStatus.CHANNEL_ID;

public class BgService extends Service {

    @Override
    public void onCreate() {
        super.onCreate();
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {

        Intent notificationIntent = new Intent(this, MainActivity.class);
        notificationIntent.putExtra("key",1);
        PendingIntent pendingIntent = PendingIntent.getActivity(this,
                0, notificationIntent, FLAG_UPDATE_CURRENT);



        Notification notification = new NotificationCompat.Builder(this, CHANNEL_ID)
                .setContentTitle("Radio Bulbul")
                .setContentText("Your Radio is playing in the background.")
                .setSmallIcon(R.drawable.ic_fm_radio)
                .setContentIntent(pendingIntent)
                .build();

        startForeground(1, notification);

        //do heavy work on a background thread
        //stopSelf();

        return START_NOT_STICKY;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }
}