package Helper;

import android.os.CountDownTimer;

import android.os.CountDownTimer;

public class TimerHelper {
    private CountDownTimer countDownTimer;
    private long timeRemaining;
    private OnTimerTickListener tickListener;
    private OnTimerFinishedListener finishListener;
    private boolean isTimerRunning = false;

    public TimerHelper(long millisInFuture, long countDownInterval) {
        timeRemaining = millisInFuture;
        startTimer();
    }

    public void setOnTimerTickListener(OnTimerTickListener listener) {
        this.tickListener = listener;
    }
    private void startTimer() {
        countDownTimer = new CountDownTimer(timeRemaining, 1000) {
            public void onTick(long millisUntilFinished) {
                timeRemaining = millisUntilFinished; // Update the remaining time
                if (tickListener != null) {
                    tickListener.onTick(millisUntilFinished / 1000);
                }
            }

            public void onFinish() {
                isTimerRunning = false;
                if (finishListener != null) {
                    finishListener.onTimerFinished();
                }
            }
        };
        countDownTimer.start();
        isTimerRunning = true;
    }
    public void setOnTimerFinishedListener(OnTimerFinishedListener listener) {
        this.finishListener = listener;
    }

    public interface OnTimerTickListener {
        void onTick(long secondsRemaining);
    }

    public interface OnTimerFinishedListener {
        void onTimerFinished();
    }

    public void cancelTimer() {
        if (countDownTimer != null) {
            countDownTimer.cancel();
            isTimerRunning = false;
        }
    }

    public void resumeTimer() {
        if (!isTimerRunning) {
            startTimer();
        }
    }

}

