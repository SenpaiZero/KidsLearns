package com.example.kidslearn;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.Button;
import android.widget.TimePicker;

import Helper.BaseActivity;

public class playtimeControl  extends BaseActivity {

    TimePicker timePicker;
    Button applyBtn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_playtime_control);

        timePicker.setIs24HourView(true);
    }
}