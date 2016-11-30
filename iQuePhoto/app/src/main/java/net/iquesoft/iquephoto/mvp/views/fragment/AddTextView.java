package net.iquesoft.iquephoto.mvp.views.fragment;

import android.support.annotation.StringRes;

import com.arellomobile.mvp.MvpView;

public interface AddTextView extends MvpView {
    void showToastMessage(@StringRes int messageText);
}
