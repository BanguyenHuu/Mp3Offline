package com.t3h.demomediaplyer;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.text.SimpleDateFormat;

/**
 * Created by Lap trinh on 3/7/2018.
 */

public class AudioAdapter extends
        RecyclerView.Adapter<AudioAdapter.AudioViewHolder> {
    private IAudioAdapter inter;
    private SimpleDateFormat formatDuration = new SimpleDateFormat("mm:ss");
    private SimpleDateFormat formatDate = new SimpleDateFormat("dd/MM/yyyy");

    public AudioAdapter(IAudioAdapter inter) {
        this.inter = inter;
    }

    @Override
    public AudioViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        return new AudioViewHolder(inflater.
                inflate(R.layout.item_audio, parent, false));
    }

    @Override
    public void onBindViewHolder(AudioViewHolder holder, final int position) {
        AudioOffline audioOffline = inter.getItem(position);
        holder.tvArtis.setText(audioOffline.getArtis());
        holder.tvName.setText(audioOffline.getDisplayName());
        holder.tvDate.setText(formatDate.format(audioOffline.getDateCreated()));
        holder.tvDuration.setText(formatDuration.format(audioOffline.getDuration()));
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                inter.onClickItem(position);
            }
        });
    }

    @Override
    public int getItemCount() {
        return inter.getCount();
    }

    static final class AudioViewHolder extends RecyclerView.ViewHolder {
        private TextView tvName;
        private TextView tvDuration;
        private TextView tvArtis;
        private TextView tvDate;

        public AudioViewHolder(View itemView) {
            super(itemView);
            tvArtis = itemView.findViewById(R.id.tv_artis);
            tvDuration = itemView.findViewById(R.id.tv_duration);
            tvName = itemView.findViewById(R.id.tv_name);
            tvDate = itemView.findViewById(R.id.tv_date);
        }
    }

    public interface IAudioAdapter {
        int getCount();

        AudioOffline getItem(int position);


        void onClickItem(int position);
    }
}
