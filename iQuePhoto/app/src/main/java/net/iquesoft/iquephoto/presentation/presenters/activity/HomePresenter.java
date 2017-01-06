package net.iquesoft.iquephoto.presentation.presenters.activity;

import com.arellomobile.mvp.InjectViewState;
import com.arellomobile.mvp.MvpPresenter;

import net.iquesoft.iquephoto.presentation.views.activity.HomeView;

@InjectViewState
public class HomePresenter extends MvpPresenter<HomeView> {

    public void openCamera() {
        getViewState().startCamera();
    }

    public void openGallery() {
        getViewState().startGallery();
    }
}
