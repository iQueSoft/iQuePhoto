package net.iquesoft.iquephoto.presentation.presenters.fragment;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v4.content.res.ResourcesCompat;

import com.arellomobile.mvp.InjectViewState;
import com.arellomobile.mvp.MvpPresenter;

import net.iquesoft.iquephoto.App;
import net.iquesoft.iquephoto.models.BrushSize;
import net.iquesoft.iquephoto.models.EditorColor;
import net.iquesoft.iquephoto.presentation.views.fragment.DrawingView;

import java.util.List;

import javax.inject.Inject;

@InjectViewState
public class DrawingPresenter extends MvpPresenter<DrawingView> {
    @Inject
    List<BrushSize> mSizes;

    @Inject
    List<EditorColor> mColors;

    public DrawingPresenter() {
        App.getAppComponent().inject(this);

        getViewState().setupSizesAdapter(mSizes);
        getViewState().setupColorsAdapter(mColors);
    }

    public void changeBrushColor(@NonNull Context context, EditorColor editorColor) {
        int color = ResourcesCompat.getColor(context.getResources(), editorColor.getColor(), null);

        getViewState().onBrushColorChanged(color);
    }
}