package net.iquesoft.iquephoto.core;

import android.content.Context;
import android.graphics.Bitmap;
import android.os.AsyncTask;
import android.support.annotation.StringRes;

import com.afollestad.materialdialogs.MaterialDialog;

import net.iquesoft.iquephoto.utils.BitmapUtil;

class BitmapScaleTask extends AsyncTask<Integer, Void, Bitmap> {
    private Context mContext;
    private int mReqWidth, mReqHeight;
    private MaterialDialog mProgressDialog;
    private ImageEditorView mImageEditorView;

    BitmapScaleTask(Context context, ImageEditorView imageEditorView, @StringRes int progressContent, int reqWidth, int reqHeight) {
        mContext = context;
        mReqWidth = reqWidth;
        mReqHeight = reqHeight;

        mImageEditorView = imageEditorView;

        mProgressDialog = new MaterialDialog.Builder(mContext)
                .content(progressContent)
                .progress(true, 0)
                .widgetColor(mContext.getResources().getColor(android.R.color.black))
                .contentColor(mContext.getResources().getColor(android.R.color.black))
                .canceledOnTouchOutside(false)
                .build();
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        mProgressDialog.show();
    }

    @Override
    protected Bitmap doInBackground(Integer... integers) {
        int data = integers[0];

        return BitmapUtil.decodeScaledBitmapFromResource(mContext.getResources(), data, mReqWidth, mReqHeight);
    }

    @Override
    protected void onPostExecute(Bitmap bitmap) {
        super.onPostExecute(bitmap);
        mProgressDialog.dismiss();
        mImageEditorView.setupOverlay(bitmap);
    }
}
