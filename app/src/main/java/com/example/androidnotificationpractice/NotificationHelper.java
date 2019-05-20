package com.example.androidnotificationpractice;

import android.app.AlertDialog;
import android.app.PendingIntent;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.NotificationManagerCompat;


public class NotificationHelper {


    public static void display_notification(Context context, String title, String body) {

        Intent intent = new Intent(context, Notification_Show_Activity.class);
        PendingIntent pendingIntent = PendingIntent.getActivity(context, 100, intent,
                PendingIntent.FLAG_CANCEL_CURRENT);

        NotificationCompat.Builder builder =
                new NotificationCompat.Builder(context, MainActivity.CHANNEL_ID)
                        .setSmallIcon(R.drawable.icon)
                        .setContentText(body)
                        .setContentTitle(title)
                        .setSmallIcon(R.drawable.icon)
                        .setContentIntent(pendingIntent)
                        .setAutoCancel(true)
                        .setPriority(NotificationCompat.PRIORITY_DEFAULT);

        NotificationManagerCompat notificationManagerCompat = NotificationManagerCompat.from(context);
        notificationManagerCompat.notify(1, builder.build());
    }

}
