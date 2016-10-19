package net.iquesoft.iquephoto.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.display.RoundedBitmapDisplayer;
import com.nostra13.universalimageloader.core.display.SimpleBitmapDisplayer;

import net.iquesoft.iquephoto.R;
import net.iquesoft.iquephoto.model.GalleryImage;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class GalleryImageAdapter extends RecyclerView.Adapter<GalleryImageAdapter.ViewHolder> {

    private Context mContext;

    private ImageLoader mImageLoader = ImageLoader.getInstance();

    private List<GalleryImage> mGalleryImageList;
    private DisplayImageOptions mDisplayImageOptions;

    public GalleryImageAdapter(List<GalleryImage> galleryImageList) {

        mGalleryImageList = galleryImageList;

        Log.i(GalleryImageAdapter.class.getSimpleName(), String.valueOf(mGalleryImageList.size() + 1));

        mDisplayImageOptions = new DisplayImageOptions.Builder()
                .showImageOnLoading(R.drawable.ic_image_loading)
                .showImageForEmptyUri(R.drawable.ic_image_loading)
                .showImageOnFail(R.drawable.ic_image_fail)
                .cacheInMemory(true)
                .cacheOnDisk(true)
                .considerExifParams(true)
                .displayer(new RoundedBitmapDisplayer(0))
                .build();
    }

    @Override
    public GalleryImageAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        mContext = parent.getContext();

        LayoutInflater inflater = LayoutInflater.from(mContext);

        View view = inflater.inflate(R.layout.item_gallery_image, parent, false);

        return new GalleryImageAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(GalleryImageAdapter.ViewHolder holder, int position) {
        GalleryImage galleryImage = mGalleryImageList.get(position);

        mImageLoader.displayImage("file://" + galleryImage.getPath(), holder.galleryImage, mDisplayImageOptions);

        //holder.galleryImage.setImageBitmap(galleryImage.getBitmap());
    }

    @Override
    public int getItemCount() {
        return mGalleryImageList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.galleryImage)
        ImageView galleryImage;

        public ViewHolder(View itemView) {
            super(itemView);

            ButterKnife.bind(this, itemView);
        }
    }
}