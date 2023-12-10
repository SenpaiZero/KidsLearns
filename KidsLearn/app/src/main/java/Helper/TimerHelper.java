package Helper;

import android.os.CountDownTimer;

import android.os.CountDownTimer;

public class TimerHelper {
    private CountDownTimer countDownTimer;
    private OnTimerTickListener tickListener;
    private OnTimerFinishedListener finishListener;

    public TimerHelper(long millisInFuture, long countDownInterval) {
        countDownTimer = new CountDownTimer(millisInFuture, countDownInterval) {
            public void onTick(long millisUntilFinished) {
                if (tickListener != null) {
                    tickListener.onTick(millisUntilFinished / 1000);
                }
            }

            public void onFinish() {
                if (finishListener != null) {
                    finishListener.onTimerFinished();
                }
            }
        }.start();
    }

    public void setOnTimerTickListener(OnTimerTickListener listener) {
        this.tickListener = listener;
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
        }
    }
}

