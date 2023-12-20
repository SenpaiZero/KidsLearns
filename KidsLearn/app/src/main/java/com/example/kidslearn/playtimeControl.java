package com.example.kidslearn;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.TimePicker;

import java.util.Timer;

import Helper.BaseActivity;
import Helper.sharedPref;
import Helper.userInterfaceHelper;

public class playtimeControl  extends BaseActivity {

    TimePicker timePicker;
    Button applyBtn;
    ImageButton backBtn;
    userInterfaceHelper UIHelper;
    TextView remainingTime, daily;
    sharedPref db;

    Switch timeSwitch;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_playtime_control);

        UIHelper = new userInterfaceHelper(this);
        UIHelper.removeActionbar();
        UIHelper.transparentStatusBar();

        db = new sharedPref(this);

        timePicker = findViewById(R.id.timePicker);
        backBtn = findViewById(R.id.backBtn);
        remainingTime = findViewById(R.id.totalTimeTxt);
        daily = findViewById(R.id.textView23);
        timeSwitch = findViewById(R.id.timeControlSwitch);
        applyBtn = findViewById(R.id.applyBtn);

        timePicker.setIs24HourView(true);
        updateDaily();
        updateRemainingTime();

        timeSwitch.setChecked(db.getIsTimer());
        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(playtimeControl.this, parentalControl.class));
            }
        });

        applyBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                stopTimer();
                int hour = timePicker.getHour();
                int min = timePicker.getMinute();
                long millis = (hour * 60 * 60 * 1000) + (min * 60 * 1000);
                db.setTimer(millis);
                db.setRemainingTime(millis);
                db.setIsTimer(timeSwitch.isChecked());

                Log.i("Timer", db.getRemainingTimer() + "");
                updateDaily();
                updateRemainingTime();

                new AlertDialog.Builder(playtimeControl.this)
                        .setTitle("Saved Success")
                        .setMessage("You saved the timer successfully")

                        .setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                            }
                        })
                        .show();
            }
        });
        startTimer();
    }

    void updateDaily() {
        if(db.getIsTimer())
        {
            long timerInMillis = db.getTimer();

            // Convert milliseconds to hours, minutes, and seconds
            long hours = timerInMillis / (1000 * 60 * 60);
            long minutes = (timerInMillis % (1000 * 60 * 60)) / (1000 * 60);
            long seconds = ((timerInMillis % (1000 * 60 * 60)) % (1000 * 60)) / 1000;

            // Format the time values into "hour:minutes:seconds"
            String formattedTime = String.format("%02d:%02d:%02d", hours, minutes, seconds);

            daily.setText(formattedTime);
        }
        else
        {
            daily.setText("TURNED OFF");
        }
    }



    CountDownTimer CD;
    void updateRemainingTime() {
        if (db.getIsTimer()) {
            CD = new CountDownTimer(db.getRemainingTimer(), 1000) {
                @Override
                public void onTick(long millisUntilFinished) {
                    long totalSeconds = millisUntilFinished / 1000;

                    int hours = (int) (totalSeconds / 3600);
                    int minutes = (int) ((totalSeconds % 3600) / 60);
                    int seconds = (int) (totalSeconds % 60);

                    String formattedTime = String.format("%02d:%02d:%02d", hours, minutes, seconds);
                    remainingTime.setText(formattedTime);
                }

                @Override
                public void onFinish() {
                    remainingTime.setText("00:00:00");
                }
            }.start();
        } else {
            remainingTime.setText("TURNED OFF");
        }
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        CD.cancel();
    }
}