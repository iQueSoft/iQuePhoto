package net.iquesoft.iquephoto.presenter;

import android.graphics.Bitmap;

import net.iquesoft.iquephoto.view.IShareActivityView;

import javax.inject.Inject;

public class ShareActivityPresenterImpl implements IShareActivityPresenter {

    private IShareActivityView mView;

    @Inject
    public ShareActivityPresenterImpl(IShareActivityView view) {
        mView = view;
    }

    @Override
    public void calculateSizesForCompressing(Bitmap bitmap) {
        int originalImageHeight = bitmap.getHeight();
        int originalImageWidth = bitmap.getWidth();

        int mediumImageHeight = originalImageHeight / 2;
        int mediumImageWidth = originalImageWidth / 2;

        int smallImageHeight = originalImageHeight / 3;
        int smallImageWidth = originalImageWidth / 3;

        String original = String.valueOf(originalImageWidth) + "x"
                + String.valueOf(originalImageHeight);

        String medium = String.valueOf(mediumImageWidth) + "x"
                + String.valueOf(mediumImageHeight);

        String small = String.valueOf(smallImageWidth) + "x"
                + String.valueOf(smallImageHeight);

        mView.initImageSizes(small, medium, original);
    }
}
