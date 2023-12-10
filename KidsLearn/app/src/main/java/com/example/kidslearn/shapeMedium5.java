package com.example.kidslearn;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
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

import Helper.TimerHelper;
import Helper.gameMenuHelper;
import Helper.userInterfaceHelper;

public class shapeMedium5 extends AppCompatActivity implements View.OnTouchListener{

    userInterfaceHelper UIHelper;
    gameMenuHelper gameHelper;
    int previousX, previousY, level, shapeCount;
    boolean[] shapesDone;
    ImageView[] shapes;
    ImageView[] blankShapes;
    boolean[] shapesBlankDone;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shape_medium5);

        UIHelper = new userInterfaceHelper(this);
        UIHelper.removeActionbar();
        UIHelper.transparentStatusBar();

        gameHelper = new gameMenuHelper();
        level = getIntent().getIntExtra("Level", 1);

        String info = gameHelper.getDifficulty() + "\n" + level;
        TextView infoTxt = findViewById(R.id.infoTxt);
        infoTxt.setText(info);
        ProgressBar progressBar = findViewById(R.id.progressBar);
        TimerHelper timer = new TimerHelper(30000, 1000);

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



        shapes = new ImageView[]
                {
                        findViewById(R.id.shape1),
                        findViewById(R.id.shape2),
                        findViewById(R.id.shape3),
                        findViewById(R.id.shape4),
                        findViewById(R.id.shape5),
                        findViewById(R.id.shape6)
                };
        blankShapes = new ImageView[]
                {
                        findViewById(R.id.blank1),
                        findViewById(R.id.blank2),
                        findViewById(R.id.blank3),
                        findViewById(R.id.blank4),
                        findViewById(R.id.blank5),
                        findViewById(R.id.blank6)
                };

        shapeCount = shapes.length;
        shapesDone = new boolean[shapeCount];
        shapesBlankDone = new boolean[shapeCount];
        for (int i = 0; i < shapeCount; i++)
        {
            shapesDone[i] = false;
            shapesBlankDone[i] = false;
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
                int[][] overlappingPairs = {
                        {0, 0},
                        {1, 1},
                        {2, 2},
                        {3, 3},
                        {4, 4},
                        {5, 5},
                        {2, 3},
                        {3, 2},
                        {4, 5},
                        {5, 4}
                };

                for (int[] pair : overlappingPairs) {
                    int firstIndex = pair[0];
                    int secondIndex = pair[1];

                    if (!shapesBlankDone[secondIndex] && !shapesDone[firstIndex] && isViewOverlapping(shapes[firstIndex], blankShapes[secondIndex])) {
                        snapToTarget(shapes[firstIndex], blankShapes[secondIndex]);
                        shapesDone[firstIndex] = true;
                        shapesBlankDone[secondIndex] = true;
                        shapes[firstIndex].setOnTouchListener(null);
                        if (areAllTrue(shapesDone)) {
                            Log.d("Game shape medium", "Finished level " + level);
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
}