package net.iquesoft.iquephoto.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import net.iquesoft.iquephoto.R;
import net.iquesoft.iquephoto.models.Filter;
import net.iquesoft.iquephoto.models.StickersSet;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class StickerSetAdapter extends RecyclerView.Adapter<StickerSetAdapter.ViewHolder> {
    private Context mContext;

    private List<StickersSet> mStickersSets;

    private OnStickerSetClickListener mOnFilterClickListener;

    public interface OnStickerSetClickListener {
        void onClick(StickersSet stickersSet);
    }

    public void setStickerSetClickListener(OnStickerSetClickListener onFilterClickListener) {
        mOnFilterClickListener = onFilterClickListener;
    }

    public StickerSetAdapter(List<StickersSet> stickersSets) {
        mStickersSets = stickersSets;
    }

    @Override
    public StickerSetAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        mContext = parent.getContext();

        View view = LayoutInflater.from(mContext).inflate(R.layout.item_sticker_set, parent, false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(StickerSetAdapter.ViewHolder holder, int position) {
        final StickersSet stickersSet = mStickersSets.get(position);

        holder.filterTitle.setText(stickersSet.getTitle());

        Picasso.with(mContext)
                .load(stickersSet.getIcon())
                .fit()
                .centerCrop()
                .noPlaceholder()
                .into(holder.filterImageView);

        holder.filterImageView.setOnClickListener(view -> {

        });
    }

    @Override
    public int getItemCount() {
        return mStickersSets.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.stickerSetTitle)
        TextView filterTitle;

        @BindView(R.id.stickerSetImage)
        ImageView filterImageView;

        public ViewHolder(View itemView) {
            super(itemView);

            ButterKnife.bind(this, itemView);
        }
    }
}