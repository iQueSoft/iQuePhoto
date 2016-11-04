package net.iquesoft.iquephoto.presenter;

import android.graphics.Bitmap;
import android.support.annotation.Nullable;

import net.iquesoft.iquephoto.view.IEditorActivityView;

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
