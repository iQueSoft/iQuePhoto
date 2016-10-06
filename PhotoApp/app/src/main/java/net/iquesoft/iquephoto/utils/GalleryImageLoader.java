package net.iquesoft.iquephoto.utils;

import android.content.Context;
import android.database.Cursor;
import android.provider.MediaStore;
import android.util.Log;

import net.iquesoft.iquephoto.model.GalleryImage;

import java.util.ArrayList;
import java.util.List;

public class GalleryImageLoader implements Runnable {

    private Context context;
    private List<GalleryImage> imagesList;

    public GalleryImageLoader(Context context) {
        this.context = context;
    }

    @Override
    public void run() {
        Cursor cursor = context.getContentResolver().query(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, null, null, null,
                null);

        if (cursor != null) {
            cursor.moveToFirst();
            imagesList = new ArrayList<GalleryImage>();
            for (int i = 0; i < cursor.getCount(); i++) {
                cursor.moveToPosition(i);

                imagesList.add(new GalleryImage(i, cursor.getString(1)));

                Log.d("GalleryImageLoader", cursor.getString(1));
            }
            cursor.close();
        }
    }

    public List<GalleryImage> getImagesList() {
        return imagesList;
    }
}
