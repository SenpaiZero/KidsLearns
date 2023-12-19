package com.example.kidslearn;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;

import Helper.BaseActivity;
import Helper.gameMenuHelper;
import Helper.userInterfaceHelper;

public class gameType extends BaseActivity {

    userInterfaceHelper UIHelper;
    gameMenuHelper gameHelper;
    ImageButton alphabet, number, shape, animal, color, coloring;
    ImageView backBtn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game_type);

        gameHelper = new gameMenuHelper();
        UIHelper = new userInterfaceHelper(this);
        UIHelper.removeActionbar();
        UIHelper.transparentStatusBar();

        alphabet = findViewById(R.id.alphabetsBtn);
        number = findViewById(R.id.numbersBtn);
        color = findViewById(R.id.colorsBtn);
        shape = findViewById(R.id.shapesBtn);
        animal = findViewById(R.id.animalBtn);
        coloring = findViewById(R.id.coloringBookBtn);
        backBtn = findViewById(R.id.backBtn);

        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(gameType.this, startMenu.class));
            }
        });
        alphabet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openGame(gameHelper.alphabet());
            }
        });

        number.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openGame(gameHelper.number());
            }
        });

        color.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openGame(gameHelper.colors());
            }
        });

        animal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openGame(gameHelper.animal());
            }
        });

        shape.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openGame(gameHelper.shape());
            }
        });

        coloring.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                gameHelper.setGame(gameHelper.coloring());
                startActivity(new Intent(gameType.this, coloringMenu.class));
            }
        });
    }

    void openGame(String game)
    {
        gameHelper.setGame(game);
        startActivity(new Intent(gameType.this, levelDifficulty.class));
    }
}