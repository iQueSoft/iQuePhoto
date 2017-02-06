package net.iquesoft.iquephoto.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;

import net.iquesoft.iquephoto.R;
import net.iquesoft.iquephoto.models.ImageAlbum;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ImageAlbumsAdapter extends RecyclerView.Adapter<ImageAlbumsAdapter.ViewHolder> {
    private Context mContext;

    private List<ImageAlbum> mImageAlbums;

    private OnAlbumClickListener mOnAlbumClickListener;

    public void setOnAlbumClickListener(OnAlbumClickListener onAlbumClickListener) {
        mOnAlbumClickListener = onAlbumClickListener;
    }

    public interface OnAlbumClickListener {
        void onAlbumClick(ImageAlbum imageAlbum);
    }

    public ImageAlbumsAdapter(List<ImageAlbum> imageAlbums) {
        mImageAlbums = imageAlbums;
    }

    @Override
    public ImageAlbumsAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        mContext = parent.getContext();

        View view = LayoutInflater.from(mContext).inflate(R.layout.item_image_album, parent, false);

        return new ImageAlbumsAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ImageAlbumsAdapter.ViewHolder holder, int position) {
        ImageAlbum imageAlbum = mImageAlbums.get(position);

        Picasso.with(mContext)
                .load("file://" + imageAlbum.getImages().get(0).getPath())
                .resize(250, 250)
                .centerCrop()
                .into(holder.folderImage, new Callback() {
                    @Override
                    public void onSuccess() {
                        holder.progressBar.setVisibility(View.GONE);
                    }

                    @Override
                    public void onError() {

                    }
                });

        holder.title.setText(
                imageAlbum.getName() + " (" + String.valueOf(imageAlbum.getImages().size()) + ")"
        );

        holder.itemView.setOnClickListener(view -> {
            mOnAlbumClickListener.onAlbumClick(imageAlbum);
        });
    }

    @Override
    public int getItemCount() {
        return mImageAlbums.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.text_view_album_title)
        TextView title;

        @BindView(R.id.image_view_album_image)
        ImageView folderImage;

        @BindView(R.id.progress_bar_gallery_album)
        ProgressBar progressBar;

        public ViewHolder(View itemView) {
            super(itemView);

            ButterKnife.bind(this, itemView);
        }
    }
}