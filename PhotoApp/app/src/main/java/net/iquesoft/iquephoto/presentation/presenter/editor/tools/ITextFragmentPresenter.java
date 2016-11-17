package net.iquesoft.iquephoto.presentation.presenter.editor.tools;

import android.graphics.Typeface;

import net.iquesoft.iquephoto.common.BaseFragmentPresenter;
import net.iquesoft.iquephoto.presentation.view.editor.tools.TextView;

public interface ITextFragmentPresenter extends BaseFragmentPresenter<TextView> {
    void addText(String text, Typeface typeface, int color, int opacity);
}
