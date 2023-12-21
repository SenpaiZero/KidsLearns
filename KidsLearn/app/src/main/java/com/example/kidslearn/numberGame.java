package com.example.kidslearn;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import Helper.BaseActivity;
import Helper.GameActivity;
import Helper.LevelPopupHelper;
import Helper.SoundHelper;
import Helper.TimerHelper;
import Helper.gameMenuHelper;
import Helper.settingHelper;
import Helper.userInterfaceHelper;

public class numberGame extends GameActivity {
    userInterfaceHelper UIHelper;
    gameMenuHelper gameHelper;

    Button[] answersBtn;
    int[][][] choices;
    int[] answers;
    int level;
    TextView questionTxt;
    String[] questions;
    String difficulty;
    ImageView questionImg;
    int userAnswer, levelIndex;
    TimerHelper timer;
    LevelPopupHelper popup;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_number_game);

        UIHelper = new userInterfaceHelper(this);
        UIHelper.removeActionbar();
        UIHelper.transparentStatusBar();

        gameHelper = new gameMenuHelper();
        level = getIntent().getIntExtra("Level", 1);
        difficulty = gameHelper.getDifficulty();

        String info = gameHelper.getDifficulty() + "\n" + level;
        TextView infoTxt = findViewById(R.id.infoTxt);
        infoTxt.setText(info);
        ProgressBar progressBar = findViewById(R.id.progressBar);
        questionImg = findViewById(R.id.imageQuestion);
        timer = new TimerHelper(30000, 1000);

        popup = new LevelPopupHelper(this);
        timer.setOnTimerTickListener(new TimerHelper.OnTimerTickListener() {
            @Override
            public void onTick(long secondsRemaining) {
                int progress = (int) secondsRemaining;
                Drawable drawable = getResources().getDrawable(R.drawable.hourglass);
                drawable.setLevel((int) (progress * 1000 / progressBar.getMax()));
                progressBar.setProgress(progress);
            }
        });
        timer.setOnTimerFinishedListener(new TimerHelper.OnTimerFinishedListener() {
            @Override
            public void onTimerFinished() {
                popup.showTimeout();
                SoundHelper sfx = new SoundHelper(numberGame.this, R.raw.time_out, false);

            }
        });

        ImageButton backBtn;
        backBtn = findViewById(R.id.backBtn);
        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(numberGame.this, levelDifficulty.class));
            }
        });
        questionTxt = findViewById(R.id.questionTxt);
        choices = new int[][][]
                {
                        {{4, 5, 1}}, // ans is 1
                        {{3, 6, 9}}, // ans is 3
                        {{10, 5, 15}}, // ans is 5
                        {{4, 6, 8}}, // ans is 4
                        {{7, 2, 5}}, // ans is 7
                        {{1, 6, 5}}, // ans is 6
                        {{8, 5, 1}}, // ans is 5
                        {{4, 6, 2}}, // ans is 2
                        {{6, 8, 4}}, // ans is 6
                        {{12, 18, 9}}, // ans is 18
                        {{15, 10, 5}}, //ans is 10
                        {{3, 6, 2}}, //ans is 3
                        {{4, 6, 9}}, //ans is 9
                        {{5, 3, 7}}, //ans is 7
                        {{10, 5, 6}} //ans is 5
                };
        Bitmap[] images =
                {
                        BitmapFactory.decodeResource(this.getResources(), R.drawable.number1),
                        BitmapFactory.decodeResource(this.getResources(), R.drawable.number2),
                        BitmapFactory.decodeResource(this.getResources(), R.drawable.number3),
                        BitmapFactory.decodeResource(this.getResources(), R.drawable.number4),
                        BitmapFactory.decodeResource(this.getResources(), R.drawable.number5),
                        BitmapFactory.decodeResource(this.getResources(), R.drawable.number6),
                        BitmapFactory.decodeResource(this.getResources(), R.drawable.number7),
                        BitmapFactory.decodeResource(this.getResources(), R.drawable.number8),
                        BitmapFactory.decodeResource(this.getResources(), R.drawable.number9),
                        BitmapFactory.decodeResource(this.getResources(), R.drawable.number10)

                };
        answers = new int[] {1, 3, 5, 4, 7, 6, 5, 2, 6, 18, 10, 3, 9, 7, 5};

        answersBtn = new Button[] {
                findViewById(R.id.ans1),
                findViewById(R.id.ans2),
                findViewById(R.id.ans3)
        };

        questions = new String[]
                {
                  "5 + 5",
                  "2 + 1",
                  "4 + 5",
                  "5 + 2",
                  "3 + 2"
                };
        int addLevelIndex = 0;
        if(difficulty.equalsIgnoreCase(gameHelper.easyDiff()))
        {
            Log.i("Diff", gameHelper.getDifficulty());
            addLevelIndex = 0;
            levelIndex = (level - 1) + addLevelIndex;
            questionImg.setImageBitmap(images[levelIndex]);
            questionImg.setVisibility(View.VISIBLE);
            questionTxt.setVisibility(View.GONE);
        }
        else if(difficulty.equalsIgnoreCase(gameHelper.mediumDiff()))
        {
            Log.i("Diff", gameHelper.getDifficulty());
            addLevelIndex = 5;
            levelIndex = (level - 1) + addLevelIndex;
            questionImg.setImageBitmap(images[levelIndex]);
            questionImg.setVisibility(View.VISIBLE);
            questionTxt.setVisibility(View.GONE);
        }else if(difficulty.equalsIgnoreCase(gameHelper.hardDiff()))
        {
            Log.i("Diff", gameHelper.getDifficulty() + levelIndex);
            addLevelIndex = 10;
            levelIndex = (level - 1) + addLevelIndex;
            questionTxt.setText(questions[level-1]);
            questionImg.setVisibility(View.GONE);
            questionTxt.setVisibility(View.VISIBLE);
        }
        answersBtn[0].setText(String.valueOf(choices[levelIndex][0][0]));
        answersBtn[1].setText(String.valueOf(choices[levelIndex][0][1]));
        answersBtn[2].setText(String.valueOf(choices[levelIndex][0][2]));

        answersBtn[0].setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                userAnswer = choices[levelIndex][0][0];
                Log.i("Btn1", "clicked: "+userAnswer );
                checkAnswer();
            }
        });
        answersBtn[1].setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                userAnswer = choices[levelIndex][0][1];
                Log.i("Btn2", "clicked: "+userAnswer );
                checkAnswer();
            }
        });
        answersBtn[2].setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                userAnswer = choices[levelIndex][0][2];
                Log.i("Btn3", "clicked: "+userAnswer );
                checkAnswer();
            }
        });
    }

    void checkAnswer()
    {
        Log.i("Btn", "ans " + levelIndex);
        if(userAnswer == answers[levelIndex])
        {
            SoundHelper sfx = new SoundHelper(numberGame.this, R.raw.level_complete, false);

            increaseLevel(level, "number");
            popup.showNextLevel();
            timer.cancelTimer();
        }
    }
    @Override
    protected void onPause() {
        super.onPause();
        timer.cancelTimer();
    }
    @Override
    protected void onDestroy() {
        super.onDestroy();
        // Stop the timer when the Activity is destroyed
        timer.cancelTimer();
    }
    @Override
    protected  void onResume() {
        super.onResume();
        timer.resumeTimer();
    }
}