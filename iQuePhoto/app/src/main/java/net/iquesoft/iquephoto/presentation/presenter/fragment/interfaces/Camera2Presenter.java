package net.iquesoft.iquephoto.presentation.presenter.fragment.interfaces;

import net.iquesoft.iquephoto.common.BaseFragmentPresenter;
import net.iquesoft.iquephoto.presentation.view.fragment.Camera2FragmentView;

public interface Camera2Presenter extends BaseFragmentPresenter<Camera2FragmentView> {
    void takePhoto();
}
