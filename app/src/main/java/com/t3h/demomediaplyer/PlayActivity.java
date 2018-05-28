package com.t3h.demomediaplyer;

import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.SeekBar;
import android.widget.TextView;

import java.text.SimpleDateFormat;

import de.hdodenhof.circleimageview.CircleImageView;

public class PlayActivity extends AppCompatActivity implements View.OnClickListener{
    private SeekBar seekBar;
    private TextView tvSong;
    private TextView tvArtist;
    private TextView tvTime_run;
    private TextView tvDuration;
    private CircleImageView btnNext;
    private CircleImageView btnStop_Play;
    private CircleImageView btnPrew;
    private ImageButton btnback;
    private SimpleDateFormat formatDuration = new SimpleDateFormat("mm:ss");
    public static boolean isPlaying;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.play_layout);
        initView();
        isPlaying=true;
    }


    private void initView(){
        seekBar=(SeekBar)findViewById(R.id.seekbar);
        tvSong=(TextView)findViewById(R.id.song);
        tvArtist=(TextView)findViewById(R.id.artist);
        tvTime_run=(TextView)findViewById(R.id.time_run);
        tvDuration=(TextView)findViewById(R.id.duration);
        btnNext=(CircleImageView)findViewById(R.id.next);
        btnPrew=(CircleImageView)findViewById(R.id.preous);
        btnStop_Play=(CircleImageView)findViewById(R.id.pause);
        btnback=(ImageButton)findViewById(R.id.back);
        btnback.setOnClickListener(this);
        btnStop_Play.setOnClickListener(this);
        btnPrew.setOnClickListener(this);
        btnNext.setOnClickListener(this);
    }


    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.pause:
                if(isPlaying){
                    MainActivity.service.pause();
                    btnStop_Play.setImageResource(R.drawable.ic_play_arrow_white_24dp);
                    PlayActivity.isPlaying=false;
                }
                else {
                    MainActivity.service.play(ServiceMediaOffline.CURRENT_POSITON);
                    btnStop_Play.setImageResource(R.drawable.ic_pause_white_24dp);
                    PlayActivity.isPlaying=true;
                }
                break;
            case R.id.next:
                ServiceMediaOffline.CURRENT_POSITON++;
                MainActivity.service.stop();
                MainActivity.service.release();
                if(ServiceMediaOffline.CURRENT_POSITON==MainActivity.service.getAudioOfflines().size()-1){
                ServiceMediaOffline.CURRENT_POSITON--;
            }
                MainActivity.service.play(ServiceMediaOffline.CURRENT_POSITON);
                break;
            case R.id.preous:
                ServiceMediaOffline.CURRENT_POSITON--;
                MainActivity.service.stop();
                MainActivity.service.release();
                if(ServiceMediaOffline.CURRENT_POSITON<0){
                    ServiceMediaOffline.CURRENT_POSITON++;
               }
                MainActivity.service.play(ServiceMediaOffline.CURRENT_POSITON);
                break;
            case R.id.back:
                Intent intent =new Intent();
                intent.setClass(PlayActivity.this,MainActivity.class);
                startActivity(intent);
                finish();
                break;
        }
    }
    private AsyncTask<Void,Void,Void> asyncTask = new AsyncTask<Void, Void, Void>() {

        @Override
        protected Void doInBackground(Void... voids) {
            while (true){
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                publishProgress();
            }
        }

        @Override
        protected void onProgressUpdate(Void... values) {
            super.onProgressUpdate(values);
            int durationmax= (int) MainActivity.service.getAudioOfflines().get(ServiceMediaOffline.CURRENT_POSITON).getDuration();
            AudioOffline curentsong=MainActivity.service.getAudioOfflines().get(ServiceMediaOffline.CURRENT_POSITON);
//            seekBar.setMax(durationmax);
            tvDuration.setText(formatDuration.format(durationmax));
            seekBar.setProgress(MainActivity.service.getCurrentPosition());
            tvTime_run.setText(formatDuration.format(MainActivity.service.getCurrentPosition()));
            tvSong.setText(MainActivity.service.getAudioOfflines().get(ServiceMediaOffline.CURRENT_POSITON).getDisplayName());
            tvArtist.setText(MainActivity.service.getAudioOfflines().get(ServiceMediaOffline.CURRENT_POSITON).getArtis());
        }

    }.execute();


}
