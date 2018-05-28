package com.t3h.demomediaplyer;

import android.media.MediaPlayer;

import java.io.IOException;

/**
 * Created by Lap trinh on 3/7/2018.
 */

public class ManagerMediaplayer {
    private MediaPlayer mediaPlayer;

    public ManagerMediaplayer() {

    }

    public void setDataSource(String path)
            throws IOException {
        if (mediaPlayer != null) {
            mediaPlayer.release();
        }
        mediaPlayer = new MediaPlayer();
        mediaPlayer.setDataSource(path);
        mediaPlayer.prepare();
    }

    public void play() {
        if (mediaPlayer != null){
            mediaPlayer.start();
        }
    }
    public int getCurrentPosition(){
        if (mediaPlayer!=null){
            return mediaPlayer.getCurrentPosition();
        }
        return 0;
    }

    public void pause() {
        if (mediaPlayer != null && mediaPlayer.isPlaying()) {
            mediaPlayer.pause();
        }
    }

    public void stop() {
        if (mediaPlayer != null && mediaPlayer.isPlaying()) {
            mediaPlayer.stop();
        }
    }

    public void release() {
        if (mediaPlayer == null) {
            return;
        }
        mediaPlayer.release();
        mediaPlayer = null;
    }
}
