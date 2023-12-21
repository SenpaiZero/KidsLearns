package Helper;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import com.example.kidslearn.gameTimeup;

public class VideoActivity extends TimerBasedActivity
{
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        sharedPref db = new sharedPref(this);
        if(db.getIsTimer())
        {
            if(db.getRemainingTimer() <= 0)
            {
                Context context = this;
                Intent intent = new Intent(context, gameTimeup.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK); // Add this flag since it's not an Activity context
                context.startActivity(intent);

            }
        }
    }

    @SuppressLint("MissingSuperCall")
    @Override
    public void onBackPressed() {

    }
}