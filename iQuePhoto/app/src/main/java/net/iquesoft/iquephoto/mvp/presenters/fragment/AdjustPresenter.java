package net.iquesoft.iquephoto.mvp.presenters.fragment;

import com.arellomobile.mvp.InjectViewState;
import com.arellomobile.mvp.MvpPresenter;

import net.iquesoft.iquephoto.App;
import net.iquesoft.iquephoto.mvp.models.Adjust;
import net.iquesoft.iquephoto.mvp.views.fragment.AdjustView;

import java.util.List;

import javax.inject.Inject;

@InjectViewState
public class AdjustPresenter extends MvpPresenter<AdjustView> {
    @Inject
    List<Adjust> mAdjusts;

    public AdjustPresenter() {
        App.getAppComponent().inject(this);
        getViewState().setupAdapter(mAdjusts);
    }
}