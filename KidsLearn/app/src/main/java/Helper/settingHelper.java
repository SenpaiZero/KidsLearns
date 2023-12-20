package Helper;

public class settingHelper {

    static boolean isExempted;
    public static void setExemption(boolean isExempt)
    {
        isExempted = isExempt;
    }
    public static boolean getExemption()
    {
        return isExempted;
    }
}
