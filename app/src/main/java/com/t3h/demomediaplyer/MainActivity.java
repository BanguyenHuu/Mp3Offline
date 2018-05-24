package com.t3h.demomediaplyer;

import android.app.NotificationManager;
import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.IBinder;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import java.io.IOException;

public class MainActivity extends AppCompatActivity implements AudioAdapter.IAudioAdapter {
    private RecyclerView rcAudio;
    private AudioAdapter adapter;
    private ServiceMediaOffline service;

    private void initService(){
        Intent intentService=new Intent();
        intentService.setClass(this,ServiceMediaOffline.class);
        startService(intentService);
        ServiceConnection conn=new ServiceConnection() {
            @Override
            public void onServiceConnected(ComponentName componentName, IBinder iBinder) {
                ServiceMediaOffline.MyBinder binder=(ServiceMediaOffline.MyBinder) iBinder;
                        service=binder.getService();
                        adapter.notifyDataSetChanged();
            }
            @Override
            public void onServiceDisconnected(ComponentName componentName) {
            }
        };
        Intent intent=new Intent();
        intent.setClass(this,ServiceMediaOffline.class);
        bindService(intent,conn,BIND_AUTO_CREATE);//intent chaỵ đến serviceMedia - > chạy onBinhd()
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        rcAudio = (RecyclerView) findViewById(R.id.rc_audio);
        adapter = new AudioAdapter(this);
        rcAudio.setLayoutManager(new LinearLayoutManager(this));
        rcAudio.setAdapter(adapter);
        initService();
    }


    @Override
    public int getCount() {
//        if (audioManager.getAudioOfflines() == null) {
//            return 0;
//        }

        if(service==null){
            return 0;
        }
        return service.getAudioOfflines().size();
    }

    @Override
    public AudioOffline getItem(int position) {
        return service.getAudioOfflines().get(position);
    }

    @Override
    public void onClickItem(int position) {

//        try {
//            managerMediaplayer.setDataSource(getItem(position).getPath());
//            managerMediaplayer.play();
//        } catch (IOException e) {
//            e.printStackTrace();
//        }

        service.play(position);

        service.createNotifycation(position);

    }

    @Override
    protected void onDestroy() {
        //managerMediaplayer.release();
        super.onDestroy();
    }

    @Override
    protected void onNewIntent(Intent intent) {

        int idNotification =intent.getIntExtra("ID",0);

        NotificationManager manager=(NotificationManager)getSystemService(NOTIFICATION_SERVICE);

        manager.cancel(idNotification);


    }
}
