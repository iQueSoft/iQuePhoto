package net.iquesoft.iquephoto.presentation.views.fragment;

import android.support.annotation.StringRes;

import com.arellomobile.mvp.MvpView;
import com.arellomobile.mvp.viewstate.strategy.SingleStateStrategy;
import com.arellomobile.mvp.viewstate.strategy.SkipStrategy;
import com.arellomobile.mvp.viewstate.strategy.StateStrategyType;

import net.iquesoft.iquephoto.models.Sticker;
import net.iquesoft.iquephoto.models.StickersSet;

import java.util.ArrayList;
import java.util.List;

public interface StickersSetView extends MvpView {
    void setupAdapter(List<StickersSet> stickersSets);

    @StateStrategyType(SkipStrategy.class)
    void showStickers(@StringRes int title, ArrayList<Sticker> stickers);
}
