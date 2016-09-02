package net.iquesoft.iquephoto.presenter;

import android.graphics.Typeface;

import net.iquesoft.iquephoto.PhotoEditorText;
import net.iquesoft.iquephoto.common.BaseFragmentPresenter;
import net.iquesoft.iquephoto.view.ITextFragmentView;

public interface ITextFragmentPresenter extends BaseFragmentPresenter<ITextFragmentView> {
    void addText(String text, int color, Typeface typeface, int opacity);

    void deleteText(PhotoEditorText photoEditorText);
}
