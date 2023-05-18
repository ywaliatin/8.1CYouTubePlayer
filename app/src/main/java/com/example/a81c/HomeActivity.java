package com.example.a81c;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.YouTubePlayer;
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.listeners.AbstractYouTubePlayerListener;
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.views.YouTubePlayerView;

public class HomeActivity extends AppCompatActivity {
    private Button buttonPlay;
    private String videoId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.home_activity);

        YouTubePlayerView youTubePlayerView = findViewById(R.id.player);
        getLifecycle().addObserver(youTubePlayerView);

        youTubePlayerView.addYouTubePlayerListener(new AbstractYouTubePlayerListener() {
            @Override
            public void onReady(@NonNull YouTubePlayer youTubePlayer) {
                if (videoId != null) {
                    youTubePlayer.loadVideo(videoId, 0);
                }
            }
        });

        Intent intent = getIntent();
        if (intent != null) {
            videoId = intent.getStringExtra("videoId");
        }

        //buttonPlay = findViewById(R.id.buttonPlay);

        //Intent intent = getIntent();
        //if (intent != null) {
        //    videoId = intent.getStringExtra("videoId");
       // }

        //buttonPlay.setOnClickListener(new View.OnClickListener() {
         //   @Override
        //    public void onClick(View v) {
         //       if (videoId != null) {
        //            playVideo(videoId);
        //        }
        //    }
       // });
    }



    // Method to play the YouTube video using the extracted video ID
    private void playVideo(String videoId) {
        String videoUrl = "https://www.youtube.com/watch?v=" + videoId;
        Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(videoUrl));
        startActivity(intent);
    }
}
