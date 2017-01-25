package net.iquesoft.iquephoto.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;

import net.iquesoft.iquephoto.R;
import net.iquesoft.iquephoto.models.Image;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ImagesAdapter extends RecyclerView.Adapter<ImagesAdapter.ViewHolder> {

    private Context mContext;

    private List<Image> mImageList;

    private OnImageClickListener mListener;

    public void setOnImageClickListener(OnImageClickListener listener) {
        mListener = listener;
    }

    public interface OnImageClickListener {
        void onClick(Image image);
    }

    public ImagesAdapter(List<Image> imageList) {
        mImageList = imageList;
    }

    @Override
    public ImagesAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        mContext = parent.getContext();

        LayoutInflater inflater = LayoutInflater.from(mContext);

        View view = inflater.inflate(R.layout.item_image, parent, false);

        return new ImagesAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ImagesAdapter.ViewHolder holder, int position) {
        Image image = mImageList.get(position);

        Picasso.with(mContext)
                .load("file://" + image.getPath())
                .resize(250, 250)
                .centerCrop()
                .into(holder.galleryImage);

        holder.galleryImage.setOnClickListener(v -> {
            mListener.onClick(image);
        });
    }

    @Override
    public int getItemCount() {
        return mImageList.size();
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