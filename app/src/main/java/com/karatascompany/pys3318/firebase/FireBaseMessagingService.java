package com.karatascompany.pys3318.firebase;

import android.app.NotificationManager;
import android.content.ContentResolver;
import android.media.Ringtone;
import android.media.RingtoneManager;
import android.net.Uri;
import androidx.core.app.NotificationCompat;

import com.google.firebase.messaging.RemoteMessage;
import com.karatascompany.pys3318.R;

/**
 * Created by azizmahmud on 1.4.2018.
 */

public class FireBaseMessagingService extends com.google.firebase.messaging.FirebaseMessagingService{

    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {
        super.onMessageReceived(remoteMessage);

        String messageTitle = remoteMessage.getNotification().getTitle();
        String messageBody = remoteMessage.getNotification().getBody();

        NotificationCompat.Builder mBuilder =
                new NotificationCompat.Builder(this,getString(R.string.default_notification_channel_id))
                .setSmallIcon(R.drawable.icons8_checkmark_48)
                .setContentTitle(messageTitle)
                .setContentText(messageBody);

        int mNotificationId = (int) System.currentTimeMillis();
        NotificationManager mNotifiyMgr =
                (NotificationManager) getSystemService(NOTIFICATION_SERVICE);

        try {
            Uri alarmSound = Uri.parse(ContentResolver.SCHEME_ANDROID_RESOURCE
                    + "://" + this.getPackageName() + "/raw/notification");
            Ringtone r = RingtoneManager.getRingtone(this, alarmSound);
            r.play();
        } catch (Exception e) {
            e.printStackTrace();
        }

        mNotifiyMgr.notify(mNotificationId,mBuilder.build());

    }
}
