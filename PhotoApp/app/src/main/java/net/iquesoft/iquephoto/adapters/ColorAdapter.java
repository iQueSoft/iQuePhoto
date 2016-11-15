package net.iquesoft.iquephoto.adapters;

import android.content.Context;
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

    private int checkedItem;

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

        LayoutInflater inflater = LayoutInflater.from(mContext);

        View view = inflater.inflate(R.layout.item_color, parent, false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ColorAdapter.ViewHolder holder, int position) {
        final EditorColor color = mEditorColorsList.get(position);

        holder.colorView.setImageDrawable(new ColorCircleDrawable(mContext.getResources().getColor(color.getColor())));

        if (color.isSelected()) {
            holder.colorSelected.setVisibility(View.VISIBLE);
        } else {
            holder.colorSelected.setVisibility(View.GONE);
        }

        holder.colorView.setOnClickListener(view -> {

            mEditorColorsList.get(checkedItem).setSelected(false);
            notifyItemChanged(checkedItem);

            checkedItem = position;
            mEditorColorsList.get(position).setSelected(true);

            notifyItemChanged(position);

            mOnColorClickListener.onClick(color);

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

