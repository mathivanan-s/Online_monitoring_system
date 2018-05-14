package com.lakshmiindustrialautomation.www.lit.firebase;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.support.v4.app.NotificationCompat;

import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;
import com.lakshmiindustrialautomation.www.lit.Database.DatabaseHelper;
import com.lakshmiindustrialautomation.www.lit.R;
import com.lakshmiindustrialautomation.www.lit.ui.NormalAlertsListActivity;
import com.lakshmiindustrialautomation.www.lit.ui.StoppageAlertsListActivity;

/**
 * Created by Android on 5/3/2017.
 */

public class CustomFirebaseMessageReceiverService extends FirebaseMessagingService{
    Intent intent;

    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {
        DatabaseHelper databaseHelper = new DatabaseHelper(this);
        if (databaseHelper.isAuthenticated()) {
            if (remoteMessage.getData().get("title").equals("Stop Status")) {
                intent = new Intent(this, StoppageAlertsListActivity.class);
                databaseHelper.insertData(remoteMessage.getData().get("title"), remoteMessage.getData().get("body"), DatabaseHelper.SECOND_TABLE_NAME);
                databaseHelper.updateUserDetails(databaseHelper.USER_TABLE_COL_3, true);
            } else {
                intent = new Intent(this, NormalAlertsListActivity.class);
                databaseHelper.insertData(remoteMessage.getData().get("title"), remoteMessage.getData().get("body"), DatabaseHelper.FIRST_TABLE_NAME);
                databaseHelper.updateUserDetails(databaseHelper.USER_TABLE_COL_4, true);
            }
            intent.setFlags(intent.FLAG_ACTIVITY_CLEAR_TOP);
            PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, intent, PendingIntent.FLAG_ONE_SHOT);
            NotificationCompat.Builder notificationBuilder = new NotificationCompat.Builder(this);
            notificationBuilder.setContentTitle(remoteMessage.getData().get("title"));
            notificationBuilder.setContentText(remoteMessage.getData().get("body"));
            notificationBuilder.setAutoCancel(true);
            notificationBuilder.setSound(Uri.parse(databaseHelper.getCurrentNotificationTone()));
            notificationBuilder.setSmallIcon(R.drawable.notification_icon);
            notificationBuilder.setContentIntent(pendingIntent);
            if(databaseHelper.is_stoppage_notification_vibrate_enabled() && remoteMessage.getData().get("title").equals("Stop Status")){
                notificationBuilder.setVibrate(new long[]{200, 350, 250, 350, 250});
            } else if (databaseHelper.is_normal_notification_vibrate_enabled() && !remoteMessage.getData().get("title").equals("Stop Status")){
                notificationBuilder.setVibrate(new long[]{200, 350, 250, 350, 250});
            }
            notificationBuilder.setLights(Color.RED, 3000, 3000);
            NotificationManager notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
            notificationManager.notify(0, notificationBuilder.build());

        }
    }
}
