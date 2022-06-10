package com.example.advancedprojectandroidclient.services;

import android.app.NotificationChannel;
import android.os.Build;

import androidx.annotation.NonNull;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import com.example.advancedprojectandroidclient.R;
import com.example.advancedprojectandroidclient.api.RegisteredUserApi;
import com.google.firebase.messaging.FirebaseMessagingService;

public class FirebaseService extends FirebaseMessagingService {

    public static String token;
    private static int notificationId = 0;

    public FirebaseService() {
    }

    public static void sendRegistrationToServer(String token) {
        RegisteredUserApi api = new RegisteredUserApi();
        api.setPhoneToken(token);
    }

    @Override
    public void onNewToken(@NonNull String token) {
        super.onNewToken(token);
        FirebaseService.token = token;
        sendRegistrationToServer(token);
    }

    @Override
    public void onMessageReceived(@NonNull com.google.firebase.messaging.RemoteMessage remoteMessage) {
        super.onMessageReceived(remoteMessage);
        if (remoteMessage.getNotification() != null){
            createNotificationChannel();
            NotificationManagerCompat notificationManager = NotificationManagerCompat.from(this);

            NotificationCompat.Builder builder = new NotificationCompat.Builder(this, getString(R.string.channel_id))
                    .setSmallIcon(R.drawable.ic_launcher_background)
                    .setContentTitle(remoteMessage.getNotification().getTitle())
                    .setContentText(remoteMessage.getNotification().getBody())
                    .setPriority(NotificationCompat.PRIORITY_DEFAULT);

            notificationManager.notify(notificationId, builder.build());
            notificationId++;
        }
    }

    private void createNotificationChannel(){
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){
            // Create the NotificationChannel, but only on API 26+ because
            // the NotificationChannel class is new and not in the support library

            CharSequence name = getString(R.string.channel_name);
            String description = getString(R.string.channel_description);
            int importance = android.app.NotificationManager.IMPORTANCE_DEFAULT;
            NotificationChannel channel = new NotificationChannel(getString(R.string.channel_id), name, importance);
            channel.setDescription(description);

            // Register the channel with the system
            android.app.NotificationManager notificationManager = getSystemService(android.app.NotificationManager.class);
            notificationManager.createNotificationChannel(channel);
        }
    }
}