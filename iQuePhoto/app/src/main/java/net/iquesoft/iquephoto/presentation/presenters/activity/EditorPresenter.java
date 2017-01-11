package net.iquesoft.iquephoto.presentation.presenters.activity;

import android.graphics.Bitmap;
import android.support.annotation.Nullable;

import com.arellomobile.mvp.InjectViewState;
import com.arellomobile.mvp.MvpPresenter;

import net.iquesoft.iquephoto.presentation.views.activity.EditorView;

@InjectViewState
public class EditorPresenter extends MvpPresenter<EditorView> {

    /*public void onBackPressed(Bitmap bitmap, @Nullable Bitmap alteredBitmap) {
        if (alteredBitmap != null) {
            if (!bitmap.sameAs(alteredBitmap)) {
                getViewState().showAlertDialog();
            } else {
                getViewState().navigateBack(false);
            }
        } else {
            getViewState().navigateBack(false);
        }
    }*/
}
