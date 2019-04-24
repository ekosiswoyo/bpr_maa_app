package com.bprmaa.mobiles.Activity;

import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.bprmaa.mobiles.R;
import com.google.android.youtube.player.YouTubeBaseActivity;
import com.google.android.youtube.player.YouTubeInitializationResult;
import com.google.android.youtube.player.YouTubePlayer;
import com.google.android.youtube.player.YouTubePlayerView;

public class PlayVideoActivity extends YouTubeBaseActivity {
    public static final String API_KEY = "AIzaSyCsjhsD-3-Dp4PZ1ImcDgPJJYnASTR7V14";

    YouTubePlayerView youTube;
    YouTubePlayer.OnInitializedListener onInitializedListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_play_video);

        final String id_video = getIntent().getStringExtra("id");
        Log.d("Id Vidoe", id_video);

        youTube = findViewById(R.id.youtube);
        onInitializedListener = new YouTubePlayer.OnInitializedListener() {
            @Override
            public void onInitializationSuccess(YouTubePlayer.Provider provider, YouTubePlayer youTubePlayer, boolean b) {
                Log.d("Respons", "Berhasil Get Video");
                youTubePlayer.loadVideo(id_video);
            }

            @Override
            public void onInitializationFailure(YouTubePlayer.Provider provider, YouTubeInitializationResult youTubeInitializationResult) {
                Toast.makeText(PlayVideoActivity.this, "Gagal Memuat Video", Toast.LENGTH_SHORT).show();
            }
        };

        youTube = findViewById(R.id.youtube);
        youTube.initialize(API_KEY, onInitializedListener);
    }
}
