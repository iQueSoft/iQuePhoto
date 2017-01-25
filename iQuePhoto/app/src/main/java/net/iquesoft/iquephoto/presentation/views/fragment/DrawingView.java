package net.iquesoft.iquephoto.presentation.views.fragment;

import android.support.annotation.ColorInt;

import com.arellomobile.mvp.MvpView;

import net.iquesoft.iquephoto.models.EditorColor;

import java.util.List;

public interface DrawingView extends MvpView {
    void setupColorsAdapter(List<EditorColor> colors);

    void onBrushColorChanged(@ColorInt int color);
}
