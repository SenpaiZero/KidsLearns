package com.example.kidslearn;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;

import Helper.userInterfaceHelper;

public class coloringMenu extends AppCompatActivity {

    ImageButton[] buttons;
    userInterfaceHelper UIHelper;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_coloring_menu);

        UIHelper = new userInterfaceHelper(this);
        UIHelper.removeActionbar();
        UIHelper.transparentStatusBar();

        buttons = new ImageButton[]
                {
                        findViewById(R.id.coloring1),
                        findViewById(R.id.coloring2),
                        findViewById(R.id.coloring3),
                        findViewById(R.id.coloring4),
                        findViewById(R.id.coloring5),
                        findViewById(R.id.coloring6),
                        findViewById(R.id.coloring7),
                        findViewById(R.id.coloring8),
                        findViewById(R.id.coloring9),
                        findViewById(R.id.coloring10)
                };

        for (int i = 0; i < buttons.length; i++)
        {
            int finalI = i;
            buttons[i].setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    startActivity(new Intent(coloringMenu.this, coloring.class)
                            .putExtra("image", (finalI+1)));
                }
            });
        }
    }
}