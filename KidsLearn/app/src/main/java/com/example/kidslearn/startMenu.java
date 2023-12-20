package com.example.kidslearn;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;

import Helper.BaseActivity;
import Helper.NeutralActivity;
import Helper.userInterfaceHelper;

public class startMenu extends NeutralActivity {

    userInterfaceHelper UIHelper;
    ImageButton videoBtn, gameBtn, storyBtn, backBtn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start_menu);

        UIHelper = new userInterfaceHelper(this);
        UIHelper.removeActionbar();
        UIHelper.transparentStatusBar();

        videoBtn = findViewById(R.id.videoMenuBtn);
        gameBtn = findViewById(R.id.gameMenuBtn);
        backBtn = findViewById(R.id.backBtn);
        storyBtn = findViewById(R.id.storyBtn);

        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(startMenu.this, StartGame.class));
            }
        });

        storyBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(startMenu.this, storyType.class));
            }
        });
        videoBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(startMenu.this, videoType.class));
            }
        });

        gameBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(startMenu.this, gameType.class));
            }
        });
    }

    @Override
    protected void onPause() {
        super.onPause();
    }

    @Override
    protected void onResume() {
        super.onResume();
    }
}