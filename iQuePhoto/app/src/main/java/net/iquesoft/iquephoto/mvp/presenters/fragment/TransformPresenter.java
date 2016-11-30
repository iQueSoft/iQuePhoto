package net.iquesoft.iquephoto.mvp.presenters.fragment;

import com.arellomobile.mvp.InjectViewState;
import com.arellomobile.mvp.MvpPresenter;

import net.iquesoft.iquephoto.core.editor.enums.EditorCommand;
import net.iquesoft.iquephoto.mvp.views.fragment.TransformView;

@InjectViewState
public class TransformPresenter extends MvpPresenter<TransformView> {

    public void setupTransform(EditorCommand editorCommand) {
        // TODO: mEditorView.setupToolFragment(editorCommand);
    }
}
