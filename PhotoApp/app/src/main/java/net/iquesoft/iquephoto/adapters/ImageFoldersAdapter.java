package net.iquesoft.iquephoto.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import net.iquesoft.iquephoto.R;
import net.iquesoft.iquephoto.model.ImageFolder;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ImageFoldersAdapter extends RecyclerView.Adapter<ImageFoldersAdapter.ViewHolder> {

    private Context mContext;

    private List<ImageFolder> mImageFoldersList;

    private OnFolderClickListener mListener;

    public void setOnFolderClickListener(OnFolderClickListener listener) {
        mListener = listener;
    }

    public interface OnFolderClickListener {
        void onFolderClick(ImageFolder imageFolder);
    }

    public ImageFoldersAdapter(List<ImageFolder> imageFoldersList) {
        mImageFoldersList = imageFoldersList;
    }

    @Override
    public ImageFoldersAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        mContext = parent.getContext();

        LayoutInflater inflater = LayoutInflater.from(mContext);

        View view = inflater.inflate(R.layout.item_image_folder, parent, false);

        return new ImageFoldersAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ImageFoldersAdapter.ViewHolder holder, int position) {
        ImageFolder imageFolder = mImageFoldersList.get(position);

        Picasso.with(mContext)
                .load("file://" + imageFolder.getImages().get(0).getPath())
                .resize(250, 250)
                .centerCrop()
                .into(holder.folderImage);

        holder.folderName.setText(imageFolder.getFolderName());
        holder.folderImagesCount.setText(String.valueOf(imageFolder.getImages().size()));

        holder.itemView.setOnClickListener(view -> {
            mListener.onFolderClick(imageFolder);
        });
    }

    @Override
    public int getItemCount() {
        return mImageFoldersList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.folderImage)
        ImageView folderImage;

        @BindView(R.id.folderName)
        TextView folderName;

        @BindView(R.id.folderImagesCount)
        TextView folderImagesCount;

        public ViewHolder(View itemView) {
            super(itemView);

            ButterKnife.bind(this, itemView);
        }
    }
}