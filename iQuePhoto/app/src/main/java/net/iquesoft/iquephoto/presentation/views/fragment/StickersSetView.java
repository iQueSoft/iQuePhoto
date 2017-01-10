package net.iquesoft.iquephoto.presentation.views.fragment;

import com.arellomobile.mvp.MvpView;

import net.iquesoft.iquephoto.models.Sticker;
import net.iquesoft.iquephoto.models.StickersSet;

import java.util.ArrayList;
import java.util.List;

public interface StickersSetView extends MvpView {
    void setupAdapter(List<StickersSet> stickersSets);

    void showStickers(ArrayList<Sticker> stickers);
}
