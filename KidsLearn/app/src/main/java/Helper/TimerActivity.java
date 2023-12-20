package Helper;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

public class TimerActivity extends AppCompatActivity {
    private AppTimer appTimer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        appTimer = AppTimer.getInstance();
        // Start the timer when the activity is created (you might adjust timings)
        appTimer.startTimer(this);
    }
    public void stopTimer()
    {
        appTimer.stopTimer();
    }

    public void startTimer()
    {
        appTimer = AppTimer.getInstance();
        // Start the timer when the activity is created (you might adjust timings)
        appTimer.startTimer(this);
    }
    @Override
    protected void onPause() {
        super.onPause();
        new sharedPref(this).setRemainingTime(appTimer.getRemainingTimeInMillis());
        appTimer.cancelTimer();
    }

    @Override
    protected void onResume() {
        super.onResume();
        new sharedPref(this).setRemainingTime(appTimer.getRemainingTimeInMillis());
        appTimer.resumeTimer(this);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        // Stop the timer when the activity is destroyed
        new sharedPref(this).setRemainingTime(appTimer.getRemainingTimeInMillis());
        appTimer.stopTimer();
    }
}
