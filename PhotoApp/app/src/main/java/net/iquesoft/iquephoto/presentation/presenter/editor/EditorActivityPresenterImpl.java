package net.iquesoft.iquephoto.presentation.presenter.editor;

import android.graphics.Bitmap;
import android.support.annotation.Nullable;

import net.iquesoft.iquephoto.presentation.view.editor.EditorView;

import javax.inject.Inject;

public class EditorActivityPresenterImpl implements IEditorActivityPresenter {

    private EditorView mView;

    @Inject
    public EditorActivityPresenterImpl(EditorView view) {
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
