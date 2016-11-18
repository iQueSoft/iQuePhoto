package net.iquesoft.iquephoto.presentation.presenter.fragment;

import android.graphics.Typeface;

import net.iquesoft.iquephoto.common.BaseFragmentPresenter;
import net.iquesoft.iquephoto.presentation.view.fragment.TextView;

public interface TextPresenter extends BaseFragmentPresenter<TextView> {
    void addText(String text, Typeface typeface, int color, int opacity);
}
