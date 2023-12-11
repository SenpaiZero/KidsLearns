package Helper;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.widget.Button;

import androidx.cardview.widget.CardView;
import androidx.constraintlayout.widget.ConstraintLayout;

import com.example.kidslearn.R;
import com.example.kidslearn.levelDifficulty;

public class LevelPopupHelper {
    Activity activity;
    ConstraintLayout timeout, nextLevel;

    Button quitBtn, doneBtn, retryBtn;
    public LevelPopupHelper(Activity activity)
    {
        this.activity = activity;
        timeout = activity.findViewById(R.id.timeRunOut);
        nextLevel = activity.findViewById(R.id.finishLevel);
        doneBtn = activity.findViewById(R.id.nextBtn);
        quitBtn = activity.findViewById(R.id.quitBtn);
        retryBtn = activity.findViewById(R.id.retryBtn);
    }
    public void showTimeout()
    {
        timeout.setVisibility(View.VISIBLE);

        quitBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                activity.startActivity(new Intent(activity, levelDifficulty.class));
            }
        });

        retryBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = activity.getIntent();
                activity.finish();
                activity.startActivity(intent);
            }
        });
    }

    public void showNextLevel()
    {
        nextLevel.setVisibility(View.VISIBLE);
        doneBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                activity.startActivity(new Intent(activity, levelDifficulty.class));
            }
        });
    }
}
