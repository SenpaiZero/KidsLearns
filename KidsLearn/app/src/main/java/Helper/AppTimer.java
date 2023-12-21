package Helper;

import android.content.Context;
import android.content.Intent;
import android.os.CountDownTimer;
import android.util.Log;

import com.example.kidslearn.StartGame;
import com.example.kidslearn.gameTimeup;
import com.example.kidslearn.parentalControl;

import Helper.sharedPref;

public class AppTimer {
    private static AppTimer instance;
    private CountDownTimer timer;
    private boolean isTimerRunning = false;
    private long remainingTimeInMillis;
    static sharedPref db;
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
        db = new sharedPref(context);
        if(db.getIsTimer())
        {
            if (!isTimerRunning) {
                timer = new CountDownTimer(settingHelper.getMs(), 1000) {
                    @Override
                    public void onTick(long millisUntilFinished) {
                        remainingTimeInMillis = millisUntilFinished;
                        settingHelper.setMs(millisUntilFinished);
                        Log.i("Timer", settingHelper.getMs() + "");

                        if(settingHelper.getMs() <= 1000)
                        {
                            Intent intent = new Intent(context, gameTimeup.class);
                            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK); // Add this flag since it's not an Activity context

                            Log.i("Timer Finish", remainingTimeInMillis + "");
                            Log.i("Timer Finish sharedPref", db.getRemainingTimer() + "");
                            if(!settingHelper.getExemption())
                            {
                                context.startActivity(intent);
                            }
                        }
                    }

                    @Override
                    public void onFinish() {
                        isTimerRunning = false;
                        db.setRemainingTime(0);
                    }
                }.start();
                isTimerRunning = true;
            }
        }
    }

    public void stopTimer() {
        if (timer != null && isTimerRunning) {
            db.setRemainingTime(settingHelper.getMs()); // Update with remainingTimeInMillis
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
