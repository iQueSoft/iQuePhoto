package net.iquesoft.iquephoto.mvp.presenters.fragment;

import com.arellomobile.mvp.InjectViewState;
import com.arellomobile.mvp.MvpPresenter;

import net.iquesoft.iquephoto.mvp.models.Adjust;
import net.iquesoft.iquephoto.mvp.views.fragment.AdjustView;

import java.util.List;

@InjectViewState
public class AdjustPresenter extends MvpPresenter<AdjustView> {
    private List<Adjust> mAdjusts = Adjust.getAdjustList();

    @Override
    protected void onFirstViewAttach() {
        super.onFirstViewAttach();
        getViewState().setupAdapter(mAdjusts);
    }
}