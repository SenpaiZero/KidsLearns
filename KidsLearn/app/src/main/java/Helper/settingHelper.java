package Helper;

import android.content.Context;

public class settingHelper {

    static boolean isExempted, isInstance;
    static long ms;

    public static void setExemption(boolean isExempt)
    {
        isExempted = isExempt;
    }
    public static boolean getExemption()
    {
        return isExempted;
    }

    public static void setIsInstance(boolean isIns)
    {
        isInstance = isIns;
    }

    public static boolean getIsInstance()
    {
        return isInstance;
    }
    public static void setMs(long ms_)
    {
        ms = ms_;
    }
    public static long getMs()
    {
        return ms;
    }

}
