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

public class alphabetHard extends AppCompatActivity implements View.OnTouchListener{

    String[] fullWord = {"CAT", "TEN", "HAT", "DOG", "SUN"};
    TextView[] choices;
    TextView[] answers;
    String[][] letters =
            {
                    {"T", "C", "A"},
                    {"E", "T", "N"},
                    {"T", "A", "H"},
                    {"O", "G", "D"},
                    {"U", "S", "N"}
            };
    int[][] indexAnswers =
            {
                    {1, 2, 0},
                    {1, 0, 2},
                    {2, 1, 0},
                    {2, 0, 1},
                    {1, 0, 2}
            };

    boolean[] gameFinish = {false, false, false};
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
        setContentView(R.layout.activity_alphabet_hard);

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
                SoundHelper sfx = new SoundHelper(alphabetHard.this, R.raw.time_out, false);

            }
        });

        choices = new TextView[]
                {
                        findViewById(R.id.one),
                        findViewById(R.id.two),
                        findViewById(R.id.three)
                };

        answers = new TextView[]
                {
                        findViewById(R.id.secondLetter),
                        findViewById(R.id.thirdLetter),
                        findViewById(R.id.fourthLetter)
                };

        for (int i = 0; i < choices.length; i++)
        {
            choices[i].setText(letters[levelIndex][i]);
            choices[i].setOnTouchListener(this);
        }

        Bitmap[] img =
                {
                        BitmapFactory.decodeResource(this.getResources(), R.drawable.cat),
                        BitmapFactory.decodeResource(this.getResources(), R.drawable.number_10),
                        BitmapFactory.decodeResource(this.getResources(), R.drawable.medium2_1),
                        BitmapFactory.decodeResource(this.getResources(), R.drawable.dog),
                        BitmapFactory.decodeResource(this.getResources(), R.drawable.sunny)
                };

        ImageView image = findViewById(R.id.img);
        image.setImageBitmap(img[levelIndex]);

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
                for(int i = 0; i < indexAnswers[levelIndex].length; i++)
                {
                    if (isViewOverlapping(choices[indexAnswers[levelIndex][i]], answers[i])) {
                    // Snap the detailsImageView to the blankImageView
                    snapToTarget(choices[indexAnswers[levelIndex][i]], answers[i]);
                    gameFinish[i] = true;
                    choices[indexAnswers[levelIndex][i]].setOnTouchListener(null);
                    if(areAllTrue(gameFinish))
                    {
                        Log.d("Game shape easy", "Finished level " + level);
                        popup.showNextLevel();
                        timer.cancelTimer();

                        SoundHelper sfx = new SoundHelper(alphabetHard.this, R.raw.level_complete, false);
                    }
                }
            }
            break;
        }

        return true;
    }
    public boolean areAllTrue(boolean[] array)
    {
        boolean isGood = false;
        for(int i = 0; i < array.length; i++)
        {
            if(array[i] == true)
            {
                isGood = true;
            }
            else
            {
                return false;
            }
        }
        return isGood;
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