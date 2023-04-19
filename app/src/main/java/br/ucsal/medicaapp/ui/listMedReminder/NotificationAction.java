package br.ucsal.medicaapp.ui.listMedReminder;

import android.app.AlarmManager;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.util.Log;

import androidx.core.app.NotificationCompat;

import java.util.Calendar;

import br.ucsal.medicaapp.R;

public class NotificationAction {

    private static final String TAG = "NotificationScheduler";
    private static final int NOTIFICATION_ID = 1;
    private static final String NOTIFICATION_CHANNEL_ID = "superSecretMedicaAppChannelId";

    public static void scheduleNotification(Context context, String medName, Calendar notificationTime) {

        NotificationAsyncTask notificationAsyncTask = new NotificationAsyncTask(context, medName, notificationTime);
        notificationAsyncTask.execute();

    }

    public static void shootNotification(Context context, String medName, String medMessage) {

        NotificationManager notificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel channel = new NotificationChannel(NOTIFICATION_CHANNEL_ID, "Medicine Notification", NotificationManager.IMPORTANCE_HIGH);
            channel.setDescription("Notificação de medicamento");
            notificationManager.createNotificationChannel(channel);
        }

        NotificationCompat.Builder builder = new NotificationCompat.Builder(context, NOTIFICATION_CHANNEL_ID)
                .setSmallIcon(R.drawable.baseline_monitor_heart_24)
                .setContentTitle(medName)
                .setContentText(medMessage)
                .setPriority(NotificationCompat.PRIORITY_HIGH)
                .setAutoCancel(true);

        notificationManager.notify(NOTIFICATION_ID, builder.build());
    }

    public static void cancelNotification(Context context) {

        Intent notificationIntent = new Intent(context, NotificationAsyncTask.class);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(context, NOTIFICATION_ID, notificationIntent, PendingIntent.FLAG_UPDATE_CURRENT);

        AlarmManager alarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
        alarmManager.cancel(pendingIntent);

        NotificationManager notificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        notificationManager.cancel(NOTIFICATION_ID);

        Log.d(TAG, "Notification cancelled");
    }

}
