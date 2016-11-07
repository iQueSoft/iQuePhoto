package net.iquesoft.iquephoto.presenter.activity;

import android.graphics.Bitmap;
import android.support.annotation.Nullable;

import net.iquesoft.iquephoto.presenter.activity.interfaces.IEditorActivityPresenter;
import net.iquesoft.iquephoto.view.activity.interfaces.IEditorActivityView;

import javax.inject.Inject;

public class EditorActivityPresenterImpl implements IEditorActivityPresenter {

    private IEditorActivityView mView;

    @Inject
    public EditorActivityPresenterImpl(IEditorActivityView view) {
        mView = view;
    }

    @Override
    public void onBackPressed(Bitmap bitmap, @Nullable Bitmap alteredBitmap) {
        if (alteredBitmap != null) {
            if (!bitmap.sameAs(alteredBitmap))
                mView.showAlertDialog();
            else mView.navigateBack(false);
        } else {
            mView.navigateBack(false);
        }
    }
}
