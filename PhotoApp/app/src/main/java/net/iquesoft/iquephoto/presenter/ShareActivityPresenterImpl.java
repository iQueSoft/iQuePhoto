package net.iquesoft.iquephoto.presenter;

import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;

import net.iquesoft.iquephoto.R;
import net.iquesoft.iquephoto.view.IShareActivityView;

import javax.inject.Inject;

public class ShareActivityPresenterImpl implements IShareActivityPresenter {
    private static final String INSTAGRAM_ID = "com.instagram.android";
    private static final String FACEBOOK_ID = "com.facebook.katana";

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

    @Override
    public void shareToInstagram(Context context, Bitmap bitmap) {
        if (isApplicationExist(context, INSTAGRAM_ID)) {
            mView.shareToInstagram(bitmap);
        } else {
            mView.showAlert(R.string.instagram_alert, INSTAGRAM_ID);
        }
    }

    @Override
    public void shareToFacebook(Context context, Bitmap bitmap) {
        if (isApplicationExist(context, FACEBOOK_ID)) {
            mView.shareToFacebook(bitmap);
        } else {
            mView.showAlert(R.string.facebook_alert, FACEBOOK_ID);
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
