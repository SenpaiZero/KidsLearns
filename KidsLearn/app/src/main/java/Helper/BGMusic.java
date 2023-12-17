package Helper;

import android.app.Activity;
import android.content.Intent;

public class BGMusic {
    static boolean isExit;
    public static void startBG(Activity activity)
    {
        if(new sharedPref(activity).getMusic())
            activity.startService(new Intent(activity, MusicServiceBackgroundNormal.class));
    }

    public static void stopBG(Activity activity)
    {
        activity.stopService(new Intent(activity, MusicServiceBackgroundNormal.class));
    }
}
