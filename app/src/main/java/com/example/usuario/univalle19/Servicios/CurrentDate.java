package com.example.usuario.univalle19.Servicios;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.Date;

public class CurrentDate extends Service {
    public CurrentDate() {
    }

    public String fecha;
    public SimpleDateFormat dateFormat;

    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        throw new UnsupportedOperationException("Not yet implemented");
    }

    @Override
    public void onCreate() {
        super.onCreate();
    }

    public String updateDate(){
        dateFormat = new SimpleDateFormat("HH:mm:ss");
        fecha = dateFormat.format(new Date());
        return fecha;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        for (int i=0; i<= 100; i++){
            try {
                Toast.makeText(getApplicationContext(),"Hi, today is: "+this.updateDate(),Toast.LENGTH_LONG ).show();
                Thread.sleep(500);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        stopSelf(startId);
        return super.onStartCommand(intent, flags, startId);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Toast.makeText(getApplicationContext(),"Service Destroy: ",Toast.LENGTH_LONG ).show();
    }
}
