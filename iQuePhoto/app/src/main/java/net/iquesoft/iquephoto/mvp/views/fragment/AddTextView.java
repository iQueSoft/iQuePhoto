package net.iquesoft.iquephoto.mvp.views.fragment;

import android.support.annotation.StringRes;

import com.arellomobile.mvp.MvpView;

import net.iquesoft.iquephoto.mvp.models.Text;

public interface AddTextView extends MvpView {
    void addText(Text text);

    void showToastMessage(@StringRes int messageText);
}
