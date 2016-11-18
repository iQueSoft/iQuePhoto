package net.iquesoft.iquephoto.task;

import android.content.Context;
import android.database.Cursor;
import android.os.AsyncTask;
import android.provider.MediaStore;

import net.iquesoft.iquephoto.model.Image;
import net.iquesoft.iquephoto.model.ImageAlbum;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ImageLoader extends AsyncTask<Void, Void, List<ImageAlbum>> {

    private Context mContext;
    private List<ImageAlbum> mFoldersList;

    private final String[] mProjection = new String[]{
            MediaStore.Images.Media._ID,
            MediaStore.Images.Media.DATA,
            MediaStore.Images.Media.BUCKET_DISPLAY_NAME
    };

    private OnImageLoadedListener mListener;

    public interface OnImageLoadedListener {
        void onImageLoaded(List<ImageAlbum> imageAlba);
    }

    public void setOnImageLoadedListener(OnImageLoadedListener listener) {
        mListener = listener;
    }

    public ImageLoader(Context context) {
        mContext = context;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
    }

    @Override
    protected List<ImageAlbum> doInBackground(Void... params) {
        Cursor cursor = mContext.getContentResolver()
                .query(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, mProjection,
                        null, null, MediaStore.Images.Media.DATE_ADDED);

        Map<String, ImageAlbum> folderMap = new HashMap<>();

        if (cursor != null) {
            if (cursor.moveToLast())
                do {
                    int id = cursor.getInt(cursor.getColumnIndex(mProjection[0]));
                    String path = cursor.getString(cursor.getColumnIndex(mProjection[1]));
                    String folderName = cursor.getString(cursor.getColumnIndex(mProjection[2]));

                    File file = new File(path);
                    if (file.exists()) {
                        Image image = new Image(id, path);

                        ImageAlbum imageAlbum = folderMap.get(folderName);
                        if (imageAlbum == null) {
                            imageAlbum = new ImageAlbum(folderName);
                            folderMap.put(folderName, imageAlbum);
                        }
                        imageAlbum.getImages().add(image);
                    }
                } while (cursor.moveToPrevious());
            cursor.close();
        }

        if (!folderMap.isEmpty())
            mFoldersList = new ArrayList<>(folderMap.values());

        return mFoldersList;
    }

    @Override
    protected void onPostExecute(List<ImageAlbum> imageAlba) {
        super.onPostExecute(imageAlba);
        //Log.i(ImageLoader.class.getSimpleName(), String.valueOf(mImagesList.size() + 1));
        mListener.onImageLoaded(imageAlba);
    }
}
