package net.iquesoft.iquephoto.adapters;

import android.content.Context;
import android.content.res.Resources;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.mikhaellopez.circularimageview.CircularImageView;

import net.iquesoft.iquephoto.R;
import net.iquesoft.iquephoto.model.Overlay;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class OverlaysAdapter extends RecyclerView.Adapter<OverlaysAdapter.ViewHolder> {

    private int mSelectedOverlay = 0;

    private Context mContext;

    private List<Overlay> mOverlayList;

    private OverlayListener listener;

    public interface OverlayListener {
        void onClick(Overlay overlay);
    }

    public void setOverlayListener(OverlayListener listener) {
        this.listener = listener;
    }

    public OverlaysAdapter(List<Overlay> overlayList) {
        mOverlayList = overlayList;
    }

    @Override
    public OverlaysAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        mContext = parent.getContext();

        LayoutInflater inflater = LayoutInflater.from(mContext);

        View view = inflater.inflate(R.layout.item_overlay, parent, false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(OverlaysAdapter.ViewHolder holder, int position) {
        final Overlay overlay = mOverlayList.get(position);

        holder.title.setText(overlay.getTitle());

        try {
            holder.image.setImageDrawable(mContext.getResources().getDrawable(overlay.getImage()));
        } catch (Resources.NotFoundException e) {
            e.printStackTrace();
        }

        if (mSelectedOverlay == 0)
            mOverlayList.get(mSelectedOverlay).setSelected(true);

        if (overlay.isSelected()) {
            mSelectedOverlay = position;
            holder.overlaySelected.setVisibility(View.VISIBLE);
        } else {
            holder.overlaySelected.setVisibility(View.GONE);
        }

        holder.image.setOnClickListener(view -> {

            mOverlayList.get(mSelectedOverlay).setSelected(false);

            mOverlayList.get(position).setSelected(true);

            notifyItemChanged(mSelectedOverlay);
            notifyItemChanged(position);

            listener.onClick(overlay);

        });
    }

    @Override
    public int getItemCount() {
        return mOverlayList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.overlayTitle)
        TextView title;

        @BindView(R.id.overlayImage)
        CircularImageView image;

        @BindView(R.id.overlayChecked)
        ImageView overlaySelected;

        public ViewHolder(View itemView) {
            super(itemView);

            ButterKnife.bind(this, itemView);
        }
    }
}

