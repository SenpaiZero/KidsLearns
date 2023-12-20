package com.example.kidslearn;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.constraintlayout.widget.ConstraintSet;
import androidx.core.content.ContextCompat;
import androidx.core.view.ViewCompat;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import java.util.Arrays;

import Helper.GameActivity;
import Helper.LevelPopupHelper;
import Helper.MusicServiceBackgroundNormal;
import Helper.SoundHelper;
import Helper.TimerHelper;
import Helper.gameMenuHelper;
import Helper.userInterfaceHelper;

public class colorMedium extends GameActivity {

    TimerHelper timer;
    LevelPopupHelper popup;
    userInterfaceHelper UIHelper;
    gameMenuHelper gameHelper;
    String difficulty;
    int level;
    int[][][] correctIndex;
    ImageView[] choices;
    ImageView[] color;
    CardView[] border;
    boolean input1, input2;
    int colorIndex, choiceIndex, lineCount = 0;
    SoundHelper bgMusic;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_color_medium);
        UIHelper = new userInterfaceHelper(this);
        UIHelper.removeActionbar();
        UIHelper.transparentStatusBar();
        bgMusic = new SoundHelper(this, R.raw.play_game_music_bg, true);
        stopService(new Intent(this, MusicServiceBackgroundNormal.class));

        gameHelper = new gameMenuHelper();
        level = getIntent().getIntExtra("Level", 1);
        difficulty = gameHelper.getDifficulty();

        String info = gameHelper.getDifficulty() + "\n" + level;
        TextView infoTxt = findViewById(R.id.infoTxt);
        infoTxt.setText(info);
        ProgressBar progressBar = findViewById(R.id.progressBar);
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
                SoundHelper sfx = new SoundHelper(colorMedium.this, R.raw.time_out, false);

            }
        });

        ImageButton backBtn = findViewById(R.id.backBtn);
        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(colorMedium.this, levelDifficulty.class));
            }
        });
        border = new CardView[]
                {
                      findViewById(R.id.color1_1_1),
                        findViewById(R.id.color2_2_2),
                        findViewById(R.id.color3_3_3)
                };
        color = new ImageView[]{
                findViewById(R.id.color1),
                findViewById(R.id.color2),
                findViewById(R.id.color3)
        };
        choices = new ImageView[]
                {
                        findViewById(R.id.color1_1),
                        findViewById(R.id.color2_1),
                        findViewById(R.id.color3_1)
                };


        Bitmap[][] colorCorrect =
                {
                        {
                                BitmapFactory.decodeResource(this.getResources(), R.drawable.medium1_1),
                                BitmapFactory.decodeResource(this.getResources(), R.drawable.medium1_2),
                                BitmapFactory.decodeResource(this.getResources(), R.drawable.medium1_3)
                        },
                        {
                                BitmapFactory.decodeResource(this.getResources(), R.drawable.medium2_1),
                                BitmapFactory.decodeResource(this.getResources(), R.drawable.medium2_2),
                                BitmapFactory.decodeResource(this.getResources(), R.drawable.medium2_3)
                        },
                        {
                                BitmapFactory.decodeResource(this.getResources(), R.drawable.medium3_1),
                                BitmapFactory.decodeResource(this.getResources(), R.drawable.medium3_2),
                                BitmapFactory.decodeResource(this.getResources(), R.drawable.medium3_3)
                        },
                        {
                                BitmapFactory.decodeResource(this.getResources(), R.drawable.medium4_1),
                                BitmapFactory.decodeResource(this.getResources(), R.drawable.medium4_2),
                                BitmapFactory.decodeResource(this.getResources(), R.drawable.medium4_3)
                        },
                        {
                                BitmapFactory.decodeResource(this.getResources(), R.drawable.medium5_1),
                                BitmapFactory.decodeResource(this.getResources(), R.drawable.medium5_2),
                                BitmapFactory.decodeResource(this.getResources(), R.drawable.medium5_3)
                        }
                };
        int[][] colors = new int[][]
                {
                        {
                                getResources().getColor(R.color.mediumColor1_3),
                                getResources().getColor(R.color.mediumColor1_1),
                                getResources().getColor(R.color.mediumColor1_2)
                        },
                        {
                                getResources().getColor(R.color.mediumColor2_1),
                                getResources().getColor(R.color.mediumColor2_2),
                                getResources().getColor(R.color.mediumColor2_3)
                         },
                        {
                                getResources().getColor(R.color.mediumColor3_2),
                                getResources().getColor(R.color.mediumColor3_1),
                                getResources().getColor(R.color.mediumColor3_3)
                        },
                        {
                                getResources().getColor(R.color.mediumColor4_1),
                                getResources().getColor(R.color.mediumColor4_3),
                                getResources().getColor(R.color.mediumColor4_2)
                        },
                        {
                                getResources().getColor(R.color.mediumColor5_3),
                                getResources().getColor(R.color.mediumColor5_1),
                                getResources().getColor(R.color.mediumColor5_2)
                        },

                };

        for (int i = 0; i < color.length; i++)
        {
            color[i].getBackground().setColorFilter(colors[level - 1][i], PorterDuff.Mode.SRC_IN);
            choices[i].setImageBitmap(colorCorrect[level-1][i]);

            int finalI = i;
            color[i].setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    input1 = true;
                    addPadding(border[finalI]);
                    colorIndex = Arrays.asList(color).indexOf(v);
                }
            });
        }

        correctIndex = new int[][][]
                {
                        //btn1
                        {{0,2}, {1,0}, {2,1}},//level
                        {{0,0}, {1,1}, {2,2}},
                        {{0,1}, {1,0}, {2,2}},
                        {{0,0}, {1,2}, {2,1}},
                        {{0,2}, {1,0}, {2,1}}
                };

        for (int i = 0; i < choices.length; i++)
        {
            int finalI = i;
            choices[i].setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                input2 = true;
                choiceIndex = finalI;
                checkCorrect();
            }
        });
        }
    }

    void checkCorrect() {
        if (input1 && input2) {
            int index1 = correctIndex[level-1][colorIndex][0];
            int index2 = correctIndex[level-1][colorIndex][1];

            if(index1 == colorIndex
                    && index2 == choiceIndex) {
                Log.i("Check", "triggered");
                lineCount++;
                setLine(index1, index2);
                correctIndex[level-1][colorIndex][0] = 500;
                if(lineCount >= 3)
                {
                    SoundHelper sfx = new SoundHelper(colorMedium.this, R.raw.level_complete, false);

                    increaseLevel(level, "color");
                    popup.showNextLevel();
                    timer.cancelTimer();
                }
            } else {
                incorrect();
            }

            // Reset input flags and indices for the next interaction
            input1 = false;
            input2 = false;
        }
    }
    void incorrect()
    {
        addPadding(null);
        input1 = false;
        input2 = false;
    }

    void addPadding(CardView card)
    {
        for (int i = 0; i < border.length; i++)
        {
            if(correctIndex[level-1][i][0] != 500)
                border[i].setContentPadding(0,0,0,0);
        }
        if(card != null)
            card.setContentPadding(10,10,10,10);
    }

    void setLine(int colorIndex_, int choiceIndex_)
    {
        int[] location1 = new int[2];
        int[] location2 = new int[2];
        color[correctIndex[level-1][colorIndex][0]].getLocationOnScreen(location1);
        choices[correctIndex[level-1][colorIndex][1]].getLocationOnScreen(location2);

        // Calculate the midpoint between the two ImageViews
        float x1 = location1[0] + color[colorIndex_].getWidth() / 2;
        float y1 = location1[1] + color[colorIndex_].getHeight() / 2;
        float x2 = location2[0] + choices[choiceIndex_].getWidth() / 2;
        float y2 = location2[1] + choices[choiceIndex_].getHeight() / 2;

        // Create a View (lineView) and set its properties
        View lineView = new View(this);
        lineView.setBackgroundColor(Color.BLACK); // Set line color
        // Set the width and height of the line (adjust according to your preference)
        int width = (int) Math.sqrt(Math.pow(x2 - x1, 2) + Math.pow(y2 - y1, 2));
        lineView.setLayoutParams(new ViewGroup.LayoutParams(width, 5)); // Adjust height and width
        // Calculate angle between two points
        double angle = Math.atan2(y2 - y1, x2 - x1) * 180 / Math.PI;
        lineView.setRotation((float) angle); // Set the rotation angle of the line
        lineView.setId(View.generateViewId());

        ConstraintLayout parentLayout = findViewById(R.id.cons);

        // Set the elevation to place lineView underneath the ImageViews
        lineView.setElevation(color[colorIndex_].getElevation() - 1); // Adjust elevation as needed

        // Add the lineView to the parent layout
        parentLayout.addView(lineView);

        ConstraintSet set = new ConstraintSet();
        set.clone(parentLayout);

        // Calculate the center coordinates between the ImageViews
        float centerX = (x1 + x2) / 2;
        float centerY = (y1 + y2) / 2;

        // Calculate the width and height of the line
        width = (int) Math.sqrt(Math.pow(x2 - x1, 2) + Math.pow(y2 - y1, 2));
        int height = 5; // Set your desired height

        // Connect the lineView underneath the ImageViews at the center position
        set.connect(lineView.getId(), ConstraintSet.START, ConstraintSet.PARENT_ID, ConstraintSet.START, (int) centerX - width / 2);
        set.connect(lineView.getId(), ConstraintSet.TOP, ConstraintSet.PARENT_ID, ConstraintSet.TOP, (int) centerY - height / 2);

        // Set the width and height of the line
        lineView.getLayoutParams().width = width;
        lineView.getLayoutParams().height = height;

        set.applyTo(parentLayout);
    }
    @Override
    protected void onPause() {
        super.onPause();
        timer.cancelTimer();
        bgMusic.pause();
    }
    @Override
    protected void onDestroy() {
        super.onDestroy();
        // Stop the timer when the Activity is destroyed
        timer.cancelTimer();
        bgMusic.releaseMediaPlayer();
    }
    @Override
    protected  void onResume() {
        super.onResume();
        timer.resumeTimer();
        bgMusic.resume();
    }
}