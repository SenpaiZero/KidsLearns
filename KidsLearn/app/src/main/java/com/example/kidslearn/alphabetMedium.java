package com.example.kidslearn;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import Helper.LevelPopupHelper;
import Helper.MusicServiceBackgroundNormal;
import Helper.SoundHelper;
import Helper.TimerHelper;
import Helper.gameMenuHelper;
import Helper.userInterfaceHelper;

public class alphabetMedium extends AppCompatActivity implements View.OnTouchListener {

    String[] fullWord = {"DOG", "CAT", "CAR", "PHONE", "FAIRY"};
    TextView[] choices;
    TextView[] answers;
    String[][] letters =
            {
                    {"D", "K", "L", "G", "O", "U", "W"},
                    {"A", "C", "W", "T", "G", "B", "M"},
                    {"B", "Q", "A", "G", "T", "R", "C"},
                    {"E", "V", "N", "X", "O", "P", "H"},
                    {"F", "R", "I", "A", "Y", "E", "O"}
            };
    int[] indexAnswers ={0, 1, 6, 5, 0 };


    Bitmap[] images;
    TimerHelper timer;
    LevelPopupHelper popup;
    userInterfaceHelper UIHelper;
    gameMenuHelper gameHelper;
    SoundHelper bgMusic;
    String difficulty;
    int level, levelIndex, previousX, previousY;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_alphabet_medium);

        UIHelper = new userInterfaceHelper(this);
        UIHelper.removeActionbar();
        UIHelper.transparentStatusBar();
        bgMusic = new SoundHelper(this, R.raw.play_game_music_bg, true);
        stopService(new Intent(this, MusicServiceBackgroundNormal.class));

        gameHelper = new gameMenuHelper();
        level = getIntent().getIntExtra("Level", 1);
        levelIndex = level-1;
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
                SoundHelper sfx = new SoundHelper(alphabetMedium.this, R.raw.time_out, false);

            }
        });

        images = new Bitmap[]
                {
                        BitmapFactory.decodeResource(this.getResources(), R.drawable.dog),
                        BitmapFactory.decodeResource(this.getResources(), R.drawable.cat),
                        BitmapFactory.decodeResource(this.getResources(), R.drawable.car),
                        BitmapFactory.decodeResource(this.getResources(), R.drawable.smartphone),
                        BitmapFactory.decodeResource(this.getResources(), R.drawable.fairy)
                };
        choices = new TextView[]
                {
                    findViewById(R.id.one),
                    findViewById(R.id.two),
                    findViewById(R.id.three),
                    findViewById(R.id.four),
                    findViewById(R.id.five),
                    findViewById(R.id.six),
                    findViewById(R.id.seven)
                };

        answers = new TextView[]
                {
                    findViewById(R.id.firstLetter),
                    findViewById(R.id.secondLetter),
                    findViewById(R.id.thirdLetter),
                    findViewById(R.id.fourthLetter),
                    findViewById(R.id.fifthLetter)
                };

        if(level < 4)
        {
            answers[0].setVisibility(View.GONE);
            answers[4].setVisibility(View.GONE);
            answers = new TextView[]
                    {
                            findViewById(R.id.secondLetter),
                            findViewById(R.id.thirdLetter),
                            findViewById(R.id.fourthLetter)
                    };
        }

        for (int i = 0; i < choices.length; i++)
        {
                choices[i].setText(letters[levelIndex][i] + "");
                choices[i].setOnTouchListener(this);
        }
        for (int i = 1; i < answers.length; i++)
        {
            answers[i].setText(fullWord[levelIndex].charAt(i) + "");
        }

        ImageView img = findViewById(R.id.img);
        img.setImageBitmap(images[levelIndex]);
    }
    @SuppressLint("ClickableViewAccessibility")
    @Override
    public boolean onTouch(View view, MotionEvent event) {
        int x = (int) event.getRawX();
        int y = (int) event.getRawY();

        switch (event.getAction() & MotionEvent.ACTION_MASK) {
            case MotionEvent.ACTION_DOWN:
                // Save initial touch coordinates
                previousX = x;
                previousY = y;
                break;

            case MotionEvent.ACTION_MOVE:
                // Calculate distance to move the ImageView
                int dx = x - previousX;
                int dy = y - previousY;

                // Update the ImageView position
                int newX = view.getLeft() + dx;
                int newY = view.getTop() + dy;
                int newRight = newX + view.getWidth();
                int newBottom = newY + view.getHeight();

                // Move the ImageView within the parent boundaries
                if (newX > 0 && newRight < ((View) view.getParent()).getWidth()) {
                    view.layout(newX, newY, newRight, newBottom);
                }

                // Update previous touch coordinates
                previousX = x;
                previousY = y;
                break;
            case MotionEvent.ACTION_UP:
                // Check for overlap and snap if needed
                if (isViewOverlapping(choices[indexAnswers[levelIndex]], answers[0])) {
                    // Snap the detailsImageView to the blankImageView
                    snapToTarget(choices[indexAnswers[levelIndex]], answers[0]);
                    Log.d("Game shape easy", "Finished level " + level);
                    popup.showNextLevel();
                    timer.cancelTimer();

                    SoundHelper sfx = new SoundHelper(alphabetMedium.this, R.raw.level_complete, false);
                }
                break;

        }

        return true;
    }

    private boolean isViewOverlapping(View view1, View view2) {
        Rect rect1 = new Rect();
        view1.getHitRect(rect1);

        Rect rect2 = new Rect();
        view2.getHitRect(rect2);

        // Apply clearance to the rects for detection
        rect1.inset(20, 20);
        rect2.inset(20, 20);

        boolean isOverlapping = rect1.intersect(rect2);
        Log.d("Overlap", "Overlap: " + isOverlapping);
        return isOverlapping;
    }

    private void snapToTarget(View viewToMove, View targetView) {
        // Set the position of viewToMove to match targetView
        viewToMove.setX(targetView.getX());
        viewToMove.setY(targetView.getY());

        // You can perform additional actions as needed upon snapping
        // For example, hide the view or perform specific logic
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