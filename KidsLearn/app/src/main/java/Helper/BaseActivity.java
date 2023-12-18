package Helper;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

public class BaseActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        sharedPref db = new sharedPref(this);

        Intent musicIntent = new Intent(this, MusicServiceBackgroundNormal.class);
        startService(musicIntent);
        if(!db.getMusic())
        {
            stopMusic();
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

    public void stopMusic()
    {
        MusicServiceBackgroundNormal musicService = MusicServiceBackgroundNormal.getInstance();
        if (musicService != null) {
            musicService.pauseMusic();
        }
    }

    public void startMusic()
    {
        MusicServiceBackgroundNormal musicService = MusicServiceBackgroundNormal.getInstance();
        if (musicService != null) {
            musicService.resumeMusic();
        }
    }

}
