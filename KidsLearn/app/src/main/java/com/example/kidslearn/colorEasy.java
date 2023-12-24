package com.example.kidslearn;

import androidx.cardview.widget.CardView;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.constraintlayout.widget.ConstraintSet;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.VideoView;

import Helper.BaseActivity;
import Helper.GameActivity;
import Helper.LevelPopupHelper;
import Helper.SoundHelper;
import Helper.TimerHelper;
import Helper.gameMenuHelper;
import Helper.settingHelper;
import Helper.userInterfaceHelper;

public class colorEasy extends GameActivity {

    TimerHelper timer;
    LevelPopupHelper popup;
    userInterfaceHelper UIHelper;
    gameMenuHelper gameHelper;
    String difficulty;
    int level;
    int[] correctIndex;
    ImageView[] choices;
    ImageView color;
    CardView colorBorder;
    boolean input1, input2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_color_easy);

        UIHelper = new userInterfaceHelper(this);
        UIHelper.removeActionbar();
        UIHelper.transparentStatusBar();

        popup = new LevelPopupHelper(this);
        gameHelper = new gameMenuHelper();
        level = getIntent().getIntExtra("Level", 1);
        difficulty = gameHelper.getDifficulty();

        String info = gameHelper.getDifficulty() + "\n" + level;
        TextView infoTxt = findViewById(R.id.infoTxt);
        infoTxt.setText(info);

        ImageButton backBtn = findViewById(R.id.backBtn);
        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(colorEasy.this, levelDifficulty.class));
            }
        });
        colorBorder = findViewById(R.id.color1_1_1);
        color = findViewById(R.id.color1);
        choices = new ImageView[]
                {
                        findViewById(R.id.color2),
                        findViewById(R.id.color3)
                };


        Bitmap[] colorCorrect =
                {
                        BitmapFactory.decodeResource(this.getResources(), R.drawable.color_easy1),
                        BitmapFactory.decodeResource(this.getResources(), R.drawable.color_easy2),
                        BitmapFactory.decodeResource(this.getResources(), R.drawable.colorr_easy3),
                        BitmapFactory.decodeResource(this.getResources(), R.drawable.colorr_easy4_1),
                        BitmapFactory.decodeResource(this.getResources(), R.drawable.colorr_easy5_1),
                };
        Bitmap[] colorIncorrect =
                {
                        BitmapFactory.decodeResource(this.getResources(), R.drawable.color_easy1_1),
                        BitmapFactory.decodeResource(this.getResources(), R.drawable.colorr_easy2_1),
                        BitmapFactory.decodeResource(this.getResources(), R.drawable.colorr_easy3_1),
                        BitmapFactory.decodeResource(this.getResources(), R.drawable.colorr_easy4),
                        BitmapFactory.decodeResource(this.getResources(), R.drawable.colorr_easy5),
                };
        int[] colors = new int[]
                {
                        getResources().getColor(R.color.easyColor1),
                        getResources().getColor(R.color.easyColor2),
                        getResources().getColor(R.color.easyColor3),
                        getResources().getColor(R.color.easyColor4),
                        getResources().getColor(R.color.easyColor5)
                };

        color.getBackground().setColorFilter(colors[level - 1], PorterDuff.Mode.SRC_IN);

        choices[0].setImageBitmap(colorCorrect[level-1]);
        choices[1].setImageBitmap(colorIncorrect[level-1]);
        correctIndex = new int[]
                {
                    1, 0, 0, 1, 1
                };

        Log.i("Check", correctIndex[level-1] + "");
        color.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                input1 = true;
                colorBorder.setContentPadding(10,10,10,10);
            }
        });

        choices[0].setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                input2 = true;
                checkCorrect(0);
            }
        });

        choices[1].setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                input2 = true;
                checkCorrect(1);
            }
        });
        Button skipTutBtn = findViewById(R.id.skipBtn);
        VideoView videoView = findViewById(R.id.tutVid);
        ConstraintLayout tutPopup = findViewById(R.id.tutorial);

        if(level <= 1)
        {
            tutPopup.setZ(90);
            tutPopup.setTranslationZ(90);
            tutPopup.setVisibility(View.VISIBLE);

            String videoPath = "android.resource://" + getPackageName() + "/raw/" + "colors_lvl1";
            videoView.setVideoURI(Uri.parse(videoPath));
            videoView.start();
            videoView.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
                @Override
                public void onPrepared(MediaPlayer mp) {
                    mp.setLooping(true);
                }
            });
        }
        else
        {
            setupTimer();
        }
        skipTutBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                tutPopup.setVisibility(View.GONE);
                setupTimer();
            }
        });
    }

    void setupTimer()
    {
        ProgressBar progressBar = findViewById(R.id.progressBar);
        timer = new TimerHelper(30000, 1000);

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
                SoundHelper sfx = new SoundHelper(colorEasy.this, R.raw.time_out, false);
            }
        });
    }

    void checkCorrect(int whatBtn)
    {
        if(input1 && input2)
        {
            if(whatBtn == correctIndex[level-1])
            {
                if(whatBtn == 0){
                    Log.i("Check", "triggered");
                    setLine(whatBtn);
                }
                else if(whatBtn == 1) {
                    Log.i("Check", "triggered1");
                    setLine(whatBtn);
                }
                SoundHelper sfx = new SoundHelper(this, R.raw.level_complete, false);

                increaseLevel(level, "color");
                popup.showNextLevel();
                timer.cancelTimer();
            }
            else
            {
                incorrect();
            }
        }
    }
    void incorrect()
    {
        colorBorder.setContentPadding(0,0,0,0);
        input1 = false;
        input2 = false;
    }

    void setLine(int whatBtn)
    {
        // Assuming you have references to imageView1, imageView2, and the parent layout
        // Get the coordinates of the ImageViews
        int[] location1 = new int[2];
        int[] location2 = new int[2];
        color.getLocationOnScreen(location1);
        choices[whatBtn].getLocationOnScreen(location2);

        // Calculate the midpoint between the two ImageViews
        float x1 = location1[0] + color.getWidth() / 2;
        float y1 = location1[1] + color.getHeight() / 2;
        float x2 = location2[0] + choices[whatBtn].getWidth() / 2;
        float y2 = location2[1] + choices[whatBtn].getHeight() / 2;

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
        lineView.setElevation(color.getElevation() - 1); // Adjust elevation as needed

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
        if(timer != null)
            timer.cancelTimer();
    }
    @Override
    protected void onDestroy() {
        super.onDestroy();
        if(timer != null)
            timer.cancelTimer();
    }
    @Override
    protected  void onResume() {
        super.onResume();
        if(timer != null)
            timer.resumeTimer();

    }
}