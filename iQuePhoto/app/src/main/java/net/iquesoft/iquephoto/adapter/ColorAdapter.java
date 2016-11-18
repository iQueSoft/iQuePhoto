package net.iquesoft.iquephoto.adapter;

import android.content.Context;
import android.support.v4.content.res.ResourcesCompat;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import net.iquesoft.iquephoto.R;
import net.iquesoft.iquephoto.common.ColorCircleDrawable;
import net.iquesoft.iquephoto.model.EditorColor;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ColorAdapter extends RecyclerView.Adapter<ColorAdapter.ViewHolder> {

    private int mSelectedColorPosition = 0;

    private Context mContext;

    private List<EditorColor> mEditorColorsList;

    private OnColorClickListener mOnColorClickListener;

    public interface OnColorClickListener {
        void onClick(EditorColor editorColor);
    }

    public void setOnColorClickListener(OnColorClickListener onColorClickListener) {
        mOnColorClickListener = onColorClickListener;
    }

    public ColorAdapter(List<EditorColor> editorColors) {
        mEditorColorsList = editorColors;
    }

    @Override
    public ColorAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        mContext = parent.getContext();

        View view = LayoutInflater.from(mContext).inflate(R.layout.item_color, parent, false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ColorAdapter.ViewHolder holder, int position) {
        final EditorColor color = mEditorColorsList.get(position);

        holder.colorView.setImageDrawable(new ColorCircleDrawable(
                ResourcesCompat.getColor(mContext.getResources(), color.getColor(), null))
        );

        if (mSelectedColorPosition == holder.getAdapterPosition()) {
            mOnColorClickListener.onClick(color);
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
        @BindView(R.id.colorView)
        ImageView colorView;

        @BindView(R.id.colorSelected)
        ImageView colorSelected;

        public ViewHolder(View itemView) {
            super(itemView);

            ButterKnife.bind(this, itemView);
        }
    }
}

