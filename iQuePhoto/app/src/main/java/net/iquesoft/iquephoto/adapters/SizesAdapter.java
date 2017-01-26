package net.iquesoft.iquephoto.adapters;

import android.content.Context;
import android.support.v4.content.res.ResourcesCompat;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import net.iquesoft.iquephoto.R;
import net.iquesoft.iquephoto.graphics.CircleSizeDrawable;
import net.iquesoft.iquephoto.graphics.ColorCircleDrawable;
import net.iquesoft.iquephoto.models.BrushSize;
import net.iquesoft.iquephoto.models.EditorColor;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class SizesAdapter extends RecyclerView.Adapter<SizesAdapter.ViewHolder> {

    private int mSelectedColorPosition = 0;

    private Context mContext;

    private List<BrushSize> mEditorColorsList;

    private OnSizeClickListener mOnSizeClickListener;

    public interface OnSizeClickListener {
        void onClick(BrushSize size);
    }

    public void setOnSizeClickListener(OnSizeClickListener onSizeClickListener) {
        mOnSizeClickListener = onSizeClickListener;
    }

    public SizesAdapter(List<BrushSize> editorColors) {
        mEditorColorsList = editorColors;
    }

    @Override
    public SizesAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        mContext = parent.getContext();

        View view = LayoutInflater.from(mContext).inflate(R.layout.item_color, parent, false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(SizesAdapter.ViewHolder holder, int position) {
        final BrushSize size = mEditorColorsList.get(position);

        holder.colorView.setImageDrawable(new CircleSizeDrawable(size.getSize()));

        if (mSelectedColorPosition == holder.getAdapterPosition()) {
            mOnSizeClickListener.onClick(size);
            holder.colorSelected.setVisibility(View.VISIBLE);
        } else {
            holder.colorSelected.setVisibility(View.GONE);
        }

        holder.colorView.setOnClickListener(view -> {
            notifyItemChanged(mSelectedColorPosition);
            mSelectedColorPosition = holder.getAdapterPosition();
            notifyItemChanged(holder.getAdapterPosition());

        });
    }

    @Override
    public int getItemCount() {
        return mEditorColorsList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.rgbFrameLayout)
        ImageView colorView;

        @BindView(R.id.colorSelected)
        ImageView colorSelected;

        public ViewHolder(View itemView) {
            super(itemView);

            ButterKnife.bind(this, itemView);
        }
    }
}

