package net.iquesoft.iquephoto.presenter;

import android.graphics.Typeface;
import android.util.DisplayMetrics;

import net.iquesoft.iquephoto.core.Text;
import net.iquesoft.iquephoto.common.BaseFragmentPresenter;
import net.iquesoft.iquephoto.view.ITextFragmentView;

interface ITextFragmentPresenter extends BaseFragmentPresenter<ITextFragmentView> {
    void addText(String text, int color, Typeface typeface, int opacity);
}
