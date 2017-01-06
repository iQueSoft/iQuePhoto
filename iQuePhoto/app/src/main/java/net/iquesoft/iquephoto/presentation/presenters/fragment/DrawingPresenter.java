package net.iquesoft.iquephoto.presentation.presenters.fragment;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v4.content.res.ResourcesCompat;

import com.arellomobile.mvp.InjectViewState;
import com.arellomobile.mvp.MvpPresenter;

import net.iquesoft.iquephoto.R;
import net.iquesoft.iquephoto.models.EditorColor;
import net.iquesoft.iquephoto.presentation.views.fragment.DrawingView;

@InjectViewState
public class DrawingPresenter extends MvpPresenter<DrawingView> {
    /*@Override
    public void init(DrawingView view) {
        mView = view;
        mEditorView.setEditorCommand(DRAWING);
    }*/

    public void changeBrushColor(@NonNull Context context, EditorColor editorColor) {
        int color = ResourcesCompat.getColor(context.getResources(), editorColor.getColor(), null);

        getViewState().onBrushColorChanged(color);
    }
}
