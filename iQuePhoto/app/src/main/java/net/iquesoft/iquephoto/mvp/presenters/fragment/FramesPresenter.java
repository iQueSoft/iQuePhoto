package net.iquesoft.iquephoto.mvp.presenters.fragment;

import com.arellomobile.mvp.InjectViewState;
import com.arellomobile.mvp.MvpPresenter;

import net.iquesoft.iquephoto.App;
import net.iquesoft.iquephoto.mvp.models.Frame;
import net.iquesoft.iquephoto.mvp.views.fragment.FramesView;

import java.util.List;

import javax.inject.Inject;

@InjectViewState
public class FramesPresenter extends MvpPresenter<FramesView> {
    @Inject
    List<Frame> mFrames;
    
    public FramesPresenter() {
        App.getAppComponent().inject(this);
        getViewState().setupAdapter(mFrames);
    }
}
