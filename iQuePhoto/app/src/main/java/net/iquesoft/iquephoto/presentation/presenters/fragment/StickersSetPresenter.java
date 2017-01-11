package net.iquesoft.iquephoto.presentation.presenters.fragment;

import android.content.Context;
import android.content.res.Resources;
import android.support.annotation.NonNull;
import android.support.v4.content.ContextCompat;
import android.support.v4.content.res.ResourcesCompat;

import com.arellomobile.mvp.InjectViewState;
import com.arellomobile.mvp.MvpPresenter;

import net.iquesoft.iquephoto.App;
import net.iquesoft.iquephoto.models.Sticker;
import net.iquesoft.iquephoto.models.StickersSet;
import net.iquesoft.iquephoto.presentation.views.fragment.StickersSetView;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

@InjectViewState
public class StickersSetPresenter extends MvpPresenter<StickersSetView> {
    @Inject
    List<StickersSet> stickersSets;

    public StickersSetPresenter() {
        App.getAppComponent().inject(this);
        getViewState().setupAdapter(stickersSets);
    }

    public void stickersSetClicked(StickersSet stickersSet) {
        ArrayList<Sticker> stickers = new ArrayList<>(stickersSet.getStickers());

        getViewState().showStickers(stickersSet.getTitle(), stickers);
    }
}
