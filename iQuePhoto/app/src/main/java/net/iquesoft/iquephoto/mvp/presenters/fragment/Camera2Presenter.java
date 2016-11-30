package net.iquesoft.iquephoto.mvp.presenters.fragment;

import com.arellomobile.mvp.InjectViewState;
import com.arellomobile.mvp.MvpPresenter;

import net.iquesoft.iquephoto.mvp.views.fragment.Camera2FragmentView;

@InjectViewState
public class Camera2Presenter extends MvpPresenter<Camera2FragmentView> {

    public void takePhoto() {
        getViewState().takePhoto();
    }
}
