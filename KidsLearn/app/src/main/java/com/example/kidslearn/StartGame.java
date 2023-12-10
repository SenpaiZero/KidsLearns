package com.example.kidslearn;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

import java.util.Random;

import Helper.userInterfaceHelper;

public class StartGame extends AppCompatActivity {

    ImageButton parental, play, close;
    Button okay, submit;
    userInterfaceHelper UIHelper;
    ConstraintLayout parentalWarn, parentalVerify;
    TextView questionTxt;
    EditText answerTB;
    int mathAnswer;
    boolean isParentVerified = false;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start_game);

        UIHelper = new userInterfaceHelper(this);
        UIHelper.removeActionbar();
        UIHelper.transparentStatusBar();

        parental = findViewById(R.id.parentalBtn);
        parentalWarn = findViewById(R.id.parentLayer);
        parentalVerify = findViewById(R.id.parentVerify);
        play = findViewById(R.id.playBtn);
        close = findViewById(R.id.closeBtn);

        okay = findViewById(R.id.okayBtn);
        submit = findViewById(R.id.submitBtn);

        questionTxt = findViewById(R.id.mathQuestion);
        answerTB = findViewById(R.id.answerTB);

        play.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!isParentVerified && parentalVerify.getVisibility() != View.VISIBLE)
                {
                    parentalWarn.setVisibility(View.VISIBLE);
                    return;
                }

                startActivity(new Intent(StartGame.this, startMenu.class));
            }
        });

        parental.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(isParentVerified)
                {
                    startActivity(new Intent(StartGame.this, parentalControl.class));
                    overridePendingTransition(0, 0);
                }
                else
                    if(parentalWarn.getVisibility() != View.VISIBLE)
                        parentVerification();
            }
        });

        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String answerText = answerTB.getText().toString();
                if (!answerText.isEmpty()) {
                    double userAnswer = Double.parseDouble(answerText);
                    if (userAnswer == mathAnswer) {
                        isParentVerified = true;
                        parentalVerify.setVisibility(View.GONE);
                    }
                    else
                    {

                        UIHelper.showCustomToast("Incorrect Answer");
                    }
                }
            }
        });
        okay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                parentalWarn.setVisibility(View.GONE);
            }
        });

        close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                parentalVerify.setVisibility(View.GONE);
            }
        });
    }

    void parentVerification()
    {
        parentalVerify.setVisibility(View.VISIBLE);

        Random rand = new Random();
        int operator = rand.nextInt(2);
        int firstNum, secondNum;

        firstNum = (rand.nextInt(98) + 1);
        secondNum = (rand.nextInt(98) + 1);

        String question = firstNum + "";
        if(operator == 0) // Multiplication
        {
            question += " x " + secondNum;
            mathAnswer = firstNum * secondNum;
        }
        else if(operator == 1) // Division
        {
            question += " / " + secondNum;
            mathAnswer = firstNum / secondNum;
        }

        questionTxt.setText(question);
    }
}