package Helper;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import com.example.kidslearn.R;
import com.example.kidslearn.gameTimeup;

public class GameActivity extends TimerBasedActivity{

    sharedPref db;
    SoundHelper bgMusic;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        settingHelper.setExemption(false);
        db = new sharedPref(this);

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
        if(db.getMusic())
        {
            bgMusic = new SoundHelper(this, R.raw.play_game_music_bg, true);
            stopService(new Intent(this, MusicServiceBackgroundNormal.class));
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        if(bgMusic != null)
            bgMusic.pause();
    }
    @Override
    protected void onDestroy() {
        super.onDestroy();
        if(bgMusic != null)
            bgMusic.releaseMediaPlayer();
    }
    @Override
    protected  void onResume() {
        super.onResume();
        if(db.getMusic())
            if(bgMusic != null)
                bgMusic.resume();
    }
    public void increaseLevel(int currentLevel, String game)
    {
        if("easy".equalsIgnoreCase(new gameMenuHelper().getDifficulty()))
            currentLevel += 0;
        else if("medium".equalsIgnoreCase(new gameMenuHelper().getDifficulty()))
            currentLevel += 5;
        else if("hard".equalsIgnoreCase(new gameMenuHelper().getDifficulty()))
            currentLevel += 10;

        sharedPref db = new sharedPref(this);
        if(game.equalsIgnoreCase("alphabet"))
        {
            if(db.getAlphabet() < currentLevel)
                db.setAlphabet(db.getAlphabet()+1);
        }
        else if(game.equalsIgnoreCase("animal"))
        {
            if(db.getAnimal() < currentLevel)
                db.setAnimal(db.getAnimal()+1);
        }
        else if(game.equalsIgnoreCase("shape"))
        {
            if(db.getShapes() < currentLevel)
                db.setShapes(db.getShapes() + 1);
        }
        else if(game.equalsIgnoreCase("color"))
        {
            if(db.getColors() < currentLevel)
                db.setColors(db.getColors()+1);
        }
        else if(game.equalsIgnoreCase("number"))
        {
            if(db.getNumber() < currentLevel)
                db.setNumber(db.getNumber()+1);
        }
    }
}