package com.example.kidslearn;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.constraintlayout.widget.ConstraintSet;

import android.annotation.SuppressLint;
import android.content.ClipData;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Rect;
import android.media.Image;
import android.os.Bundle;
import android.util.Log;
import android.view.DragEvent;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

import Helper.SoundHelper;
import Helper.gameMenuHelper;
import Helper.userInterfaceHelper;

public class gameAnimal extends AppCompatActivity implements View.OnTouchListener{
    userInterfaceHelper UIHelper;
    gameMenuHelper gameHelper;

    String difficulty;
    int level;

    ImageView[] animals;
    ImageView[] animalBlanks;
    TextView info;
    Bitmap[] animalImages;
    int previousX, previousY, levelIndex, count;

    int[] easy = {0, 6, 4, 1, 2};
    int[][] easyBlank =
            {
                    {0, 1}, //0
                    {2, 6}, //1
                    {5, 4}, //1
                    {1, 3}, //0
                    {0, 2} //1
            };
    int[] easyAns =
            {
                    0, 1, 1, 0, 1
            };

    int[][] medium =
            {
                    {1, 2, 4},
                    {4, 2, 3},
                    {0, 4, 6},
                    {2, 3, 5},
                    {5, 0, 2}
            };

    int[][] hard =
            {
                    {5, 1, 4, 6, 2},
                    {2, 1, 5, 0, 6},
                    {0, 1, 2, 3, 5},
                    {4, 6, 1, 5, 0},
                    {3, 5, 6, 2, 1}
            };
    boolean[] isDone;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game_animal);

        UIHelper = new userInterfaceHelper(this);
        UIHelper.removeActionbar();
        UIHelper.transparentStatusBar();

        gameHelper = new gameMenuHelper();
        difficulty = getIntent().getStringExtra("Diff");
        level = getIntent().getIntExtra("Level", 1);
        levelIndex = level-1;

        info = findViewById(R.id.infoTxt);
        info.setText(gameHelper.getDifficulty() + "\nLevel " + level);

        setAnimalVariables();
        setupGame();

        for (int i = 0; i < animals.length; i++)
        {
            animals[i].setOnTouchListener(this);
        }

    }

    void setAnimalVariables() {
        animals = new ImageView[]{
                findViewById(R.id.animal1),
                findViewById(R.id.animal2),
                findViewById(R.id.animal3),
                findViewById(R.id.animal4),
                findViewById(R.id.animal5)
        };

        animalBlanks = new ImageView[] {
                findViewById(R.id.animal_blank1),
                findViewById(R.id.animal_blank2),
                findViewById(R.id.animal_blank3),
                findViewById(R.id.animal_blank4),
                findViewById(R.id.animal_blank5)
        };

        animalImages = new Bitmap[]
                {
                        BitmapFactory.decodeResource(this.getResources(), R.drawable.cat),
                        BitmapFactory.decodeResource(this.getResources(), R.drawable.horse),
                        BitmapFactory.decodeResource(this.getResources(), R.drawable.monkey),
                        BitmapFactory.decodeResource(this.getResources(), R.drawable.tiger),
                        BitmapFactory.decodeResource(this.getResources(), R.drawable.pig),
                        BitmapFactory.decodeResource(this.getResources(), R.drawable.elephant),
                        BitmapFactory.decodeResource(this.getResources(), R.drawable.dog)
                };
    }

    void setupGame()
    {
        count = 0;
        if(gameHelper.getDifficulty() == gameHelper.easyDiff())
        {
            animals[0].setVisibility(View.VISIBLE);
            animalBlanks[0].setVisibility(View.VISIBLE);
            animalBlanks[1].setVisibility(View.VISIBLE);

            animals[0].setImageBitmap(animalImages[easy[levelIndex]]);
            animalBlanks[0].setImageBitmap(animalImages[easyBlank[levelIndex][0]]);
            animalBlanks[1].setImageBitmap(animalImages[easyBlank[levelIndex][1]]);
        }
        else if(gameHelper.getDifficulty() == gameHelper.mediumDiff())
            count = 3;
        else if(gameHelper.getDifficulty() == gameHelper.hardDiff())
            count = 5;

        if(count != 0)
        {
            for (int i = 0; i < count; i++)
            {

                if(count == 3)
                {
                    animals[i+2].setVisibility(View.VISIBLE);
                    animalBlanks[i+2].setVisibility(View.VISIBLE);
                    animals[i+2].setImageBitmap(animalImages[medium[levelIndex][i]]);
                    animalBlanks[i+2].setImageBitmap(animalImages[medium[levelIndex][i]]);
                    isDone = new boolean[] {true, true, false, false, false};
                }
                else if(count == 5)
                {
                    animals[i].setVisibility(View.VISIBLE);
                    animalBlanks[i].setVisibility(View.VISIBLE);
                    animals[i].setImageBitmap(animalImages[hard[levelIndex][i]]);
                    animalBlanks[i].setImageBitmap(animalImages[hard[levelIndex][i]]);
                    isDone = new boolean[] {false, false, false, false, false};
                }
            }
        }
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
                if(gameHelper.getDifficulty() == gameHelper.easyDiff())
                {
                    for (int i = 0; i < 2; i++)
                    {
                        // Check for overlap and snap if needed
                        if (isViewOverlapping(animals[0], animalBlanks[easyAns[levelIndex]])) {
                            // Snap the detailsImageView to the blankImageView
                            snapToTarget(animals[0], animalBlanks[easyAns[levelIndex]]);
                            animals[i].setOnTouchListener(null);
                            win();
                        }
                    }
                }
                else if(gameHelper.getDifficulty() == gameHelper.mediumDiff())
                {
                    for (int i = 0; i < animals.length; i++)
                    {
                        if (isViewOverlapping(animals[i], animalBlanks[i])) {
                            snapToTarget(animals[i], animalBlanks[i]);
                            animals[i].setOnTouchListener(null);
                            isDone[i] = true;
                            if(areAllTrue(isDone))
                            {
                                win();
                            }
                        }
                    }
                }
                else if(gameHelper.getDifficulty() == gameHelper.hardDiff())
                {
                    for (int i = 0; i < animals.length; i++)
                    {
                        if (isViewOverlapping(animals[i], animalBlanks[i])) {
                            snapToTarget(animals[i], animalBlanks[i]);
                            animals[i].setOnTouchListener(null);
                            isDone[i] = true;
                            if(areAllTrue(isDone))
                            {
                                win();
                            }
                        }
                    }
                }
                break;
        }

        return true;
    }

    void win()
    {
        SoundHelper sfx = new SoundHelper(gameAnimal.this, R.raw.level_complete, false);
        //popup.showNextLevel();
        //timer.cancelTimer();
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
}