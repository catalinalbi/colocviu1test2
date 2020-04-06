package ro.pub.cs.systems.eim.practicaltest01;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.IBinder;
import android.util.Log;

import androidx.annotation.Nullable;

import java.util.Date;

public class PracticalTest01Service extends Service {
    private int startMode;
    private double medieAritmetica, medieGeometrica;

    @Override
    public void onCreate() {
        super.onCreate();
        // ...
    }

    @Override
    public int onStartCommand(Intent intent,
                              int flags,
                              int startId) {
        // ...
        medieAritmetica = intent.getDoubleExtra("medieAritmetica", 0);
        medieGeometrica = intent.getDoubleExtra("medieGeometrica", 0);
        ProcessingThread thread = new ProcessingThread(this, medieAritmetica, medieAritmetica);
        thread.start();
        return startMode;
    }

    @Override
    public IBinder onBind(Intent intent) {
        // ...
        return null;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        // ...
    }

    private class ProcessingThread extends Thread {

        Context context;
        private double medieAritmetica, medieGeometrica;
        boolean run;

        public ProcessingThread(Context context, double medieAritmetica, double medieGeometrica) {
            this.medieAritmetica = medieAritmetica;
            this.medieGeometrica = medieGeometrica;
            this.context = context;
            run = true;
        }

        public void run() {
            while (run){
                SendMessage(Constants.DATE);
                Log.v("send message", "date sent");
                sleep();
                SendMessage(Constants.MEAN1);
                sleep();
                SendMessage(Constants.MEAN2);
                sleep();
            }
        }

        private void sleep() {
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        private void SendMessage(int message) {
            Intent intent = new Intent();
            intent.setAction(Constants.SEND_DATA);

            switch (message) {
                case Constants.DATE:
                    intent.putExtra(Constants.DATA, java.text.DateFormat.getDateTimeInstance().format(new Date()));
                    break;
                case Constants.MEAN1:
                    intent.putExtra(Constants.DATA, ((Double)medieAritmetica).toString());
                    break;
                case Constants.MEAN2:
                    intent.putExtra(Constants.DATA, ((Double)medieGeometrica).toString());
                    break;
            }

            context.sendBroadcast(intent);
        }

        public void StopService() {
            run = false;
        }
    }
}
