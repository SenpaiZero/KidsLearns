package Helper;

import android.app.Service;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.IBinder;

import com.example.kidslearn.R;

public class MusicServiceBackgroundNormal extends Service {
    private static MusicServiceBackgroundNormal instance;
    private MediaPlayer mediaPlayer;

    public static MusicServiceBackgroundNormal getInstance() {
        return instance;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        instance = this;
        // Initialize MediaPlayer here
        mediaPlayer = MediaPlayer.create(this, R.raw.music_background);
        mediaPlayer.setLooping(true); // If you want the music to loop
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        mediaPlayer.start();
        return START_STICKY;
    }

    @Override
    public void onDestroy() {
        mediaPlayer.stop();
        mediaPlayer.release();
        super.onDestroy();
    }

    public void pauseMusic() {
        try
        {
            if (mediaPlayer != null && mediaPlayer.isPlaying()) {
                mediaPlayer.pause();
            }
        } catch (Exception e)
        {
            mediaPlayer = MediaPlayer.create(this, R.raw.music_background);
            mediaPlayer.setLooping(true); // If you want the music to loop
        }

    }

    public void resumeMusic() {
        try
        {
            if (mediaPlayer != null && !mediaPlayer.isPlaying()) {
                mediaPlayer.start();
            }
        } catch (Exception e)
        {
            mediaPlayer = MediaPlayer.create(this, R.raw.music_background);
            mediaPlayer.setLooping(true); // If you want the music to loop
        }
    }

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }
}
