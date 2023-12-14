package Helper;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;

public class sharedPref {
    SharedPreferences sharedPreferences;
    Activity activity;
    public sharedPref(Activity activity)
    {
        this.activity = activity;
        sharedPreferences = this.activity.getSharedPreferences("myPrefs", Context.MODE_PRIVATE);
    }

    public void setSound(boolean active)
    {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putBoolean("soundActive", active);
        editor.apply();
    }

    public void setMusic(boolean active)
    {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putBoolean("musicActive", active);
        editor.apply();
    }

    public void setGallery(boolean active)
    {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putBoolean("galleryActive", active);
        editor.apply();
    }

    public void setAlphabet(int level)
    {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putInt("alphabet", level);
        editor.apply();
    }
    public void setNumber(int level)
    {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putInt("number", level);
        editor.apply();
    }
    public void setColors(int level)
    {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putInt("color", level);
        editor.apply();
    }
    public void setAnimal(int level)
    {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putInt("animal", level);
        editor.apply();
    }
    public void setShapes(int level)
    {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putInt("shape", level);
        editor.apply();
    }
    public int getAlphabet()
    {
        return sharedPreferences.getInt("alphabet", 1);
    }
    public int getNumber()
    {
        return sharedPreferences.getInt("number", 1);
    }
    public int getColors()
    {
        return sharedPreferences.getInt("color", 1);
    }
    public int getShapes()
    {
        return sharedPreferences.getInt("shape", 1);
    }
    public int getAnimal()
    {
        return sharedPreferences.getInt("animal", 1);
    }
    public boolean getGallery()
    {
        return sharedPreferences.getBoolean("galleryActive", true);
    }
    public boolean getSound()
    {
       return sharedPreferences.getBoolean("soundActive", true);
    }

    public boolean getMusic()
    {
        return sharedPreferences.getBoolean("musicActive", true);
    }
}
