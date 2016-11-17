package net.iquesoft.iquephoto.presentation.presenter.share;

import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;

import net.iquesoft.iquephoto.R;
import net.iquesoft.iquephoto.presentation.view.share.ShareView;

import javax.inject.Inject;

public class ShareActivityPresenterImpl implements IShareActivityPresenter {
    public static final String INSTAGRAM_ID = "com.instagram.android";
    public static final String FACEBOOK_ID = "com.facebook.katana";

    private ShareView mView;

    @Inject
    public ShareActivityPresenterImpl(ShareView view) {
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

    @Override
    public void shareTo(Context context, String applicationId, Bitmap bitmap) {
        if (isApplicationExist(context, applicationId)) {
            mView.share(bitmap, applicationId);
        } else {
            switch (applicationId) {
                case INSTAGRAM_ID:
                    mView.showAlert(R.string.instagram_alert, applicationId);
                    break;
                case FACEBOOK_ID:
                    mView.showAlert(R.string.facebook_alert, applicationId);
                    break;
            }
        }
    }

    private boolean isApplicationExist(Context context, String packageId) {
        boolean installed = false;

        try {
            ApplicationInfo applicationInfo =
                    context.getPackageManager().getApplicationInfo(packageId, 0);
            installed = true;
        } catch (PackageManager.NameNotFoundException e) {
            installed = false;
        }

        return installed;
    }
}
