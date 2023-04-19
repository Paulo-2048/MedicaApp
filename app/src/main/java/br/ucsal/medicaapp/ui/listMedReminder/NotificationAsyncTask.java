package br.ucsal.medicaapp.ui.listMedReminder;

import static br.ucsal.medicaapp.ui.listMedReminder.NotificationAction.shootNotification;

import android.app.Notification;
import android.app.NotificationManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.util.Log;

import java.time.LocalTime;
import java.util.Calendar;

public class NotificationAsyncTask extends AsyncTask<Void, Void, Void> {

    private Context context;
    private String medName;
    private String medMessage;
    private Calendar notificationTime;

    public NotificationAsyncTask(Context context, String medName, Calendar notificationTime) {
        this.context = context;
        this.medName = medName;
        this.medMessage = "Hora de tomar o remédio " + medName + "!";
        this.notificationTime = notificationTime;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();

        Log.d("Notification", "scheduleNotification: " + medName + " " + notificationTime.getTime());
    }

    @Override
    protected Void doInBackground(Void... voids) {
        try {

            Thread.sleep(notificationTime.getTimeInMillis() - System.currentTimeMillis());

            Log.d("Notification", "Notification done for " + LocalTime.now());

            shootNotification(context, medName, medMessage);
        } catch (InterruptedException e) {
            Log.d("Notification", "doInBackground: Fail to schedule notification");
            e.printStackTrace();
        }
        return null;
    }
}