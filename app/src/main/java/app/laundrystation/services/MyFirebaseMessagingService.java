package app.laundrystation.services;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Build;
import android.util.Log;
import android.widget.Toast;

import androidx.core.app.NotificationCompat;

import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

import androidx.localbroadcastmanager.content.LocalBroadcastManager;
import app.laundrystation.R;
import app.laundrystation.common.MyApplication;
import app.laundrystation.common.SharedPrefManager;
import app.laundrystation.ui.HomeActivity;

public class MyFirebaseMessagingService extends FirebaseMessagingService {
    String type, orderId;

    @Override
    public void onNewToken(String s) {
        super.onNewToken(s);
    }

    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {
        super.onMessageReceived(remoteMessage);
        type = remoteMessage.getData().get("type");
        orderId = remoteMessage.getData().get("order_id");
        showNotification(remoteMessage.getNotification());
        Intent intent = new Intent();
        intent.putExtra("content", remoteMessage.getNotification().getBody());
        intent.setAction("app.laundrystation.receiver");
        sendBroadcast(intent);

    }

    private void showNotification(RemoteMessage.Notification notification) {
        Uri path = Uri.parse("android.resource://" + getApplicationContext().getPackageName() + "/" + R.raw.ringtone);

        Intent intent = new Intent(this, HomeActivity.class);
        intent.putExtra("type", type);
        intent.putExtra("order_id", orderId);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, intent, PendingIntent.FLAG_ONE_SHOT);
        NotificationCompat.Builder builder = new NotificationCompat.Builder(this)
                .setSmallIcon(R.mipmap.ic_launcher)
                .setContentTitle(notification.getTitle())
                .setContentText(notification.getBody())
                .setAutoCancel(true)
                .setSound(path)
                .setContentIntent(pendingIntent);
        NotificationManager notificationManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            CharSequence name = getString(R.string.channel_name);
            String description = getString(R.string.channel_description);
            int importance = NotificationManager.IMPORTANCE_DEFAULT;
            NotificationChannel channel = new NotificationChannel("ch11002", name, importance);
            channel.setDescription(description);
            // Register the channel with the system; you can't change the importance
            // or other notification behaviors after this
            notificationManager = getSystemService(NotificationManager.class);
            try {
                notificationManager.createNotificationChannel(channel);
            } catch (Exception e) {
                Log.i("showNotification", "showNotification: " + e.getMessage() + "");
            }

        }

        notificationManager.notify(0, builder.build());
    }
}