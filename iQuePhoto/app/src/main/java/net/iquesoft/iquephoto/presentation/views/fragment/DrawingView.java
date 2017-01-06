package net.iquesoft.iquephoto.presentation.views.fragment;

import android.support.annotation.ColorInt;

import com.arellomobile.mvp.MvpView;

public interface DrawingView extends MvpView {
    void onBrushColorChanged(@ColorInt int color);
}
