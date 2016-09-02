package net.iquesoft.iquephoto.presenter;

import android.graphics.Color;
import android.graphics.Typeface;

import net.iquesoft.iquephoto.PhotoEditorText;
import net.iquesoft.iquephoto.view.ITextFragmentView;

import javax.inject.Inject;

public class TextFragmentPresenterImpl implements ITextFragmentPresenter {

    private ITextFragmentView view;

    @Inject
    public TextFragmentPresenterImpl() {

    }

    @Override
    public void init(ITextFragmentView view) {
        this.view = view;
    }

    @Override
    public void addText(String text, int color, Typeface typeface) {
        PhotoEditorText photoEditorText = new PhotoEditorText();
        photoEditorText.setTypeface(typeface);
        photoEditorText.setText(text);
        photoEditorText.setColor(color);

        view.onAddComplete(photoEditorText);
    }
}
