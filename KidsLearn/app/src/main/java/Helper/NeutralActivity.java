package Helper;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import com.example.kidslearn.gameTimeup;

public class NeutralActivity extends TimerBasedActivity
{

    sharedPref db;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        settingHelper.setExemption(false);
        db = new sharedPref(this);
        Log.i("db", db.getMusic() + "");
        if(!db.getMusic())
        {
            stopMusic();
        }
        else
        {
            Intent musicIntent = new Intent(this, MusicServiceBackgroundNormal.class);
            startService(musicIntent);
        }

        if(DayChecker.isNewDay(this))
        {
            db.setRemainingTime(db.getTimer());
        }
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

    @Override
    protected void onResume() {
        super.onResume();
        startMusic();
    }

    @Override
    protected void onPause() {
        super.onPause();
        stopMusic();
    }

    @SuppressLint("MissingSuperCall")
    @Override
    public void onBackPressed() {

    }

    public void stopMusic()
    {
        MusicServiceBackgroundNormal musicService = MusicServiceBackgroundNormal.getInstance();
        if (musicService != null) {
            musicService.pauseMusic();
        }
    }

    public void startMusic()
    {
        if(db.getMusic())
        {
            MusicServiceBackgroundNormal musicService = MusicServiceBackgroundNormal.getInstance();
            if (musicService != null)
            {
                musicService.resumeMusic();
            }
        }
    }
}