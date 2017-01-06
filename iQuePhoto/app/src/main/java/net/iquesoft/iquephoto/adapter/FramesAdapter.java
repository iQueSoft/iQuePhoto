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
import net.iquesoft.iquephoto.models.Frame;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class FramesAdapter extends RecyclerView.Adapter<FramesAdapter.ViewHolder> {

    private int mCurrentPosition = 0;

    private Context mContext;

    private List<Frame> mFramesList;

    private OnFrameClickListener mOnFrameClickListener;

    public interface OnFrameClickListener {
        void onClick(Frame frame);
    }

    public void setFramesListener(OnFrameClickListener onFrameClickListener) {
        mOnFrameClickListener = onFrameClickListener;
    }

    public FramesAdapter(List<Frame> framesList) {
        mFramesList = framesList;
    }

    @Override
    public FramesAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        mContext = parent.getContext();

        View view = LayoutInflater.from(mContext).inflate(R.layout.item_frame, parent, false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(FramesAdapter.ViewHolder holder, int position) {
        Frame frame = mFramesList.get(position);

        holder.title.setText(frame.getTitle());

        Picasso.with(mContext)
                .load(frame.getImage())
                .into(holder.image);
        
        if (mCurrentPosition == position) {
            if (mOnFrameClickListener != null)
                mOnFrameClickListener.onClick(frame);
            holder.frameSelected.setVisibility(View.VISIBLE);
        } else
            holder.frameSelected.setVisibility(View.GONE);


        holder.image.setOnClickListener(view -> {
            notifyItemChanged(mCurrentPosition);
            mCurrentPosition = position;
            notifyItemChanged(position);
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

