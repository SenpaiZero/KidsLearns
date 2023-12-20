package Helper;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

public class NeutralActivity extends TimerActivity
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
