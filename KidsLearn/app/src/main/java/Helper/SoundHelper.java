package Helper;

import android.app.Activity;
import android.media.MediaPlayer;
import android.net.Uri;

public class SoundHelper {
    MediaPlayer mp;

    public SoundHelper(Activity activity, int uri, boolean isLoop)
    {
        sharedPref db = new sharedPref(activity);

        mp = MediaPlayer.create(activity, uri);
        mp.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mediaPlayer) {
                // Release resources when playback completes
                releaseMediaPlayer();
            }
        });
        if(isLoop)
        {
            mp.setLooping(true);
            if(db.getMusic())
                mp.start();
        }
        else
        {
            if(db.getSound())
                mp.start();
        }
    }
    public void pause() {
        if (mp != null && mp.isPlaying()) {
            mp.pause();
        }
    }

    public void resume() {
        if (mp != null && !mp.isPlaying()) {
            mp.start();
        }
    }
    public void releaseMediaPlayer() {
        if (mp != null) {
            mp.stop();
            mp.release();
            mp = null;
        }
    }
}
