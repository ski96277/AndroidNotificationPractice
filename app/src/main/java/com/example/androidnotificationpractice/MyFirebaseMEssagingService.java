package com.example.androidnotificationpractice;


import android.content.Intent;

import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

public class MyFirebaseMEssagingService extends FirebaseMessagingService {

    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {
        super.onMessageReceived(remoteMessage);

/*
        if (remoteMessage.getNotification() != null) {
            //Call new Activity
            Intent intent = new Intent(getApplicationContext(), AlertActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            intent.putExtra("alert", "alert");
            startActivity(intent);
        }*/

//Call Notification
        if (remoteMessage.getNotification() != null) {

            String title = remoteMessage.getNotification().getTitle();
            String body = remoteMessage.getNotification().getBody();

            NotificationHelper.display_notification(getApplicationContext(), title, body);

        }
    }
}
