package Helper;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ObjectAnimator;
import android.app.Activity;
import android.graphics.Color;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import com.example.kidslearn.R;

public class userInterfaceHelper extends AppCompatActivity {

    Activity activity;

    CardView customCardView;
    public userInterfaceHelper(Activity activity)
    {
        this.activity = activity;
        customCardView = activity.findViewById(R.id.card_view);
        if(customCardView != null) customCardView.setVisibility(View.GONE);

        activity.overridePendingTransition(R.anim.fadein, R.anim.fadeout);
    }

    public userInterfaceHelper(Activity activity, boolean isTransition)
    {

        this.activity = activity;
        customCardView = activity.findViewById(R.id.card_view);
        if(customCardView != null) customCardView.setVisibility(View.GONE);

        if(isTransition)
            activity.overridePendingTransition(R.anim.fadein, R.anim.fadeout);
    }
    public void removeActionbar()
    {
        try
        {
            ((AppCompatActivity)activity).getSupportActionBar().hide();
        } catch (Exception ex)
        {
            try {
                activity.getActionBar().hide();
            } catch (Exception exx)
            {
                Log.e("Action Bar", exx.getMessage());
            }
        }
    }

    public void transparentStatusBar()
    {
        try {
            Window window = activity.getWindow();
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN);
            window.setStatusBarColor(Color.TRANSPARENT);
        } catch (Exception ex)
        {
            Log.e("Transparent Status Bar", ex.getMessage());
        }

    }

    public void showCustomToast(String message) {
        final TextView toastText = customCardView.findViewById(R.id.toast_text);
        toastText.setText(message);

        // Show the CardView
        customCardView.setVisibility(View.VISIBLE);

        // Create a handler to delay hiding the CardView
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                // Create a fade-out animation for the CardView
                ObjectAnimator fadeOut = ObjectAnimator.ofFloat(customCardView, "alpha", 1f, 0f);
                fadeOut.setDuration(1000); // Set the duration of the fade-out animation (1 second)

                // Set a listener to hide the CardView when the animation ends
                fadeOut.addListener(new AnimatorListenerAdapter() {
                    @Override
                    public void onAnimationEnd(Animator animation) {
                        super.onAnimationEnd(animation);
                        customCardView.setVisibility(View.GONE);
                    }
                });

                // Start the fade-out animation
                fadeOut.start();
            }
        }, 200); // Shorter delay before hiding (100 milliseconds)
    }

    public void setFullscreen()
    {
        activity.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
    }
}
