package net.iquesoft.iquephoto.presentation.presenters.fragment;

import com.arellomobile.mvp.InjectViewState;
import com.arellomobile.mvp.MvpPresenter;

import net.iquesoft.iquephoto.App;
import net.iquesoft.iquephoto.models.Sticker;
import net.iquesoft.iquephoto.models.StickersSet;
import net.iquesoft.iquephoto.presentation.views.fragment.StickersView;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

@InjectViewState
public class StickersPresenter extends MvpPresenter<StickersView> {
    @Inject
    List<StickersSet> stickersSets;

    private List<Sticker> mStickers;

    public StickersPresenter() {
        App.getAppComponent().inject(this);
    }

    public void setupStickersSet(int position) {
        mStickers = new ArrayList<>(stickersSets.get(position).getStickers());

        getViewState().setupAdapter(mStickers);
    }
}
