package com.example.kidslearn;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;

import Helper.BaseActivity;
import Helper.VideoActivity;
import Helper.userInterfaceHelper;

public class storyType extends VideoActivity {

    ImageButton gooseBtn, donkeyBtn, frogBtn, pigBtn, catFoxBtn;
    ImageView backBtn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_story_type);

        userInterfaceHelper UIHelper = new userInterfaceHelper(this);
        UIHelper.removeActionbar();
        UIHelper.transparentStatusBar();

        backBtn = findViewById(R.id.backBtn);
        gooseBtn = findViewById(R.id.gooseBtn);
        donkeyBtn = findViewById(R.id.donkeyHorseBtn);
        frogBtn = findViewById(R.id.frogPrinceBtn);
        pigBtn = findViewById(R.id.threePigsBtn);
        catFoxBtn = findViewById(R.id.catFoxBtn);

        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(storyType.this, startMenu.class));
            }
        });

        gooseBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startVideo("the_goose_that_laid_golden_egg");
            }
        });

        catFoxBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startVideo("the_cat_and_the_fox");
            }
        });

        donkeyBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startVideo("the_donkey_and_the_horse");
            }
        });

        frogBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startVideo("the_frog_prince");
            }
        });

        pigBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startVideo("three_little_pigs");
            }
        });
    }
    void startVideo(String videoType)
    {
        startActivity(new Intent(storyType.this, watchVideo.class)
                .putExtra("Type", videoType));
    }
}