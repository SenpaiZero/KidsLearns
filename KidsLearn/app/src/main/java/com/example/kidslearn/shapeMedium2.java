package com.example.kidslearn;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.media.Image;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import Helper.LevelPopupHelper;
import Helper.MusicServiceBackgroundNormal;
import Helper.SoundHelper;
import Helper.TimerHelper;
import Helper.gameMenuHelper;
import Helper.userInterfaceHelper;

public class shapeMedium2 extends AppCompatActivity implements View.OnTouchListener{

    userInterfaceHelper UIHelper;
    gameMenuHelper gameHelper;
    int previousX, previousY, level, shapeCount;
    boolean[] shapesDone;
    ImageView[] shapes;
    ImageView[] blankShapes;
    LevelPopupHelper popup;
    TimerHelper timer;
    SoundHelper bgMusic;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shape_medium2);

        UIHelper = new userInterfaceHelper(this);
        UIHelper.removeActionbar();
        UIHelper.transparentStatusBar();
        bgMusic = new SoundHelper(this, R.raw.play_game_music_bg, true);
        stopService(new Intent(this, MusicServiceBackgroundNormal.class));

        popup = new LevelPopupHelper(this);
        gameHelper = new gameMenuHelper();
        level = getIntent().getIntExtra("Level", 1);

        String info = gameHelper.getDifficulty() + "\n" + level;
        TextView infoTxt = findViewById(R.id.infoTxt);
        infoTxt.setText(info);
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
                SoundHelper sfx = new SoundHelper(shapeMedium2.this, R.raw.time_out, false);

            }
        });
        shapes = new ImageView[]
                {
                        findViewById(R.id.shape1),
                        findViewById(R.id.shape2)
                };
        blankShapes = new ImageView[]
                {
                        findViewById(R.id.blank1),
                        findViewById(R.id.blank2)
                };

        shapeCount = shapes.length;
        shapesDone = new boolean[shapeCount];
        for (int i = 0; i < shapeCount; i++)
        {
            shapesDone[i] = false;
            shapes[i].setOnTouchListener(this);
        }

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
                for (int i = 0; i < shapeCount; i++)
                {
                    // Check for overlap and snap if needed
                    if (isViewOverlapping(shapes[i], blankShapes[i])) {
                        // Snap the detailsImageView to the blankImageView
                        snapToTarget(shapes[i], blankShapes[i]);

                        shapesDone[i] = true;

                        if(areAllTrue(shapesDone))
                        {
                            Log.d("Game shape medium", "Finished level " + level);
                            SoundHelper sfx = new SoundHelper(shapeMedium2.this, R.raw.level_complete, false);

                            popup.showNextLevel();
                            timer.cancelTimer();
                        }
                    }
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
        rect1.inset(100, 100);
        rect2.inset(100, 100);

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

    public boolean areAllTrue(boolean[] array)
    {
        boolean isGood = false;
        for(int i = 0; i < shapes.length; i++)
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