package net.iquesoft.iquephoto.presenter.fragment.interfaces;

import net.iquesoft.iquephoto.common.BaseFragmentPresenter;
import net.iquesoft.iquephoto.core.EditorCommand;
import net.iquesoft.iquephoto.view.fragment.interfaces.ISliderControlFragmentView;
import net.iquesoft.iquephoto.view.fragment.interfaces.ITransformFragmentView;

public interface ISliderControlFragmentPresenter extends BaseFragmentPresenter<ISliderControlFragmentView> {
    void setCommand(EditorCommand command);
}
