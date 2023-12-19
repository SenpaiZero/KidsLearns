package com.example.kidslearn;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

import Helper.GameActivity;
import Helper.LevelPopupHelper;
import Helper.MusicServiceBackgroundNormal;
import Helper.SoundHelper;
import Helper.TimerHelper;
import Helper.gameMenuHelper;
import Helper.userInterfaceHelper;

public class alphabetEasy extends GameActivity implements View.OnTouchListener{

    TextView[] letters;
    TextView[] choicesTxt;

    String[][][] choices;
    int level, levelIndex, previousX, previousY;
    TextView infoTxt;
    TimerHelper timer;
    userInterfaceHelper UIHelper;
    gameMenuHelper gameHelper;
    LevelPopupHelper popup;
    int[] answersIndex;
    int[] dropIndex;
    SoundHelper bgMusic;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_alphabet_easy);

        UIHelper = new userInterfaceHelper(this);
        UIHelper.removeActionbar();
        UIHelper.transparentStatusBar();

        gameHelper = new gameMenuHelper();

        popup = new LevelPopupHelper(this);
        bgMusic = new SoundHelper(alphabetEasy.this, R.raw.play_game_music_bg, true);
        stopService(new Intent(this, MusicServiceBackgroundNormal.class));

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
                popup.showTimeout();
                SoundHelper sfx = new SoundHelper(alphabetEasy.this, R.raw.time_out, false);
            }
        });
        setupVariables();
    }

    void setupVariables() {
        letters = new TextView[]{
                findViewById(R.id.A),
                findViewById(R.id.B),
                findViewById(R.id.C),
                findViewById(R.id.D),
                findViewById(R.id.E),
                findViewById(R.id.F),
                findViewById(R.id.G),
                findViewById(R.id.H),
                findViewById(R.id.I),
                findViewById(R.id.J),
                findViewById(R.id.K),
                findViewById(R.id.L),
                findViewById(R.id.M),
                findViewById(R.id.N),
                findViewById(R.id.O),
                findViewById(R.id.P),
                findViewById(R.id.Q),
                findViewById(R.id.R),
                findViewById(R.id.S),
                findViewById(R.id.T),
                findViewById(R.id.U),
                findViewById(R.id.V),
                findViewById(R.id.W),
                findViewById(R.id.X),
                findViewById(R.id.Y),
                findViewById(R.id.Z)
        };

        choicesTxt = new TextView[]{
                findViewById(R.id.choice1),
                findViewById(R.id.choice2),
                findViewById(R.id.choice3)
        };
        choices = new String[][][]{
                {{"A", "C", "G"}}, //A 0
                {{"H", "G", "J"}}, //J 2
                {{"L", "H", "J"}}, //L 0
                {{"K", "W", "Z"}}, //W 1
                {{"Y", "A", "U"}} //Y 0
        };

        levelIndex = level-1;
        answersIndex = new int[]{0, 2, 0, 1, 0};
        dropIndex = new int[] {0, 9, 11, 22, 24};

        Log.i("level index", levelIndex + "");
        Log.i("answerIndex", answersIndex[levelIndex] + "");
        Log.i("dropIndex", dropIndex[levelIndex] + "");
        Log.i("choicesTxtLength", choicesTxt.length + "");
        Log.i("lettersLength", letters.length + "");

        for (int i = 0; i < choicesTxt.length; i++)
        {
            choicesTxt[i].setOnTouchListener(this);
            choicesTxt[i].setText(choices[levelIndex][0][i]);
        }

        letters[dropIndex[levelIndex]].setBackgroundColor(getResources().getColor(R.color.peach));
        letters[dropIndex[levelIndex]].setText("");
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
                if (isViewOverlapping(choicesTxt[answersIndex[levelIndex]], letters[dropIndex[levelIndex]])) {
                    // Snap the detailsImageView to the blankImageView
                    snapToTarget(choicesTxt[answersIndex[levelIndex]], letters[dropIndex[levelIndex]]);
                    Log.d("Game shape easy", "Finished level " + level);
                    increaseLevel(level, "alphabet");
                    popup.showNextLevel();
                    timer.cancelTimer();

                    SoundHelper sfx = new SoundHelper(alphabetEasy.this, R.raw.level_complete, false);
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
        rect1.inset(-20, -20);
        rect2.inset(-20, -20);

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