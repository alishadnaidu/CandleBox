package com.example.candlebox.SpotifyStuff;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.candlebox.R;
import com.example.candlebox.Song;

import org.parceler.Parcels;

import java.util.ArrayList;

public class SongsAdapter extends RecyclerView.Adapter<SongsAdapter.Viewholder> {

    private Context context;
    private ArrayList<Song> songList;

    public SongsAdapter(Context context, ArrayList<Song> songList) {
        this.context = context;
        this.songList = songList;
    }

    @NonNull
    @Override
    public SongsAdapter.Viewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.activity_cardview, parent, false);
        return new Viewholder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull SongsAdapter.Viewholder holder, int position) {
        Song song = songList.get(position);
        holder.tvSongName.setText(song.getSongName() + " by " + song.getArtist());
        holder.ivSongImage.setImageResource(song.getSongCover());
    }

    @Override
    public int getItemCount() {
        return songList.size();
    }

    public class Viewholder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private ImageView ivSongImage;
        private TextView tvSongName;

        public Viewholder(@NonNull View itemView) {
            super(itemView);
            ivSongImage = itemView.findViewById(R.id.ivSongImage);
            tvSongName = itemView.findViewById(R.id.tvSongName);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            int position = getAdapterPosition();
            if (position != RecyclerView.NO_POSITION) {
                Song song = songList.get(position);
                Intent intent = new Intent(context, SongDetailActivity.class);
                intent.putExtra(Song.class.getSimpleName(), Parcels.wrap(song));
                context.startActivity(intent);
            }
        }
    }
}