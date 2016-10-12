package net.iquesoft.iquephoto.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import net.iquesoft.iquephoto.R;
import net.iquesoft.iquephoto.model.ImageGallery;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class GalleryAdapter extends RecyclerView.Adapter<GalleryAdapter.ViewHolder> {

    private Context mContext;

    private List<ImageGallery> mImageGalleries;

    public GalleryAdapter(List<ImageGallery> imageGalleries) {
        mImageGalleries = imageGalleries;
    }

    @Override
    public GalleryAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        mContext = parent.getContext();

        LayoutInflater inflater = LayoutInflater.from(mContext);

        View view = inflater.inflate(R.layout.item_gallery_image, parent, false);

        return new GalleryAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(GalleryAdapter.ViewHolder holder, int position) {
        ImageGallery imageGallery = mImageGalleries.get(position);

        holder.galleryImage.setImageBitmap(imageGallery.getBitmap());
    }

    @Override
    public int getItemCount() {
        return mImageGalleries.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.imageGallery)
        ImageView galleryImage;

        public ViewHolder(View itemView) {
            super(itemView);

            ButterKnife.bind(this, itemView);
        }
    }
}