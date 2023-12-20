package com.example.kidslearn;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import Helper.BaseActivity;
import Helper.gameMenuHelper;
import Helper.sharedPref;
import Helper.userInterfaceHelper;

public class levelDifficulty extends BaseActivity {
    userInterfaceHelper UIHelper;
    gameMenuHelper gameHelper;
    ImageButton backBtn, nextDiffBtn, prevDiffBtn;
    Button lvl1, lvl2, lvl3, lvl4, lvl5;
    Button[] levelArr;
    TextView diffTxt;
    String game;
    String difficulty;
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
        levelArr = new Button[]
                {
                        lvl1, lvl2, lvl3, lvl4, lvl5
                };
        diffTxt = findViewById(R.id.diffTxt);
        prevDiffBtn.setVisibility(View.GONE);

        difficulty = gameHelper.easyDiff();
        game = gameHelper.getGame();
        Log.d("Game Type", game);
        prevDiffBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(gameHelper.getDifficulty().equals(gameHelper.hardDiff()))
                {
                    gameHelper.setDifficulty(gameHelper.mediumDiff());
                    difficulty = gameHelper.mediumDiff();
                    if(nextDiffBtn.getVisibility() == View.GONE)
                        nextDiffBtn.setVisibility(View.VISIBLE);
                }
                else if(gameHelper.getDifficulty().equals(gameHelper.mediumDiff()))
                {
                    difficulty = gameHelper.easyDiff();
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
                    difficulty = gameHelper.mediumDiff();
                    if(prevDiffBtn.getVisibility() == View.GONE)
                        prevDiffBtn.setVisibility(View.VISIBLE);
                }
                else if(gameHelper.getDifficulty().equals(gameHelper.mediumDiff()))
                {
                    difficulty = gameHelper.hardDiff();
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

        if(gameHelper.getDifficulty().equals(gameHelper.hardDiff()))
        {
            difficulty = gameHelper.hardDiff();
            prevDiffBtn.setVisibility(View.VISIBLE);
            nextDiffBtn.setVisibility(View.GONE);
        }
        else if(gameHelper.getDifficulty().equals(gameHelper.mediumDiff()))
        {
            difficulty = gameHelper.mediumDiff();
            prevDiffBtn.setVisibility(View.VISIBLE);
            nextDiffBtn.setVisibility(View.VISIBLE);
        }
        else if(gameHelper.getDifficulty().equals(gameHelper.easyDiff()))
        {
            difficulty = gameHelper.easyDiff();
            prevDiffBtn.setVisibility(View.GONE);
            nextDiffBtn.setVisibility(View.VISIBLE);
        }
    }

    void checkLevelAvailability(String diff)
    {
        for(int i = 0; i < levelArr.length; i++)
        {
            levelArr[i].setBackgroundTintList(ContextCompat.getColorStateList(this, R.color.unavail));
            levelArr[i].setOnClickListener(null);
        }

        sharedPref db = new sharedPref(this);
        int levelAvail = 1;
        int end = 5;
        int level = 0;

        if(diff.equalsIgnoreCase(gameHelper.easyDiff()))
        {
            levelAvail += 0;
            end += 0;
        }
        else if(diff.equalsIgnoreCase(gameHelper.mediumDiff()))
        {
            levelAvail += 5;
            end += 5;
        }
        else if(diff.equalsIgnoreCase(gameHelper.hardDiff()))
        {
            levelAvail += 10;
            end += 10;
        }

        if(game.equalsIgnoreCase(gameHelper.alphabet()))
        {
            level = db.getAlphabet();
        }
        else if(game.equalsIgnoreCase(gameHelper.colors()))
        {
            level = db.getColors();
        }
        else if(game.equalsIgnoreCase(gameHelper.shape()))
        {
            level = db.getShapes();
        }
        else if(game.equalsIgnoreCase(gameHelper.number()))
        {
            level = db.getNumber();
        }
        else if(game.equalsIgnoreCase(gameHelper.animal()))
        {
            level = db.getAnimal();
        }
        changeDiff();

        for(int i = levelAvail; i <= level + 1; i++) { // Change loop condition
            if(i <= end) {
                // Assuming levelArr is an array of elements that need tint updates
                if(i - levelAvail < levelArr.length) {
                    levelArr[i - levelAvail].setBackgroundTintList(ContextCompat.getColorStateList(this, R.color.peach));
                    int finalI = i;
                    int finalLevelAvail = levelAvail;
                    Log.i("Level Avail", "index: " + (i-levelAvail) + " level: " + level);
                    levelArr[i - levelAvail].setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            openLevelMenu((finalI - finalLevelAvail) + 1);
                        }
                    });
                }
            }
        }
    }
    void openLevelMenu(int level)
    {
        Log.i("Diff", gameHelper.getDifficulty());
        String gamemode = gameHelper.getGame();
        if(gamemode.equalsIgnoreCase(gameHelper.alphabet()))
        {
            if(gameHelper.getDifficulty().equalsIgnoreCase(gameHelper.easyDiff()))
            {
                startActivity(new Intent(levelDifficulty.this, alphabetEasy.class)
                        .putExtra("Level", level));
            }
            else if(gameHelper.getDifficulty().equalsIgnoreCase(gameHelper.mediumDiff()))
            {
                startActivity(new Intent(levelDifficulty.this, alphabetMedium.class)
                        .putExtra("Level", level));
            }
            else if(gameHelper.getDifficulty().equalsIgnoreCase(gameHelper.hardDiff()))
            {
                startActivity(new Intent(levelDifficulty.this, alphabetHard.class)
                        .putExtra("Level", level));
            }
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
            if(gameHelper.getDifficulty().equalsIgnoreCase(gameHelper.easyDiff()))
            {
                startActivity(new Intent(levelDifficulty.this, colorEasy.class)
                    .putExtra("Diff", gameHelper.getDifficulty())
                    .putExtra("Level", level));
            }
            else if(gameHelper.getDifficulty().equalsIgnoreCase(gameHelper.mediumDiff()))
            {
                startActivity(new Intent(levelDifficulty.this, colorMedium.class)
                        .putExtra("Diff", gameHelper.getDifficulty())
                        .putExtra("Level", level));
            }
            else if(gameHelper.getDifficulty().equalsIgnoreCase(gameHelper.hardDiff()))
            {
                startActivity(new Intent(levelDifficulty.this, colorHard.class)
                    .putExtra("Diff", gameHelper.getDifficulty())
                    .putExtra("Level", level));
            }

        }else if(gamemode.equalsIgnoreCase(gameHelper.coloring()))
        {
            startActivity(new Intent(levelDifficulty.this, coloring.class)
                    .putExtra("Diff", gameHelper.getDifficulty())
                    .putExtra("Level", level));
        }
    }
    void changeDiff()
    {
        diffTxt.setText(gameHelper.getDifficulty());
        checkLevelAvailability(gameHelper.getDifficulty());
    }
}