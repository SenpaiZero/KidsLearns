package com.example.kidslearn;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;

import Helper.BGMusic;
import Helper.userInterfaceHelper;

public class startMenu extends AppCompatActivity {

    userInterfaceHelper UIHelper;
    ImageButton videoBtn, gameBtn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start_menu);

        UIHelper = new userInterfaceHelper(this);
        UIHelper.removeActionbar();
        UIHelper.transparentStatusBar();

        videoBtn = findViewById(R.id.videoMenuBtn);
        gameBtn = findViewById(R.id.gameMenuBtn);

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
        BGMusic.stopBG(startMenu.this);
    }

    @Override
    protected void onResume() {
        super.onResume();
        BGMusic.startBG(startMenu.this);
    }
}