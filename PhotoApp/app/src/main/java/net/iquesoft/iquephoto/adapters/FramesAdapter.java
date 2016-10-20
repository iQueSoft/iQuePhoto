package net.iquesoft.iquephoto.adapters;

import android.content.Context;
import android.content.res.Resources;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import net.iquesoft.iquephoto.R;
import net.iquesoft.iquephoto.model.Frame;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class FramesAdapter extends RecyclerView.Adapter<FramesAdapter.ViewHolder> {

    private int mSelectedOverlay = 0;

    private Context mContext;

    private List<Frame> mFramesList;

    private FramesListener mListener;

    public interface FramesListener {
        void onClick(Frame frame);
    }

    public void setFramesListener(FramesListener listener) {
        mListener = listener;
    }

    public FramesAdapter(List<Frame> framesList) {
        mFramesList = framesList;
    }

    @Override
    public FramesAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        mContext = parent.getContext();

        LayoutInflater inflater = LayoutInflater.from(mContext);

        View view = inflater.inflate(R.layout.item_frame, parent, false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(FramesAdapter.ViewHolder holder, int position) {
        Frame frame = mFramesList.get(position);

        holder.title.setText(frame.getTitle());

        try {
            holder.image.setImageDrawable(mContext.getResources().getDrawable(frame.getImage()));
        } catch (Resources.NotFoundException e) {
            e.printStackTrace();
        }

        if (mSelectedOverlay == 0)
            mFramesList.get(mSelectedOverlay).setSelected(true);

        if (frame.isSelected()) {
            mSelectedOverlay = position;
            holder.frameSelected.setVisibility(View.VISIBLE);
        } else {
            holder.frameSelected.setVisibility(View.GONE);
        }

        holder.image.setOnClickListener(view -> {

            mFramesList.get(mSelectedOverlay).setSelected(false);

            mFramesList.get(position).setSelected(true);

            notifyItemChanged(mSelectedOverlay);
            notifyItemChanged(position);

            mListener.onClick(frame);

        });
    }

    @Override
    public int getItemCount() {
        return mFramesList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.frameTitle)
        TextView title;

        @BindView(R.id.frameImage)
        ImageView image;

        @BindView(R.id.frameChecked)
        ImageView frameSelected;

        public ViewHolder(View itemView) {
            super(itemView);

            ButterKnife.bind(this, itemView);
        }
    }
}

