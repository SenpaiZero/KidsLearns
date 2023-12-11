package com.example.kidslearn;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.annotation.SuppressLint;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.PorterDuff;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.LayerDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import java.util.Random;

import Helper.TimerHelper;
import Helper.gameMenuHelper;
import Helper.userInterfaceHelper;

public class shapeEasy extends AppCompatActivity implements View.OnTouchListener{

    userInterfaceHelper UIHelper;
    gameMenuHelper gameHelper;

    ImageView moveShape;
    ImageView[] blankShape;

    int level;
    int previousX, previousY;
    boolean isDone;
    TimerHelper timer;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shape_easy);


        UIHelper = new userInterfaceHelper(this);
        UIHelper.removeActionbar();
        UIHelper.transparentStatusBar();

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
                // Handle timer finish, e.g., perform necessary actions
            }
        });

        timer.setOnTimerFinishedListener(new TimerHelper.OnTimerFinishedListener() {
            @Override
            public void onTimerFinished() {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        // Perform any other necessary actions on timer finish
                    }
                });
            }
        });
        isDone = false;
        moveShape = findViewById(R.id.shape1);
        blankShape = new ImageView[]
                {
                        findViewById(R.id.shapeBlank1),
                        findViewById(R.id.shapeBlank2)
                };

        moveShape.setOnTouchListener(this);

        Bitmap[] shapes =
                {
                        BitmapFactory.decodeResource(this.getResources(), R.drawable.triangle),
                        BitmapFactory.decodeResource(this.getResources(), R.drawable.square),
                        BitmapFactory.decodeResource(this.getResources(), R.drawable.rectangle),
                        BitmapFactory.decodeResource(this.getResources(), R.drawable.circle),
                        BitmapFactory.decodeResource(this.getResources(), R.drawable.oval),
                };
        int[] colors = {
                R.color.red,
                R.color.purple_700,
                R.color.teal_700,
                R.color.orange,
                R.color.purple_500
        };

        Random rand = new Random();
        int shapesLength = shapes.length;

        int levelIndex = level - 1; // Convert level number to array index
        if (levelIndex >= 0 && levelIndex < shapesLength) {
            moveShape.setImageBitmap(shapes[levelIndex]);
            blankShape[0].setImageBitmap(shapes[levelIndex]);
        }

        int randIndex = rand.nextInt(shapesLength);
        while (randIndex == levelIndex) {
            randIndex = rand.nextInt(shapesLength);
        }

        if (randIndex >= 0 && randIndex < shapesLength) {
            blankShape[1].setImageBitmap(shapes[randIndex]);
        }

        int color = colors[levelIndex]; // Get the color from the array

        // Apply color filter to the ImageView
        moveShape.setColorFilter(ContextCompat.getColor(this, color), android.graphics.PorterDuff.Mode.MULTIPLY);


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
                for (int i = 0; i < blankShape.length; i++)
                {
                    // Check for overlap and snap if needed
                    if (isViewOverlapping(moveShape, blankShape[0])) {
                        // Snap the detailsImageView to the blankImageView
                        snapToTarget(moveShape, blankShape[0]);
                            Log.d("Game shape easy", "Finished level " + level);
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