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
import net.iquesoft.iquephoto.model.Overlay;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class OverlaysAdapter extends RecyclerView.Adapter<OverlaysAdapter.ViewHolder> {

    private int mCurrentPosition = 0;

    private Context mContext;

    private List<Overlay> mOverlayList;

    private OnOverlayClickListener mOnOverlayClickListener;

    public interface OnOverlayClickListener {
        void onClick(Overlay overlay);
    }

    public void setOnOverlayClickListener(OnOverlayClickListener onOverlayClickListener) {
        mOnOverlayClickListener = onOverlayClickListener;
    }

    public OverlaysAdapter(List<Overlay> overlayList) {
        mOverlayList = overlayList;
    }

    @Override
    public OverlaysAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        mContext = parent.getContext();

        View view = LayoutInflater.from(mContext).inflate(R.layout.item_overlay, parent, false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(OverlaysAdapter.ViewHolder holder, int position) {
        final Overlay overlay = mOverlayList.get(position);

        holder.title.setText(overlay.getTitle());

        Picasso.with(mContext)
                .load(overlay.getImage())
                .fit()
                .centerCrop()
                .noPlaceholder()
                .into(holder.image);

        if (mCurrentPosition == position) {
            mOnOverlayClickListener.onClick(overlay);
            holder.overlaySelected.setVisibility(View.VISIBLE);
        } else
            holder.overlaySelected.setVisibility(View.GONE);

        holder.image.setOnClickListener(view -> {
            notifyItemChanged(mCurrentPosition);
            mCurrentPosition = position;
            notifyItemChanged(position);
        });
    }

    @Override
    public int getItemCount() {
        return mOverlayList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.overlayTitle)
        TextView title;

        @BindView(R.id.overlayImageView)
        ImageView image;

        @BindView(R.id.overlayChecked)
        ImageView overlaySelected;

        public ViewHolder(View itemView) {
            super(itemView);

            ButterKnife.bind(this, itemView);
        }
    }
}

