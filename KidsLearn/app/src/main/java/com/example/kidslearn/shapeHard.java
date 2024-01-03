package com.example.kidslearn;

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
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import java.util.Random;

import Helper.BaseActivity;
import Helper.GameActivity;
import Helper.LevelPopupHelper;
import Helper.SoundHelper;
import Helper.TimerHelper;
import Helper.gameMenuHelper;
import Helper.settingHelper;
import Helper.userInterfaceHelper;

public class shapeHard extends GameActivity implements View.OnTouchListener{
    userInterfaceHelper UIHelper;
    gameMenuHelper gameHelper;
    int previousX, previousY, level, shapeCount;
    ImageView[] shape;
    ImageView blankShape;
    TextView infoTxt;
    TimerHelper timer;
    LevelPopupHelper popup;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shape_hard);
        UIHelper = new userInterfaceHelper(this);
        UIHelper.removeActionbar();
        UIHelper.transparentStatusBar();

        popup = new LevelPopupHelper(this);
        gameHelper = new gameMenuHelper();
        level = getIntent().getIntExtra("Level", 1);
        String info = gameHelper.getDifficulty() + "\n" + level;
        infoTxt = findViewById(R.id.infoTxt);
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
                SoundHelper sfx = new SoundHelper(shapeHard.this, R.raw.time_out, false);

                popup.showTimeout();
            }
        });

        ImageButton backBtn;
        backBtn = findViewById(R.id.backBtn);
        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(shapeHard.this, levelDifficulty.class));
            }
        });
        shape = new ImageView[] {
                findViewById(R.id.shape1),
                findViewById(R.id.shape2),
                findViewById(R.id.shape3)
        };
        blankShape = findViewById(R.id.blank1);

        Bitmap[] shapes =
                {
                        BitmapFactory.decodeResource(this.getResources(), R.drawable.beach_ball),
                        BitmapFactory.decodeResource(this.getResources(), R.drawable.chess_board),
                        BitmapFactory.decodeResource(this.getResources(), R.drawable.notebook),
                        BitmapFactory.decodeResource(this.getResources(), R.drawable.dollar),
                        BitmapFactory.decodeResource(this.getResources(), R.drawable.box),
                        BitmapFactory.decodeResource(this.getResources(), R.drawable.car),
                        BitmapFactory.decodeResource(this.getResources(), R.drawable.home),
                        BitmapFactory.decodeResource(this.getResources(), R.drawable.tree)
                };


        int levelIndex = level - 1;
        Random rand = new Random();
        shapeCount = shapes.length;
        shape[0].setImageBitmap(shapes[levelIndex]);
        blankShape.setImageBitmap(shapes[levelIndex]);

        shape[0].setOnTouchListener(this);
        shape[1].setOnTouchListener(this);
        shape[2].setOnTouchListener(this);

        for (int i = 1; i < shape.length; i++)
        {
            int randIndex = rand.nextInt(shapeCount);
            while (randIndex == levelIndex) {
                randIndex = rand.nextInt(shapeCount);
            }
            shape[i].setImageBitmap(shapes[randIndex]);
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
                for (int i = 0; i < shape.length; i++)
                {
                    // Check for overlap and snap if needed
                    if (isViewOverlapping(shape[0], blankShape)) {
                        // Snap the detailsImageView to the blankImageView
                        snapToTarget(shape[0], blankShape);
                        Log.d("Game shape easy", "Finished level " + level);

                        SoundHelper sfx = new SoundHelper(shapeHard.this, R.raw.level_complete, false);

                        increaseLevel(level, "shape");
                        popup.showNextLevel();
                        timer.cancelTimer();
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
