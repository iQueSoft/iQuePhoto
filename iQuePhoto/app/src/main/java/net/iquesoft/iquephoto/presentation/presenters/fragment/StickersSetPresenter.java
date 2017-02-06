package net.iquesoft.iquephoto.presentation.presenters.fragment;

import com.arellomobile.mvp.InjectViewState;
import com.arellomobile.mvp.MvpPresenter;

import net.iquesoft.iquephoto.App;
import net.iquesoft.iquephoto.models.Sticker;
import net.iquesoft.iquephoto.models.StickersSet;
import net.iquesoft.iquephoto.presentation.views.fragment.StickersSetView;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import dagger.Lazy;

@InjectViewState
public class StickersSetPresenter extends MvpPresenter<StickersSetView> {
    @Inject
    Lazy<List<StickersSet>> mStickersSets;

    public StickersSetPresenter() {
        App.getAppComponent().inject(this);
        getViewState().setupAdapter(mStickersSets.get());
    }

    public void stickersSetClicked(StickersSet stickersSet) {
        ArrayList<Sticker> stickers = new ArrayList<>(stickersSet.getStickers());

        getViewState().showStickers(stickersSet.getTitle(), stickers);
    }
}