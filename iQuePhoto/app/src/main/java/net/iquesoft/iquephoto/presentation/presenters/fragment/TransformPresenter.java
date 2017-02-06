package net.iquesoft.iquephoto.presentation.presenters.fragment;

import com.arellomobile.mvp.InjectViewState;
import com.arellomobile.mvp.MvpPresenter;

import net.iquesoft.iquephoto.core.enums.EditorTool;
import net.iquesoft.iquephoto.presentation.views.fragment.TransformView;

@InjectViewState
public class TransformPresenter extends MvpPresenter<TransformView> {

    public void setupTransform(EditorTool editorTool) {
        // TODO: mEditorView.setupToolFragment(editorTool);
    }
}