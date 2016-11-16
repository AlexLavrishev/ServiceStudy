package com.example.shadow.servicestudy;

import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.util.Log;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

import static android.content.ContentValues.TAG;

/**
 * Created by shadow on 15/11/16.
 */

public class MyService extends Service {
    @Nullable
    ExecutorService es;
    public void onCreate(){
        super.onCreate();
        Log.i(TAG, "onCreate: ");
        es = Executors.newFixedThreadPool(8);
    }

    public int onStartCommand(Intent intent, int flags, int startId){
        Log.i(TAG, "onStartCommand: ");

        PendingIntent pi = intent.getParcelableExtra(MainActivity.PARAM_PINTENT);

        MyRun mr = new MyRun( startId, pi);

        es.execute(mr);

        return super.onStartCommand(intent, flags, startId);

    }
    public  void onDestroy(){
        Log.i(TAG, "onDestroy: ");
        super.onDestroy();
    }
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    class MyRun implements Runnable{
        int startId;
        PendingIntent pi;

        public MyRun(int startId, PendingIntent pi) {
            this.startId = startId;
            this.pi = pi;
        }

        @Override
        public void run() {
            Log.i(TAG, "run: ");
            try {
                // сообщаем об старте задачи
                pi.send(MainActivity.STATUS_START);
                // начинаем выполнение задачи
                TimeUnit.SECONDS.sleep(1);
                // сообщаем об окончании задачи
                Intent intent = new Intent()
                        .putExtra("Key1", "Value1" )
                        .putExtra("Key2", "Value2" )
                        .putExtra("Key3", "Value3" )
                        .putExtra("Key4", "Value4" );
                pi.send(MyService.this, MainActivity.STATUS_FINISH, intent);
            } catch (InterruptedException e) {
                e.printStackTrace();
            } catch (PendingIntent.CanceledException e) {
                e.printStackTrace();
            }

        }

    }
}
