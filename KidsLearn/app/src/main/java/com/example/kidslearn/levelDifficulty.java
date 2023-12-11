package com.example.kidslearn;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import Helper.gameMenuHelper;
import Helper.userInterfaceHelper;

public class levelDifficulty extends AppCompatActivity {
    userInterfaceHelper UIHelper;
    gameMenuHelper gameHelper;
    ImageButton backBtn, nextDiffBtn, prevDiffBtn;
    Button lvl1, lvl2, lvl3, lvl4, lvl5;
    TextView diffTxt;
    String game;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_level_difficulty);

        UIHelper = new userInterfaceHelper(this);
        UIHelper.removeActionbar();
        UIHelper.transparentStatusBar();

        gameHelper = new gameMenuHelper();

        nextDiffBtn = findViewById(R.id.nextDiffBtn);
        prevDiffBtn = findViewById(R.id.prevDiffBtn);
        backBtn = findViewById(R.id.backBtn);
        lvl1 = findViewById(R.id.lvlOneBtn);
        lvl2 = findViewById(R.id.lvlTwoBtn);
        lvl3 = findViewById(R.id.lvlThreeBtn);
        lvl4 = findViewById(R.id.lvlFourBtn);
        lvl5 = findViewById(R.id.lvlFiveBtn);

        diffTxt = findViewById(R.id.diffTxt);
        prevDiffBtn.setVisibility(View.GONE);

        game = gameHelper.getGame();
        Log.d("Game Type", game);
        changeDiff();


        prevDiffBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(gameHelper.getDifficulty().equals(gameHelper.hardDiff()))
                {
                    gameHelper.setDifficulty(gameHelper.mediumDiff());
                    if(nextDiffBtn.getVisibility() == View.GONE)
                        nextDiffBtn.setVisibility(View.VISIBLE);
                }
                else if(gameHelper.getDifficulty().equals(gameHelper.mediumDiff()))
                {
                    gameHelper.setDifficulty(gameHelper.easyDiff());
                    prevDiffBtn.setVisibility(View.GONE);
                }
                changeDiff();
            }
        });

        nextDiffBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(gameHelper.getDifficulty().equals(gameHelper.easyDiff()))
                {
                    gameHelper.setDifficulty(gameHelper.mediumDiff());
                    if(prevDiffBtn.getVisibility() == View.GONE)
                        prevDiffBtn.setVisibility(View.VISIBLE);
                }
                else if(gameHelper.getDifficulty().equals(gameHelper.mediumDiff()))
                {
                    gameHelper.setDifficulty(gameHelper.hardDiff());
                    nextDiffBtn.setVisibility(View.GONE);
                }
                changeDiff();
            }
        });

        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(levelDifficulty.this, gameType.class));
            }
        });

        lvl1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openLevelMenu(1);
            }
        });

        lvl2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openLevelMenu(2);
            }
        });
        lvl3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openLevelMenu(3);
            }
        });
        lvl4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openLevelMenu(4);
            }
        });
        lvl5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openLevelMenu(5);
            }
        });
    }

    void openLevelMenu(int level)
    {
        Log.i("Diff", gameHelper.getDifficulty());
        String gamemode = gameHelper.getGame();
        String difficulty = gameHelper.getDifficulty();
        if(gamemode.equalsIgnoreCase(gameHelper.alphabet()))
        {

        }
        else if(gamemode.equalsIgnoreCase(gameHelper.number()))
        {
            startActivity(new Intent(levelDifficulty.this, numberGame.class)
                    .putExtra("Level", level));
        } else if(gamemode.equalsIgnoreCase(gameHelper.shape()))
        {
            if(gameHelper.getDifficulty().equalsIgnoreCase(gameHelper.easyDiff()))
            {
                startActivity(new Intent(levelDifficulty.this, shapeEasy.class)
                        .putExtra("Level", level));
            }
            else if(gameHelper.getDifficulty().equalsIgnoreCase(gameHelper.mediumDiff()))
            {
                if(level == 1)
                    startActivity(new Intent(levelDifficulty.this, shapeMedium1.class)
                            .putExtra("Level", level));
                else if(level == 2)
                    startActivity(new Intent(levelDifficulty.this, shapeMedium2.class)
                            .putExtra("Level", level));
                else if(level == 3)
                    startActivity(new Intent(levelDifficulty.this, shapeMedium3.class)
                            .putExtra("Level", level));
                else if(level == 4)
                    startActivity(new Intent(levelDifficulty.this, shapeMedium4.class)
                            .putExtra("Level", level));
                else if(level == 5)
                    startActivity(new Intent(levelDifficulty.this, shapeMedium5.class)
                            .putExtra("Level", level));
            }
            else if(gameHelper.getDifficulty().equalsIgnoreCase(gameHelper.hardDiff()))
            {
                startActivity(new Intent(levelDifficulty.this, shapeHard.class)
                        .putExtra("Level", level));
            }
        }else if(gamemode.equalsIgnoreCase(gameHelper.animal()))
        {
            startActivity(new Intent(levelDifficulty.this, gameAnimal.class)
                    .putExtra("Diff", gameHelper.getDifficulty())
                    .putExtra("Level", level));
        }else if(gamemode.equalsIgnoreCase(gameHelper.colors()))
        {

        }
    }
    void changeDiff()
    {
        diffTxt.setText(gameHelper.getDifficulty());
    }
}