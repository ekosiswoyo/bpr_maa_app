package com.bprmaa.mobiles;

import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.bprmaa.mobiles.App.ApiConfig;
import com.bprmaa.mobiles.App.ApiService;
import com.google.android.youtube.player.YouTubeBaseActivity;
import com.google.android.youtube.player.YouTubeInitializationResult;
import com.google.android.youtube.player.YouTubePlayer;
import com.google.android.youtube.player.YouTubePlayerView;

public class TestActivity extends YouTubeBaseActivity {

    ApiService api = ApiConfig.getInstanceRetrofit();

    public static final String API_KEY = "AIzaSyCsjhsD-3-Dp4PZ1ImcDgPJJYnASTR7V14";
    public static final String VIDEO_ID = "aJ7BoNG-r2c";

    YouTubePlayerView youTube;
    YouTubePlayer.OnInitializedListener onInitializedListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test);

        youTube = findViewById(R.id.youtube);
        onInitializedListener = new YouTubePlayer.OnInitializedListener() {
            @Override
            public void onInitializationSuccess(YouTubePlayer.Provider provider, YouTubePlayer youTubePlayer, boolean b) {
                Log.d("Respons", "Berhasil Get Video");
                youTubePlayer.loadVideo("3dCkn8eYTtE");
            }

            @Override
            public void onInitializationFailure(YouTubePlayer.Provider provider, YouTubeInitializationResult youTubeInitializationResult) {
                Toast.makeText(TestActivity.this, "Gagal Memuat Video", Toast.LENGTH_SHORT).show();
            }
        };

        youTube = findViewById(R.id.youtube);
        youTube.initialize(API_KEY, onInitializedListener);


    }

}
