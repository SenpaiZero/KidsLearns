package com.example.kidslearn;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.Image;
import android.os.Bundle;
import android.util.Base64;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;

import Helper.BaseActivity;
import Helper.NeutralActivity;
import Helper.sharedPref;
import Helper.userInterfaceHelper;

public class coloringBookmark extends NeutralActivity {

    ImageView[] bookmarkImages;
    ImageButton[] deleteBtn;
    CardView[] cards;
    ImageButton backBtn;
    String[] base64Images;
    userInterfaceHelper UIHelper;
    sharedPref db;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_coloring_bookmark);

        db = new sharedPref(coloringBookmark.this);
        UIHelper = new userInterfaceHelper(this);
        UIHelper.removeActionbar();
        UIHelper.transparentStatusBar();

        bookmarkImages = new ImageView[]
                {
                        findViewById(R.id.one),
                        findViewById(R.id.two),
                        findViewById(R.id.three),
                        findViewById(R.id.four),
                        findViewById(R.id.five),
                        findViewById(R.id.six),
                        findViewById(R.id.seven),
                        findViewById(R.id.eight),
                        findViewById(R.id.nine),
                        findViewById(R.id.ten)
                };

        deleteBtn = new ImageButton[]
                {
                        findViewById(R.id.oneBtn),
                        findViewById(R.id.twoBtn),
                        findViewById(R.id.threeBtn),
                        findViewById(R.id.fourBtn),
                        findViewById(R.id.fiveBtn),
                        findViewById(R.id.sixBtn),
                        findViewById(R.id.sevenBtn),
                        findViewById(R.id.eightBtn),
                        findViewById(R.id.nineBtn),
                        findViewById(R.id.tenBtn)
                };

        cards = new CardView[]
                {
                        findViewById(R.id.cOne),
                        findViewById(R.id.cTwo),
                        findViewById(R.id.cThree),
                        findViewById(R.id.cFour),
                        findViewById(R.id.cFive),
                        findViewById(R.id.cSix),
                        findViewById(R.id.cSeven),
                        findViewById(R.id.cEight),
                        findViewById(R.id.cNine),
                        findViewById(R.id.cTen)
                };

        backBtn = findViewById(R.id.imageButton3);
        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(coloringBookmark.this, coloringMenu.class));
            }
        });

        changeVisiblity();
    }

    void changeVisiblity()
    {
        base64Images = db.getColoringBookmark();
        for (int i = 1; i < base64Images.length; i++)
        {
            if(base64Images[i].isEmpty())
            {
                cards[i-1].setVisibility(View.GONE);
            }
            else
            {
                cards[i-1].setVisibility(View.VISIBLE);
                byte[] b = Base64.decode(base64Images[i], Base64.DEFAULT);
                Bitmap bitmap = BitmapFactory.decodeByteArray(b, 0, b.length);
                bookmarkImages[i-1].setImageBitmap(bitmap);
            }

            int finalI = i;
            deleteBtn[i-1].setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    new AlertDialog.Builder(coloringBookmark.this)
                            .setTitle("Deleting")
                            .setMessage("Are you sure you want to delete this drawing?")

                            .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int which) {
                                    db.setColoringBookmark("", finalI);
                                    changeVisiblity();
                                }
                            })
                            .setNegativeButton(android.R.string.no, new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {

                                }
                            })
                            .show();
                }
            });

            bookmarkImages[i-1].setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    startActivity(new Intent(coloringBookmark.this, coloring.class)
                            .putExtra("image", finalI)
                            .putExtra("normal", false));
                }
            });
        }
    }
}