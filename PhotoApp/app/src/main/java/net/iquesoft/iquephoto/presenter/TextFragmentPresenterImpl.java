package net.iquesoft.iquephoto.presenter;

import android.graphics.Typeface;

import net.iquesoft.iquephoto.model.Text;
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
    public void addText(String text, int color, Typeface typeface, int opacity) {
        Text photoEditorText = new Text();
        photoEditorText.setTypeface(typeface);
        photoEditorText.setText(text);
        photoEditorText.setColor(color);
        photoEditorText.setOpacity(opacity);

        view.onAddTextComplete(photoEditorText);
    }

    @Override
    public void deleteText(Text text) {
        view.onDeleteTextComplete(text);
    }
}
