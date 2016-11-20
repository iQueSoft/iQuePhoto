package net.iquesoft.iquephoto.presentation.presenter.fragment;

import net.iquesoft.iquephoto.common.BaseFragmentPresenter;
import net.iquesoft.iquephoto.core.EditorCommand;
import net.iquesoft.iquephoto.presentation.view.fragment.TransformVerticalView;

public interface TransformVerticalPresenter extends BaseFragmentPresenter<TransformVerticalView> {
    void setupTransform(EditorCommand editorCommand);
}
