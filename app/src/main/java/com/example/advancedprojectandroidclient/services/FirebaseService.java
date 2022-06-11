package com.example.advancedprojectandroidclient.services;

import android.app.NotificationChannel;
import android.app.PendingIntent;
import android.content.Intent;
import android.os.Build;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import com.example.advancedprojectandroidclient.MyApplication;
import com.example.advancedprojectandroidclient.R;
import com.example.advancedprojectandroidclient.activities.ChatActivity;
import com.example.advancedprojectandroidclient.api.RegisteredUserApi;
import com.google.firebase.messaging.FirebaseMessagingService;

public class FirebaseService extends FirebaseMessagingService {

    public static String token;

    public FirebaseService() {
        MyApplication.refreshTokenRepository.autoRenewTokens(1, 100);
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

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    public void onMessageReceived(@NonNull com.google.firebase.messaging.RemoteMessage remoteMessage) {
        super.onMessageReceived(remoteMessage);
        String username = remoteMessage.getData().get("username");
        String current = MyApplication.messagesRepository.getWith();
        MyApplication.messagesRepository.setWith(remoteMessage.getData().get("username"));
        MyApplication.messagesRepository.getAll();
        // Second condition is to prevent the notification from being sent when the user is in the chat activity
        // With the user who sent the message
        if (remoteMessage.getNotification() != null &&
                (!ChatActivity.running || !current.equals(username) )){
            createNotificationChannel();
            NotificationManagerCompat notificationManager = NotificationManagerCompat.from(this);

            Intent i = new Intent(this, ChatActivity.class);
            i.putExtra("contactName", remoteMessage.getData().get("nickname"));
            i.putExtra("contactId", remoteMessage.getData().get("username"));
            PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, i, PendingIntent.FLAG_IMMUTABLE);

            NotificationCompat.Builder builder = new NotificationCompat.Builder(this, getString(R.string.channel_id))
                    .setSmallIcon(R.drawable.ic_launcher_background)
                    .setContentTitle(remoteMessage.getNotification().getTitle())
                    .setContentText(remoteMessage.getNotification().getBody())
                    .setStyle(new NotificationCompat.BigTextStyle()
                            .bigText(remoteMessage.getNotification().getBody()))
                    .setPriority(NotificationCompat.PRIORITY_DEFAULT)
                    .setAutoCancel(true)
                    .setContentIntent(pendingIntent);

            notificationManager.notify(String.valueOf(remoteMessage.getNotification().getTitle()).hashCode(), builder.build());
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