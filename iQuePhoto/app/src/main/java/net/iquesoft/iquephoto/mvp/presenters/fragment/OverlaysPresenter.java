package net.iquesoft.iquephoto.mvp.presenters.fragment;

import com.arellomobile.mvp.InjectViewState;
import com.arellomobile.mvp.MvpPresenter;

import net.iquesoft.iquephoto.mvp.models.Overlay;
import net.iquesoft.iquephoto.mvp.views.fragment.OverlaysView;

import java.util.List;

@InjectViewState
public class OverlaysPresenter extends MvpPresenter<OverlaysView> {
    private List<Overlay> mOverlays = Overlay.getOverlaysList();

    @Override
    protected void onFirstViewAttach() {
        super.onFirstViewAttach();
        getViewState().setupAdapter(mOverlays);
    }
}
