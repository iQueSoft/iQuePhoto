package net.iquesoft.iquephoto.mvp.presenters.fragment;

import com.arellomobile.mvp.InjectViewState;
import com.arellomobile.mvp.MvpPresenter;

import net.iquesoft.iquephoto.mvp.models.Frame;
import net.iquesoft.iquephoto.mvp.views.fragment.FramesView;

import java.util.List;

@InjectViewState
public class FramesPresenter extends MvpPresenter<FramesView> {
    private List<Frame> mFrameList = Frame.getFramesList();

    public FramesPresenter() {
        getViewState().setupAdapter(mFrameList);
    }
}
