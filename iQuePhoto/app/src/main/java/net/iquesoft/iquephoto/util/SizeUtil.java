package net.iquesoft.iquephoto.util;

import net.iquesoft.iquephoto.App;

public class SizeUtil {
    public static float dp2px(float dp) {
        return dp * getDensity();
    }

    public static float px2dp(float px) {
        return px / getDensity();
    }

    private static float getDensity() {
        return App.getAppComponent()
                .getContext()
                .getResources()
                .getDisplayMetrics()
                .density;
    }
}