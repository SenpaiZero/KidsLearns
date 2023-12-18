package Helper;

import android.content.Context;
import android.content.Intent;
import android.os.CountDownTimer;
import android.util.Log;

import com.example.kidslearn.StartGame;
import com.example.kidslearn.parentalControl;

import Helper.sharedPref;

public class AppTimer {
    private static AppTimer instance;
    private CountDownTimer timer;
    private boolean isTimerRunning = false;
    private long remainingTimeInMillis;

    private AppTimer() {
        // Private constructor to prevent external instantiation
    }

    public static synchronized AppTimer getInstance() {
        if (instance == null) {
            instance = new AppTimer();
        }
        return instance;
    }

    public void startTimer(Context context) {
        sharedPref db = new sharedPref(context);
        if (!isTimerRunning) {
            timer = new CountDownTimer(db.getTimer(), 1000) {
                @Override
                public void onTick(long millisUntilFinished) {
                    remainingTimeInMillis = millisUntilFinished;
                    Log.i("Timer", remainingTimeInMillis + "");
                }

                @Override
                public void onFinish() {
                    // Start the activity when the timer finishes
                    Intent intent = new Intent(context, parentalControl.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK); // Add this flag since it's not an Activity context
                    context.startActivity(intent);
                }
            }.start();
            isTimerRunning = true;
        }
    }

    public void stopTimer() {
        if (timer != null) {
            timer.cancel();
            isTimerRunning = false;
            remainingTimeInMillis = 0;
        }
    }

    public long getRemainingTimeInMillis() {
        return remainingTimeInMillis;
    }

    public boolean isTimerRunning() {
        return isTimerRunning;
    }

    public void cancelTimer() {
        if (timer != null) {
            timer.cancel();
            isTimerRunning = false;
        }
    }

    public void resumeTimer(Context context) {
        if (!isTimerRunning) {
            startTimer(context);
        }
    }
}
