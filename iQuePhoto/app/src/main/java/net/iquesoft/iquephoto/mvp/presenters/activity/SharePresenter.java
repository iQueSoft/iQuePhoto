package net.iquesoft.iquephoto.mvp.presenters.activity;

import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;

import com.arellomobile.mvp.InjectViewState;
import com.arellomobile.mvp.MvpPresenter;

import net.iquesoft.iquephoto.R;
import net.iquesoft.iquephoto.mvp.views.activity.ShareView;

@InjectViewState
public class SharePresenter extends MvpPresenter<ShareView> {
    public static final String INSTAGRAM_ID = "com.instagram.android";
    public static final String FACEBOOK_ID = "com.facebook.katana";

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

        getViewState().initImageSizes(small, medium, original);
    }

    public void shareTo(Context context, String applicationId, Bitmap bitmap) {
        if (isApplicationExist(context, applicationId)) {
            getViewState().share(bitmap, applicationId);
        } else {
            switch (applicationId) {
                case INSTAGRAM_ID:
                    getViewState().showAlert(R.string.instagram_alert, applicationId);
                    break;
                case FACEBOOK_ID:
                    getViewState().showAlert(R.string.facebook_alert, applicationId);
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
