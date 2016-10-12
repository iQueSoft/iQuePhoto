package net.iquesoft.iquephoto.utils;

import android.content.Context;
import android.database.Cursor;
import android.os.AsyncTask;
import android.provider.MediaStore;
import android.util.Log;

import net.iquesoft.iquephoto.model.ImageGallery;

import java.util.ArrayList;
import java.util.List;

public class GalleryImageLoader extends AsyncTask<Void, List<ImageGallery>, Void> {

    private Context mContext;
    private List<ImageGallery> mImagesList;

    private GalleryImageLoaderListener mListener;

    public interface GalleryImageLoaderListener {
        void fetchImages(List<ImageGallery> imageGalleryList);
    }

    public void setListener(GalleryImageLoaderListener listener) {
        mListener = listener;
    }

    public GalleryImageLoader(Context context) {
        mContext = context;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
    }

    @Override
    protected Void doInBackground(Void... params) {
        Cursor cursor = mContext.getContentResolver().query(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, null, null, null,
                null);

        if (cursor != null) {
            cursor.moveToFirst();
            mImagesList = new ArrayList<ImageGallery>();
            for (int i = 0; i < cursor.getCount(); i++) {
                cursor.moveToPosition(i);

                mImagesList.add(new ImageGallery(i, cursor.getString(1)));

                Log.d("GalleryImageLoader", cursor.getString(1));
                Log.d("T", Thread.currentThread().getName());
            }
            cursor.close();
        }

        return null;
    }

    @Override
    protected void onPostExecute(Void aVoid) {
        super.onPostExecute(aVoid);
        mListener.fetchImages(mImagesList);
    }
}
