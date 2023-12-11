package com.example.kidslearn;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.MediaController;
import android.widget.TextView;
import android.widget.VideoView;

import Helper.userInterfaceHelper;

public class watchVideo extends AppCompatActivity {

    private VideoView videoView;
    private TextView quitBtn;
    private MediaController mediaController;
    private boolean isPlaying = false;
    String type;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_watch_video);

        userInterfaceHelper UIHelper = new userInterfaceHelper(this);
        UIHelper.removeActionbar();
        UIHelper.transparentStatusBar();
        UIHelper.setFullscreen();

        videoView = findViewById(R.id.videoView);

        type = getIntent().getStringExtra("Type");
        mediaController = new MediaController(this);
        mediaController.setAnchorView(videoView);
        videoView.setMediaController(mediaController);

        String videoPath = "android.resource://" + getPackageName() + "/raw/" + type;
        videoView.setVideoURI(Uri.parse(videoPath));
        videoView.start();

        quitBtn = findViewById(R.id.backBtn);
        quitBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(watchVideo.this, videoType.class));
            }
        });

    }
}