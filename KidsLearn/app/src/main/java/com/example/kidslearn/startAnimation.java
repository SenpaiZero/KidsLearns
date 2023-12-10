package com.example.kidslearn;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.VideoView;

import Helper.TimerHelper;
import Helper.userInterfaceHelper;

public class startAnimation extends AppCompatActivity {

    TextView skipBtn;
    userInterfaceHelper UIHelper;
    ProgressBar progressBar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start_animation);

        UIHelper = new userInterfaceHelper(this, true);
        UIHelper.removeActionbar();
        UIHelper.transparentStatusBar();
        VideoView videoView = findViewById(R.id.videoView);
        String videoPath = "android.resource://" + getPackageName() + "/" + R.raw.animation_start;
        videoView.setVideoURI(Uri.parse(videoPath));
        videoView.start();
        skipBtn = findViewById(R.id.skipBtn);

        skipBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(startAnimation.this, StartGame.class));
                overridePendingTransition(R.anim.fadein, R.anim.fadeout);
                finish();
            }
        });

        progressBar = findViewById(R.id.progressBar);
        TimerHelper timer = new TimerHelper(22000, 1000);

        timer.setOnTimerTickListener(new TimerHelper.OnTimerTickListener() {
            @Override
            public void onTick(long secondsRemaining) {
                int progress = (int) secondsRemaining;
                Drawable drawable = getResources().getDrawable(R.drawable.hourglass);
                drawable.setLevel((int) (progress * 1000 / progressBar.getMax()));
                progressBar.setProgress(progress);
            }
        });
        timer.setOnTimerFinishedListener(new TimerHelper.OnTimerFinishedListener() {
            @Override
            public void onTimerFinished() {
                startActivity(new Intent(startAnimation.this, StartGame.class));
                overridePendingTransition(R.anim.fadein, R.anim.fadeout);
                finish();
            }
        });
    }
}