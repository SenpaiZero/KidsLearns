package com.example.kidslearn;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;

import Helper.BaseActivity;
import Helper.sharedPref;
import Helper.userInterfaceHelper;

public class achievement extends BaseActivity {

    ImageButton backBtn;
    Button easyBtn, mediumBtn, hardBtn;
    ImageView[] alphabet, numbers, colors, shapes, animals;
    Bitmap finish;
    Bitmap notFinish;
    userInterfaceHelper UIHelper;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_achievement);

        UIHelper = new userInterfaceHelper(this);
        UIHelper.removeActionbar();
        UIHelper.transparentStatusBar();

        setupVariables();
        loadData("easy");
        easyBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                changeButtonColor(easyBtn);
                loadData("easy");
            }
        });

        mediumBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                changeButtonColor(mediumBtn);
                loadData("medium");
            }
        });

        hardBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                changeButtonColor(hardBtn);
                loadData("hard");
            }
        });

        changeButtonColor(easyBtn);
        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(achievement.this, parentalControl.class));
            }
        });
    }

    void setupVariables()
    {
        backBtn = findViewById(R.id.backBtn);
        easyBtn = findViewById(R.id.easyBtn);
        mediumBtn = findViewById(R.id.mediumBtn);
        hardBtn = findViewById(R.id.hardBtn);

        alphabet = new ImageView[]
                {
                        findViewById(R.id.alphabet1),
                        findViewById(R.id.alphabet2),
                        findViewById(R.id.alphabet3),
                        findViewById(R.id.alphabet4),
                        findViewById(R.id.alphabet5)
                };

        numbers = new ImageView[]
                {
                        findViewById(R.id.numbers1),
                        findViewById(R.id.numbers2),
                        findViewById(R.id.numbers3),
                        findViewById(R.id.numbers4),
                        findViewById(R.id.numbers5)
                };

        animals = new ImageView[]
                {
                        findViewById(R.id.animals1),
                        findViewById(R.id.animals2),
                        findViewById(R.id.animals3),
                        findViewById(R.id.animals4),
                        findViewById(R.id.animals5)
                };

        shapes = new ImageView[]
                {
                        findViewById(R.id.shapes1),
                        findViewById(R.id.shapes2),
                        findViewById(R.id.shapes3),
                        findViewById(R.id.shapes4),
                        findViewById(R.id.shapes5)
                };

        colors = new ImageView[]
                {
                        findViewById(R.id.colors1),
                        findViewById(R.id.colors2),
                        findViewById(R.id.colors3),
                        findViewById(R.id.colors4),
                        findViewById(R.id.colors5)
                };

        finish = BitmapFactory.decodeResource(this.getResources(), R.drawable.done);
        notFinish = BitmapFactory.decodeResource(this.getResources(), R.drawable.notdone);
    }

    void loadData(String difficulty) {
        sharedPref db = new sharedPref(this);
        db.setShapes(0);
        int start, end;
        if (difficulty.equalsIgnoreCase("easy")) {
            start = 1;
            end = 6;
        } else if (difficulty.equalsIgnoreCase("medium")) {
            start = 6;
            end = 11;
        } else if (difficulty.equalsIgnoreCase("hard")) {
            start = 11;
            end = 16;
        } else {
            start = 0;
            end = 0;
        }

        for (int i = start; i < end; i++) {
            {
                if (i <= db.getAlphabet())
                    alphabet[i - start].setImageBitmap(finish);
                else
                    alphabet[i - start].setImageBitmap(notFinish);

                if (i <= db.getNumber())
                    numbers[i - start].setImageBitmap(finish);
                else
                    numbers[i - start].setImageBitmap(notFinish);

                if (i <= db.getColors())
                    colors[i - start].setImageBitmap(finish);
                else
                    colors[i - start].setImageBitmap(notFinish);

                if (i <= db.getAnimal())
                    animals[i - start].setImageBitmap(finish);
                else
                    animals[i - start].setImageBitmap(notFinish);

                if (i <= db.getShapes())
                    shapes[i - start].setImageBitmap(finish);
                else
                    shapes[i - start].setImageBitmap(notFinish);
            }

            Log.i("pref", db.getAlphabet() + "");
        }
    }
    void changeButtonColor(Button btn)
    {
        easyBtn.setBackgroundTintList(ContextCompat.getColorStateList(this, R.color.blueBG));
        mediumBtn.setBackgroundTintList(ContextCompat.getColorStateList(this, R.color.blueBG));
        hardBtn.setBackgroundTintList(ContextCompat.getColorStateList(this, R.color.blueBG));

        btn.setBackgroundTintList(ContextCompat.getColorStateList(this, R.color.blueColoring));

    }
}