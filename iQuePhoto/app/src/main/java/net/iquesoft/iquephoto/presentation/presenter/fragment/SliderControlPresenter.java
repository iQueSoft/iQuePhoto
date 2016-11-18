package net.iquesoft.iquephoto.presentation.presenter.fragment;

import net.iquesoft.iquephoto.common.BaseFragmentPresenter;
import net.iquesoft.iquephoto.core.EditorCommand;
import net.iquesoft.iquephoto.presentation.view.fragment.SliderControlView;

public interface SliderControlPresenter extends BaseFragmentPresenter<SliderControlView> {
    void setCommand(EditorCommand command);
}
