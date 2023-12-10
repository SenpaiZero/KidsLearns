package com.example.kidslearn;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import Helper.userInterfaceHelper;

public class parentalControl extends AppCompatActivity {

    userInterfaceHelper UIHelper;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_parental_control);

        UIHelper = new userInterfaceHelper(this);
        UIHelper.removeActionbar();
        UIHelper.transparentStatusBar();
    }
}