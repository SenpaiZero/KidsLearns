package com.example.kidslearn;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;

import Helper.BaseActivity;
import Helper.userInterfaceHelper;

public class videoType extends BaseActivity {

    userInterfaceHelper UIHelper;
    ImageButton alphabet, number, shape, animal, color;
    ImageView backBtn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_video_type);

        UIHelper = new userInterfaceHelper(this);
        UIHelper.removeActionbar();
        UIHelper.transparentStatusBar();

        alphabet = findViewById(R.id.alphabetsBtn);
        number = findViewById(R.id.numbersBtn);
        color = findViewById(R.id.colorsBtn);
        shape = findViewById(R.id.shapesBtn);
        animal = findViewById(R.id.animalBtn);

        backBtn = findViewById(R.id.backBtn);

        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(videoType.this, startMenu.class));
            }
        });
        alphabet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startVideo("alphabet");
            }
        });

        number.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startVideo("number");
            }
        });

        color.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startVideo("color");
            }
        });

        animal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startVideo("animal");
            }
        });

        shape.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startVideo("shape");
            }
        });
    }

    void startVideo(String videoType)
    {
        startActivity(new Intent(videoType.this, watchVideo.class)
                .putExtra("Type", videoType));
    }
}