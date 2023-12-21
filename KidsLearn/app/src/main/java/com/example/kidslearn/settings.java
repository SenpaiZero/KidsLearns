package com.example.kidslearn;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.Switch;

import Helper.BaseActivity;
import Helper.settingHelper;
import Helper.sharedPref;
import Helper.userInterfaceHelper;

public class settings extends BaseActivity {

    userInterfaceHelper UIHelper;
    sharedPref db;
    Switch music, sfx, gallery;
    ImageButton backBtn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        UIHelper = new userInterfaceHelper(this);
        UIHelper.removeActionbar();
        UIHelper.transparentStatusBar();

        db = new sharedPref(settings.this);
        music = findViewById(R.id.musicSwitch);
        sfx = findViewById(R.id.soundSwitch);
        gallery = findViewById(R.id.gallerySwitch);
        backBtn = findViewById(R.id.backBtn);

        music.setChecked(db.getMusic());
        sfx.setChecked(db.getSound());
        gallery.setChecked(db.getGallery());


        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(settings.this, parentalControl.class));
            }
        });
        music.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                db.setMusic(music.isChecked());

                if(db.getMusic())
                    startMusic();
                else
                    stopMusic();

            }
        });

        sfx.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                db.setSound(sfx.isChecked());
            }
        });

        gallery.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                db.setGallery(gallery.isChecked());
            }
        });
    }
}