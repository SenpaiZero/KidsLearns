package com.example.kidslearn;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

import java.util.Random;

import Helper.BaseActivity;
import Helper.userInterfaceHelper;

public class gameTimeup extends BaseActivity {

    CardView parent;
    ImageButton close;
    ConstraintLayout parentalVerify;
    TextView questionTxt;
    EditText answerTB;
    int mathAnswer;
    Button submit;
    boolean isParentVerified = false;
    userInterfaceHelper UIHelper;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game_timeup);

        parent = findViewById(R.id.parentBtn);
        UIHelper = new userInterfaceHelper(this);
        UIHelper.removeActionbar();
        UIHelper.transparentStatusBar();

        parentalVerify = findViewById(R.id.parentVerify);
        submit = findViewById(R.id.submitBtn);

        questionTxt = findViewById(R.id.mathQuestion);
        answerTB = findViewById(R.id.answerTB);
        close = findViewById(R.id.closeBtn);

        parent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!isParentVerified && parentalVerify.getVisibility() != View.VISIBLE)
                {
                    parentalVerify.setVisibility(View.VISIBLE);
                    parentVerification();
                    return;
                }

                if(isParentVerified)
                    startActivity(new Intent(gameTimeup.this, playtimeControl.class));
            }
        });
        answerTB.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_DONE) {
                    // Hide the keyboard
                    InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                    imm.hideSoftInputFromWindow(answerTB.getWindowToken(), 0);
                    return true;
                }
                return false;
            }
        });
        answerTB.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (!hasFocus) {
                    // Hide the keyboard
                    InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                    imm.hideSoftInputFromWindow(answerTB.getWindowToken(), 0);
                }
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
        close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                parentalVerify.setVisibility(View.GONE);
            }
        });
    }
    void parentVerification() {
        parentalVerify.setVisibility(View.VISIBLE);

        Random rand = new Random();
        int operator = rand.nextInt(2);
        int firstNum, secondNum;

        firstNum = (rand.nextInt(98) + 1);
        secondNum = (rand.nextInt(98) + 1);

        String question;

        if (operator == 0) { // Multiplication
            question = firstNum + " x " + secondNum;
            mathAnswer = firstNum * secondNum;
        } else { // Division
            if (firstNum < secondNum) {
                int temp = firstNum;
                firstNum = secondNum;
                secondNum = temp;
            }
            question = firstNum + " / " + secondNum;
            mathAnswer = firstNum / secondNum;
        }

        questionTxt.setText(question);
    }
}