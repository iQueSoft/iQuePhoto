package net.iquesoft.iquephoto.tasks;

import android.content.Context;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.AsyncTask;
import android.support.v4.content.FileProvider;

import net.iquesoft.iquephoto.BuildConfig;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.OutputStream;

public class ImageCacheSaveTask extends AsyncTask<Void, Void, Uri> {
    private Bitmap mBitmap;
    private Context mContext;

    private OnImageCacheSaveListener mOnImageCacheSaveListener;

    public interface OnImageCacheSaveListener {
        void onSaveStarted();

        void onImageSaved(Uri uri);
    }

    public void setOnImageLoadedListener(OnImageCacheSaveListener onImageCacheSaveListener) {
        mOnImageCacheSaveListener = onImageCacheSaveListener;
    }

    public ImageCacheSaveTask(Context context, Bitmap bitmap) {
        mBitmap = bitmap;
        mContext = context;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        mOnImageCacheSaveListener.onSaveStarted();
    }

    @Override
    protected Uri doInBackground(Void... voids) {
        Uri uri = null;

        try {
            uri = FileProvider.getUriForFile(mContext,
                    BuildConfig.APPLICATION_ID + ".provider",
                    getTempFile(mContext));
        } catch (IOException e) {
            e.printStackTrace();
        }

        if (uri != null) {
            OutputStream outputStream = null;
            try {
                outputStream = mContext.getContentResolver()
                        .openOutputStream(uri);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }

            if (outputStream != null) {
                mBitmap.compress(Bitmap.CompressFormat.PNG, 100, outputStream);
            }
        }

        return uri;
    }

    @Override
    protected void onPostExecute(Uri uri) {
        super.onPostExecute(uri);

        mOnImageCacheSaveListener.onImageSaved(uri);
    }

    private File getTempFile(Context context) throws IOException {
        return File.createTempFile("temp", ".jpeg", context.getCacheDir());
    }
}