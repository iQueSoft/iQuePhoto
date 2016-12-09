package net.iquesoft.iquephoto.task;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Environment;
import android.util.Log;
import android.widget.Toast;

import com.afollestad.materialdialogs.MaterialDialog;

import net.iquesoft.iquephoto.R;
import net.iquesoft.iquephoto.ui.activities.EditorActivity;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class ImageSaveTask extends AsyncTask<Void, Void, Void> {
    private static final String TAG = ImageSaveTask.class.getSimpleName();

    private final static String IMAGE_STORAGE_PATH = Environment.getExternalStorageDirectory().toString() + "/" +
            android.os.Environment.DIRECTORY_DCIM + "/iQuePhoto";

    private Bitmap mBitmap;
    private Context mContext;
    private MaterialDialog mProgressDialog;

    public ImageSaveTask(Context context, Bitmap bitmap) {
        mBitmap = bitmap;
        mContext = context;

        mProgressDialog = new MaterialDialog.Builder(mContext)
                .content(R.string.saving)
                .progress(true, 0)
                .widgetColor(Color.BLACK)
                .contentColor(Color.BLACK)
                .canceledOnTouchOutside(false)
                .build();
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        mProgressDialog.show();
    }

    @Override
    protected Void doInBackground(Void... voids) {
        File file = getOutputMediaFile();
        if (file == null) {
            Log.d(TAG,
                    "Error creating media file, check storage permissions: ");
            return null;
        }
        try {
            FileOutputStream fileOutputStream = new FileOutputStream(file);
            mBitmap.compress(Bitmap.CompressFormat.PNG, 100, fileOutputStream);
            fileOutputStream.close();
        } catch (FileNotFoundException e) {
            Log.d(TAG, "File not found: " + e.getMessage());
        } catch (IOException e) {
            Log.d(TAG, "Error accessing file: " + e.getMessage());
        }

        return null;
    }

    @Override
    protected void onPostExecute(Void aVoid) {
        super.onPostExecute(aVoid);
        mProgressDialog.dismiss();
        Toast.makeText(mContext, R.string.image_saved, Toast.LENGTH_SHORT).show();

        if (mContext.getClass()
                .getSimpleName()
                .equalsIgnoreCase(EditorActivity.class.getSimpleName())) {
            Activity activity = (Activity) mContext;
            activity.finish();
        }
    }

    private File getOutputMediaFile() {
        File mediaStorageDir = new File(IMAGE_STORAGE_PATH);

        if (!mediaStorageDir.exists()) {
            if (!mediaStorageDir.mkdirs()) {
                return null;
            }
        }
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        File mediaFile;
        String mImageName = "iQuePhoto_" + timeStamp + ".jpg";
        mediaFile = new File(mediaStorageDir.getPath() + File.separator + mImageName);
        return mediaFile;
    }
}
