package com.example.kidslearn;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;

import Helper.BaseActivity;
import Helper.settingHelper;
import Helper.userInterfaceHelper;

public class aboutGame extends BaseActivity {

    ImageButton backBtn;
    userInterfaceHelper UIHelper;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about_game);
        UIHelper = new userInterfaceHelper(this);
        UIHelper.removeActionbar();
        UIHelper.transparentStatusBar();
        backBtn = findViewById(R.id.imageButton2);
        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(aboutGame.this, parentalControl.class));
            }
        });
    }
}