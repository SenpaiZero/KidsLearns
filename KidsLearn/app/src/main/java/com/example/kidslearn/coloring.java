package com.example.kidslearn;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.drawable.BitmapDrawable;
import android.media.MediaScannerConnection;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.util.Pair;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;

import java.io.File;
import java.io.FileOutputStream;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Random;

import Helper.MusicServiceBackgroundNormal;
import Helper.QueueLinearFloodFiller;
import Helper.SoundHelper;
import Helper.sharedPref;
import Helper.userInterfaceHelper;

public class coloring extends AppCompatActivity {
    userInterfaceHelper UIHelper;
    private ImageView imageView;
    private Bitmap mBitmap;
    private QueueLinearFloodFiller floodFiller;
    CardView redBtn, blueBtn, greenBtn, yellowBtn, brownBtn, purpleBtn, orangeBtn, blackBtn;
    ImageButton backBtn, cameraBtn, eraseBtn;
    int replacementColor;
    int image;
    int toler;
    SoundHelper bgMusic;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_coloring);

        UIHelper = new userInterfaceHelper(this);
        UIHelper.removeActionbar();
        UIHelper.transparentStatusBar();
        bgMusic = new SoundHelper(this, R.raw.play_game_music_bg, true);
        stopService(new Intent(this, MusicServiceBackgroundNormal.class));

        imageView = findViewById(R.id.imageView2);
        image = getIntent().getIntExtra("image", 1);
        int resId = getResources().getIdentifier("coloring" + image, "drawable", getPackageName());
        mBitmap = BitmapFactory.decodeResource(getResources(), resId);

        imageView.setImageBitmap(mBitmap);

        floodFiller = new QueueLinearFloodFiller(mBitmap);

        redBtn = findViewById(R.id.redColor);
        blueBtn = findViewById(R.id.blueColor);
        greenBtn = findViewById(R.id.greenColor);
        yellowBtn = findViewById(R.id.yellowColor);
        brownBtn = findViewById(R.id.brownColor);
        purpleBtn = findViewById(R.id.purpleColor);
        orangeBtn = findViewById(R.id.orangeColor);
        blackBtn = findViewById(R.id.blackColor);

        backBtn = findViewById(R.id.backBtn);
        eraseBtn = findViewById(R.id.eraseBtn);
        cameraBtn = findViewById(R.id.cameraBtn);

        replacementColor = getResources().getColor(R.color.redColoring);
        changeColor(redBtn);

        toler = 20;
        eraseBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                changeColor(null);
                replacementColor = getResources().getColor(R.color.white);
            }
        });

        cameraBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sharedPref db = new sharedPref(coloring.this);
                if(db.getGallery())
                {
                    new AlertDialog.Builder(coloring.this)
                        .setTitle("Saved Success")
                        .setMessage("You saved the drawing successfully")

                        .setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                            }
                        })
                        .show();
                    SaveImage(((BitmapDrawable)imageView.getDrawable()).getBitmap());
                }
                else
                {
                    UIHelper.showCustomToast("Saving to gallery is disabled");
                }

            }
        });
        redBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                changeColor(redBtn);
                replacementColor = getResources().getColor(R.color.redColoring);
            }
        });

        blueBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                replacementColor = getResources().getColor(R.color.blueColoring);
                changeColor(blueBtn);
            }
        });

        greenBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                changeColor(greenBtn);
                replacementColor = getResources().getColor(R.color.greenColoring);
            }
        });

        yellowBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                changeColor(yellowBtn);
                replacementColor = getResources().getColor(R.color.yellowColoring);
            }
        });

        purpleBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                changeColor(purpleBtn);
                replacementColor = getResources().getColor(R.color.purpleColoring);
            }
        });

        brownBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                changeColor(brownBtn);
                replacementColor = getResources().getColor(R.color.brownColoring);
            }
        });

        orangeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                changeColor(orangeBtn);
                replacementColor = getResources().getColor(R.color.orangeColoring);
            }
        });

        blackBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                changeColor(blackBtn);
                replacementColor = getResources().getColor(R.color.blackColoring);
            }
        });

        imageView.setOnTouchListener((v, event) -> {
            int action = event.getAction();
            switch (action) {
                case MotionEvent.ACTION_DOWN:
                    try {
                        // Get the actual image coordinates based on touch coordinates
                        int viewX = (int) event.getX();
                        int viewY = (int) event.getY();
                        // Log the touch coordinates
                        Log.d("Touch Info", "View Coordinates: (" + viewX + ", " + viewY + ")");
                        toler = 244;
                        int[] tolerance = {toler, toler, toler};
                        floodFiller.setTolerance(tolerance);

                        float[] pts = {viewX, viewY};
                        Matrix inverse = new Matrix();
                        imageView.getImageMatrix().invert(inverse);
                        inverse.mapPoints(pts);
                        int imageX = (int) pts[0];
                        int imageY = (int) pts[1];
                        int touchedColor = mBitmap.getPixel(imageX, imageY);
                        int black = Color.BLACK;

                        if (touchedColor != black) {
                            if (imageX >= 0 && imageX < mBitmap.getWidth() && imageY >= 0 && imageY < mBitmap.getHeight()) {

                                // Perform flood fill
                                Log.d("Color Info", "Touched Color: " + touchedColor);
                                Log.d("Color info", "Color using: " + replacementColor);
                                floodFiller.setTargetColor(touchedColor);
                                floodFiller.setFillColor(replacementColor);
                                floodFiller.floodFill(imageX, imageY);

                                imageView.setImageBitmap(floodFiller.getImage());
                            }
                        }
                    } catch (IllegalArgumentException ex)
                    {
                        Log.e("Error Fill", ex.getMessage());
                    }
                    break;
            }
            return true;
        });
    }

    void changeColor(CardView card)
    {
        redBtn.setCardBackgroundColor(getResources().getColor(R.color.white));
        blueBtn.setCardBackgroundColor(getResources().getColor(R.color.white));
        greenBtn.setCardBackgroundColor(getResources().getColor(R.color.white));
        purpleBtn.setCardBackgroundColor(getResources().getColor(R.color.white));
        orangeBtn.setCardBackgroundColor(getResources().getColor(R.color.white));
        blackBtn.setCardBackgroundColor(getResources().getColor(R.color.white));
        yellowBtn.setCardBackgroundColor(getResources().getColor(R.color.white));
        brownBtn.setCardBackgroundColor(getResources().getColor(R.color.white));

        if(card == null) return;
        card.setCardBackgroundColor(getResources().getColor(R.color.lightDark));
    }
     void SaveImage(Bitmap finalBitmap) {

        String root = Environment.getExternalStoragePublicDirectory(
                Environment.DIRECTORY_PICTURES).toString();
        File myDir = new File(root + "/KidsLearn");
        myDir.mkdirs();
        Random generator = new Random();

        int n = 10000;
        n = generator.nextInt(n);
        String fname = "Image-"+ n +".jpg";
        File file = new File (myDir, fname);
        if (file.exists ()) file.delete ();
        try {
            FileOutputStream out = new FileOutputStream(file);
            finalBitmap.compress(Bitmap.CompressFormat.JPEG, 90, out);
            // sendBroadcast(new Intent(Intent.ACTION_MEDIA_MOUNTED,
            //     Uri.parse("file://"+ Environment.getExternalStorageDirectory())));
            out.flush();
            out.close();

        } catch (Exception e) {
            e.printStackTrace();
        }
// Tell the media scanner about the new file so that it is
// immediately available to the user.
        MediaScannerConnection.scanFile(this, new String[]{file.toString()}, null,
                new MediaScannerConnection.OnScanCompletedListener() {
                    public void onScanCompleted(String path, Uri uri) {
                        Log.i("ExternalStorage", "Scanned " + path + ":");
                        Log.i("ExternalStorage", "-> uri=" + uri);
                    }
                });
    }
    @Override
    protected void onPause() {
        super.onPause();
        bgMusic.pause();
    }
    @Override
    protected void onDestroy() {
        super.onDestroy();
        bgMusic.releaseMediaPlayer();
    }
    @Override
    protected  void onResume() {
        super.onResume();
        bgMusic.resume();
    }
}