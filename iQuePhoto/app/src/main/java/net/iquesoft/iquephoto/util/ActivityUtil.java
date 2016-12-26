package net.iquesoft.iquephoto.util;

import android.support.annotation.NonNull;
import android.support.annotation.StringRes;
import android.support.v4.app.FragmentActivity;

import net.iquesoft.iquephoto.ui.activities.EditorActivity;

public class ActivityUtil {
    public static void updateToolbarTitle(@StringRes int title, @NonNull FragmentActivity fragmentActivity) {
        EditorActivity editorActivity = (EditorActivity) fragmentActivity;
        if (editorActivity.getSupportActionBar() != null) {
            editorActivity.getSupportActionBar().setTitle(title);
        }
    }
}
