package net.iquesoft.iquephoto.mvp.presenters.fragment;

import com.arellomobile.mvp.InjectViewState;
import com.arellomobile.mvp.MvpPresenter;

import net.iquesoft.iquephoto.App;
import net.iquesoft.iquephoto.mvp.models.Overlay;
import net.iquesoft.iquephoto.mvp.views.fragment.OverlaysView;

import java.util.List;

import javax.inject.Inject;

@InjectViewState
public class OverlaysPresenter extends MvpPresenter<OverlaysView> {
    @Inject
    List<Overlay> mOverlays;

    public OverlaysPresenter() {
        App.getAppComponent().inject(this);
        getViewState().setupAdapter(mOverlays);
    }
}
