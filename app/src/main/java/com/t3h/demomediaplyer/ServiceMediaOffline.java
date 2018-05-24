package com.t3h.demomediaplyer;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.os.Binder;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.NotificationManagerCompat;

import java.io.IOException;
import java.util.List;

/**
 * Created by Administrator on 4/11/2018.
 */

public class ServiceMediaOffline extends Service{
    private ManagerMediaplayer managerMediaplayer;
    private AudioManager audioManager;

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {//lắng nghe yêu cầu kết nối từ các đt khác
        return new MyBinder(this);//activity nhận đc kết quả
    }

    @Override
    public void onCreate() {

        super.onCreate();
        managerMediaplayer = new ManagerMediaplayer();
        audioManager = new AudioManager(this);
        audioManager.getAllListAudio();


    }

    public List<AudioOffline> getAudioOfflines(){
        return audioManager.getAudioOfflines();
    }

    public void play(int position){

        try {
            managerMediaplayer.setDataSource(audioManager.getAudioOfflines().get(position).getPath());
        } catch (IOException e) {
            e.printStackTrace();
        }
        managerMediaplayer.play();

    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {

        return START_NOT_STICKY;
    }

    public static class MyBinder extends Binder{
        private ServiceMediaOffline service;
        public MyBinder(ServiceMediaOffline service) {
            this.service = service;
        }

        public ServiceMediaOffline getService() {
            return service;
        }
    }

    public  void createNotifycation(int position){
        Intent intentPending=new Intent();
        intentPending.setClass(this,MainActivity.class);
        intentPending.putExtra("ID",1);
        PendingIntent pendingIntent =
                //PendingIntent.getActivities(this,100,intentPending,PendingIntent.FLAG_UPDATE_CURRENT);
                PendingIntent.getActivity(this,100,intentPending,PendingIntent.FLAG_UPDATE_CURRENT);
        AudioOffline date= audioManager.getAudioOfflines().get(position);
        NotificationCompat.Builder builder=new NotificationCompat.Builder(this);
        builder.setSmallIcon(R.drawable.ic_arrow_back_white_24dp);
        builder.setContentTitle(date.getDisplayName());
        builder.setLargeIcon(
                BitmapFactory.decodeResource(getResources(),R.mipmap.ic_launcher));
        NotificationManager conn=(NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
       // builder.setContent(pendingIntent);
        builder.setContentIntent(pendingIntent);
        conn.notify(1,builder.build());
        startForeground(1,builder.build());
    }


    @Override
    public void onDestroy() {
        super.onDestroy();
        managerMediaplayer.release();
        managerMediaplayer=null;


    }
}
