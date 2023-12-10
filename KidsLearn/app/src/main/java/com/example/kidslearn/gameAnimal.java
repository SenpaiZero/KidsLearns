package com.example.kidslearn;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.constraintlayout.widget.ConstraintSet;

import android.annotation.SuppressLint;
import android.content.ClipData;
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

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

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
    int[] indexOfAnimals;
    boolean[] animalDone;
    int previousX, previousY;
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

        info = findViewById(R.id.infoTxt);
        info.setText(gameHelper.getDifficulty() + "\nLevel " + level);

        setAnimalVariables();
        setupGame();

        for (int i = 0; i < animals.length; i++)
        {
            animals[i].setOnTouchListener(this);
        }
    }

    int getAnimalCount() {
        if(difficulty.equalsIgnoreCase(gameHelper.easyDiff()))
        {
            return 2;
        }
        else if(difficulty.equalsIgnoreCase(gameHelper.mediumDiff()))
        {
            return 3;
        }
        else if(difficulty.equalsIgnoreCase(gameHelper.hardDiff()))
        {
            return 5;
        }
        else {
            // error
            return 2;
        }
    }

    void setAnimalVariables() {
        animals = new ImageView[]{
                findViewById(R.id.animal1),
                findViewById(R.id.animal2),
                findViewById(R.id.animal3),
                findViewById(R.id.animal4),
                findViewById(R.id.animal5),
                findViewById(R.id.animal6),
                findViewById(R.id.animal7)
        };

        animalBlanks = new ImageView[] {
                findViewById(R.id.animal_blank1),
                findViewById(R.id.animal_blank2),
                findViewById(R.id.animal_blank3),
                findViewById(R.id.animal_blank4),
                findViewById(R.id.animal_blank5),
                findViewById(R.id.animal_blank6),
                findViewById(R.id.animal_blank7)
        };
    }

    void setupGame() {
        Random rand = new Random();
        List<Integer> indexList = new ArrayList<>();
        for (int i = 0; i < 7; i++) {
            indexList.add(i);
        }
        Collections.shuffle(indexList);

        int animalCount = getAnimalCount();
        indexOfAnimals = new int[animalCount];
        animalDone = new boolean[animalCount];

        for (int i = 0; i < animalCount; i++) {
            int index = indexList.get(i);

            animalBlanks[index].setVisibility(View.VISIBLE);
            animals[index].setVisibility(View.VISIBLE);
            indexOfAnimals[i] = index;
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
                for (int i = 0; i < getAnimalCount(); i++)
                {
                    // Check for overlap and snap if needed
                    if (isViewOverlapping(animals[indexOfAnimals[i]], animalBlanks[indexOfAnimals[i]])) {
                        // Snap the detailsImageView to the blankImageView
                        snapToTarget(animals[indexOfAnimals[i]], animalBlanks[indexOfAnimals[i]]);
                        animalDone[indexOfAnimals[i]] = true;

                        if(areAllTrue(animalDone))
                        {
                            //congrats
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

    public static boolean areAllTrue(boolean[] array)
    {
        for(boolean b : array) if(!b) return false;
        return true;
    }
}