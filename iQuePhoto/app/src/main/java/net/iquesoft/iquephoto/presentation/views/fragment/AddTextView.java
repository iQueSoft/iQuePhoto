package net.iquesoft.iquephoto.presentation.views.fragment;

import android.support.annotation.ColorInt;
import android.support.annotation.StringRes;

import com.arellomobile.mvp.MvpView;

import net.iquesoft.iquephoto.models.Text;

public interface AddTextView extends MvpView {
    void addText(Text text);

    void onTextColorChanged(@ColorInt int color);

    void showToastMessage(@StringRes int messageText);
}
