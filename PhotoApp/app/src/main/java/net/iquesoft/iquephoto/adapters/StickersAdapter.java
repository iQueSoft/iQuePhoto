package net.iquesoft.iquephoto.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import net.iquesoft.iquephoto.R;
import net.iquesoft.iquephoto.model.Sticker;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class StickersAdapter extends RecyclerView.Adapter<StickersAdapter.ViewHolder> {

    private Context mContext;

    private List<Sticker> mStickersList;

    private StickersListener mStickersListener;

    public interface StickersListener {
        void onClick(Sticker sticker);
    }

    public void setStickersListener(StickersListener stickersListener) {
        this.mStickersListener = stickersListener;
    }

    public StickersAdapter(List<Sticker> stickers) {
        mStickersList = stickers;
    }

    @Override
    public StickersAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        mContext = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(mContext);

        View toolItem = inflater.inflate(R.layout.item_sticker, parent, false);

        return new ViewHolder(toolItem);
    }

    @Override
    public void onBindViewHolder(StickersAdapter.ViewHolder holder, int position) {
        final Sticker sticker = mStickersList.get(position);
        holder.stickerTitle.setText(mContext.getResources().getString(sticker.getTitle()));
        holder.stickerImage.setImageDrawable(mContext.getResources().getDrawable(sticker.getImage()));

        holder.stickerImage.setOnClickListener(view -> mStickersListener.onClick(sticker));
    }


    @Override
    public int getItemCount() {
        return mStickersList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.stickerTitleTextView)
        TextView stickerTitle;

        @BindView(R.id.stickerImageView)
        ImageView stickerImage;

        public ViewHolder(View itemView) {
            super(itemView);

            ButterKnife.bind(this, itemView);
        }
    }
}
