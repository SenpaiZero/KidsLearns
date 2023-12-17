package com.example.kidslearn;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import Helper.userInterfaceHelper;

public class parentalControl extends AppCompatActivity {

    userInterfaceHelper UIHelper;
    Button playtimeBtn, achievementBtn, worksheetBtn, settingBtn, aboutBtn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_parental_control);

        UIHelper = new userInterfaceHelper(this);
        UIHelper.removeActionbar();
        UIHelper.transparentStatusBar();

        settingBtn = findViewById(R.id.settingBtn);

        settingBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(parentalControl.this, settings.class));
            }
        });
    }
}