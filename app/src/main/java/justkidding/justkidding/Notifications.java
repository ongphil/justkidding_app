package justkidding.justkidding;

import android.app.Notification;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.NotificationManagerCompat;

import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;


public class Notifications extends FirebaseMessagingService {

    @Override
    public void onNewToken(String token) {
        super.onNewToken(token);

    }

    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {
        Notification notification = new NotificationCompat.Builder(this)
                .setContentTitle(remoteMessage.getNotification().getTitle())
                .setContentText(remoteMessage.getNotification().getBody())
                .setSmallIcon(R.drawable.logo_icon_app)
                .build();
        NotificationManagerCompat manager = NotificationManagerCompat.from(getApplicationContext());
        manager.notify(123, notification);
    }
}
