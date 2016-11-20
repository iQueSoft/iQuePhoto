package net.iquesoft.iquephoto.presentation.presenter.activity;

import android.graphics.Bitmap;
import android.support.annotation.Nullable;

import net.iquesoft.iquephoto.presentation.view.activity.EditorView;
import net.iquesoft.iquephoto.ui.fragment.AddTextFragment;
import net.iquesoft.iquephoto.ui.fragment.AdjustFragment;
import net.iquesoft.iquephoto.ui.fragment.DrawingFragment;
import net.iquesoft.iquephoto.ui.fragment.FiltersFragment;
import net.iquesoft.iquephoto.ui.fragment.FramesFragment;
import net.iquesoft.iquephoto.ui.fragment.OverlayFragment;
import net.iquesoft.iquephoto.ui.fragment.StickersFragment;
import net.iquesoft.iquephoto.ui.fragment.TiltShiftFragment;
import net.iquesoft.iquephoto.ui.fragment.TransformFragment;

import javax.inject.Inject;

public class EditorActivityPresenterImpl implements EditorPresenter {

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
