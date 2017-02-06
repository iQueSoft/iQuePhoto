package net.iquesoft.iquephoto.tasks;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Point;
import android.os.AsyncTask;
import android.os.Build;
import android.support.annotation.NonNull;
import android.util.Log;
import android.view.Display;
import android.view.WindowManager;

public class DecodeScaledImageAsyncTask extends AsyncTask<String, Void, Bitmap> {
    private static String TAG = DecodeScaledImageAsyncTask.class.getSimpleName();

    private int mMaxSize;

    private Context mContext;

    private OnProgressListener mListener;

    public interface OnProgressListener {
        void onStarted();

        void onFinished(Bitmap bitmap);
    }

    public DecodeScaledImageAsyncTask(Context context) {
        mContext = context;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();

        mMaxSize = calculateMaxBitmapSize(mContext);
    }

    @Override
    protected Bitmap doInBackground(String... strings) {
        return decodeSampledBitmapFromFile(strings[0], mMaxSize, mMaxSize);
    }

    @Override
    protected void onPostExecute(Bitmap bitmap) {
        mListener.onFinished(bitmap);
    }

    public void setProgressListener(OnProgressListener listener) {
        mListener = listener;
    }

    private int calculateMaxBitmapSize(@NonNull Context context) {
        WindowManager wm = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        Display display = wm.getDefaultDisplay();

        Point size = new Point();
        int width, height;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB_MR2) {
            display.getSize(size);
            width = size.x;
            height = size.y;
        } else {
            width = display.getWidth();
            height = display.getHeight();
        }

        int maxBitmapSize = (int) Math.sqrt(Math.pow(width, 2) + Math.pow(height, 2));

        Canvas canvas = new Canvas();
        final int maxCanvasSize = Math.min(canvas.getMaximumBitmapWidth(), canvas.getMaximumBitmapHeight());
        if (maxCanvasSize > 0) {
            maxBitmapSize = Math.min(maxBitmapSize, maxCanvasSize);
        }

        Log.d(TAG, "maxBitmapSize: " + maxBitmapSize);
        return maxBitmapSize;
    }

    private int calculateInSampleSize(
            BitmapFactory.Options options, int reqWidth, int reqHeight) {
        final int height = options.outHeight;
        final int width = options.outWidth;
        int inSampleSize = 1;

        if (height > reqHeight || width > reqWidth) {
            final int halfHeight = height / 2;
            final int halfWidth = width / 2;

            while ((halfHeight / inSampleSize) >= reqHeight
                    && (halfWidth / inSampleSize) >= reqWidth) {
                inSampleSize *= 2;
            }
        }

        Log.i("Bitmap", "inSampleSize = " + String.valueOf(inSampleSize));

        return inSampleSize;
    }

    private Bitmap decodeSampledBitmapFromFile(String path, int reqWidth, int reqHeight) {
        final BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        BitmapFactory.decodeFile(path, options);

        options.inJustDecodeBounds = false;
        options.inSampleSize = calculateInSampleSize(options, reqWidth, reqHeight);

        return BitmapFactory.decodeFile(path, options);
    }
}