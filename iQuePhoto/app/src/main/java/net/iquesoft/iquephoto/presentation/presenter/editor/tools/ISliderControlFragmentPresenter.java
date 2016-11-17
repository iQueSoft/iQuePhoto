package net.iquesoft.iquephoto.presentation.presenter.editor.tools;

import net.iquesoft.iquephoto.common.BaseFragmentPresenter;
import net.iquesoft.iquephoto.core.EditorCommand;
import net.iquesoft.iquephoto.presentation.view.editor.tools.SliderControlView;

public interface ISliderControlFragmentPresenter extends BaseFragmentPresenter<SliderControlView> {
    void setCommand(EditorCommand command);
}
