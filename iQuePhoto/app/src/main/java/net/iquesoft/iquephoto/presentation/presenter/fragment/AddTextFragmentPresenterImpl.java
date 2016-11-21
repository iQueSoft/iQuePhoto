package net.iquesoft.iquephoto.presentation.presenter.fragment;

import android.graphics.Typeface;

import net.iquesoft.iquephoto.R;
import net.iquesoft.iquephoto.model.Text;
import net.iquesoft.iquephoto.presentation.presenter.fragment.interfaces.AddTextPresenter;
import net.iquesoft.iquephoto.presentation.view.activity.EditorView;
import net.iquesoft.iquephoto.presentation.view.fragment.AddTextView;

import javax.inject.Inject;

import static net.iquesoft.iquephoto.core.editor.enums.EditorCommand.TEXT;

public class AddTextFragmentPresenterImpl implements AddTextPresenter {

    private AddTextView mView;
    private EditorView mEditorView;

    @Inject
    public AddTextFragmentPresenterImpl(EditorView editorView) {
        mEditorView = editorView;
    }

    @Override
    public void init(AddTextView view) {
        mView = view;
        mEditorView.setEditorCommand(TEXT);
    }

    @Override
    public void addText(String text, Typeface typeface, int color, int opacity) {
        if (!text.isEmpty()) {
            int opacityValue = opacity * (int) 2.5f;
            Text texts = new Text(text, typeface, color, opacityValue);
            mEditorView.addTextToEditor(texts);
        } else
            mView.showToastMessage(R.string.text_is_empty);
    }
}
