package net.iquesoft.iquephoto.presentation.presenter.fragment;

import android.graphics.Typeface;

import net.iquesoft.iquephoto.R;
import net.iquesoft.iquephoto.model.Text;
import net.iquesoft.iquephoto.presentation.view.activity.EditorView;
import net.iquesoft.iquephoto.presentation.view.fragment.TextView;

import javax.inject.Inject;

public class TextFragmentPresenterImpl implements TextPresenter {

    private TextView mView;
    private EditorView mEditorActivityView;

    @Inject
    public TextFragmentPresenterImpl(EditorView editorActivityView) {
        mEditorActivityView = editorActivityView;
    }

    @Override
    public void init(TextView view) {
        mView = view;
    }

    @Override
    public void addText(String text, Typeface typeface, int color, int opacity) {
        if (!text.isEmpty()) {
            int opacityValue = opacity * (int) 2.5f;
            Text texts = new Text(text, typeface, color, opacityValue);
            mEditorActivityView.addTextToEditor(texts);
        } else
            mView.showToastMessage(R.string.text_is_empty);
    }
}
