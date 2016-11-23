package net.iquesoft.iquephoto.task;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.support.annotation.StringRes;

import com.afollestad.materialdialogs.MaterialDialog;

import net.iquesoft.iquephoto.core.editor.ImageEditorView;

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

        mReqHeight = reqHeight;
        mReqWidth = reqWidth;

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

        Bitmap srcBitmap = BitmapFactory.decodeResource(mContext.getResources(), data);
        // FIXME: BUG!!!
        return Bitmap.createScaledBitmap(srcBitmap, mReqWidth, mReqHeight, false);
        //BitmapUtil.decodeScaledBitmapFromResource(mContext.getResources(), data, mReqWidth, mReqHeight);
    }

    @Override
    protected void onPostExecute(Bitmap bitmap) {
        super.onPostExecute(bitmap);
        mProgressDialog.dismiss();
        //FIXME: mImageEditorView.setupOverlay(bitmap);
    }
}
