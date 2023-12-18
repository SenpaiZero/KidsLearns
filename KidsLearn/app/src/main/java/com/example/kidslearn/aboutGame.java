package com.example.kidslearn;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;

import Helper.BaseActivity;

public class aboutGame extends BaseActivity {

    ImageButton backBtn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about_game);

        backBtn = findViewById(R.id.imageButton2);
        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(aboutGame.this, parentalControl.class));
            }
        });
    }
}