package com.t3h.demomediaplyer;

import android.content.Context;
import android.database.Cursor;
import android.provider.MediaStore;
import android.util.Log;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by Lap trinh on 3/7/2018.
 */

public class AudioManager {
    private static final String TAG = AudioManager.class.getSimpleName();
    private Context context;
    private List<AudioOffline> audioOfflines =
            new ArrayList<>();

    public AudioManager(Context context) {
        this.context = context;
    }

    public void getAllListAudio() {
        //pa1: duong duong cua bang can query
        //pa2: la cac cot can lay, neu null thi lay het
        //par3 va pa4: menh de where
        //pare 5: kieu sap sep
        Cursor c =
                context.getContentResolver().query(
                        MediaStore.Audio.Media.EXTERNAL_CONTENT_URI,
                        null, null, null, null
                );
        if (c == null) {
            return;
        }
        //lay cac ten cot trong bang ra
        String[] columns = c.getColumnNames();
        for (String column : columns) {
            Log.d(TAG, "getAllListAudio column: " + column);
        }
        c.moveToFirst();
        int indexId = c.getColumnIndex("_id");
        int indexData = c.getColumnIndex("_data");
        int indexDisplayName = c.getColumnIndex("_display_name");
        int indexMineType = c.getColumnIndex("mime_type");
        int indexDateAdd = c.getColumnIndex("date_added");
        int indexAlbumId = c.getColumnIndex("album_id");
        int indexDuration = c.getColumnIndex("duration");
        while (!c.isAfterLast()){
            long id = c.getLong(indexId);
            String data = c.getString(indexData);
            String displayName = c.getString(indexDisplayName);
            String mineType = c.getString(indexMineType);
            long dateAdd = c.getLong(indexDateAdd); //giay
            long albumId = c.getLong(indexAlbumId);
            long duration = c.getLong(indexDuration);
            AudioOffline audioOffline = new AudioOffline();
            audioOffline.setId(id);
            audioOffline.setPath(data);
            audioOffline.setDisplayName(displayName);
            audioOffline.setMineType(mineType);
            audioOffline.setDateCreated(
                    new Date(dateAdd*1000)
            );
            audioOffline.setAlbumId(albumId);
            audioOffline.setDuration(duration);
            audioOfflines.add(audioOffline);
            c.moveToNext();
        }
        c.close();

    }

    public List<AudioOffline> getAudioOfflines() {
        return audioOfflines;
    }

    public void setAudioOfflines(List<AudioOffline> audioOfflines) {
        this.audioOfflines = audioOfflines;
    }
}
