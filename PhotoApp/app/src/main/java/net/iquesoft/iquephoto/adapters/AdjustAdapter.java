package net.iquesoft.iquephoto.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import net.iquesoft.iquephoto.R;
import net.iquesoft.iquephoto.model.Adjust;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class AdjustAdapter extends RecyclerView.Adapter<AdjustAdapter.ViewHolder> {

    private int checkedItem;

    private Context mContext;

    private List<Adjust> mAdjustList;

    private AdjustListener mListener;

    public interface AdjustListener {
        void onClick(Adjust adjust, int position);
    }

    public void setAdjustListener(AdjustListener listener) {
        mListener = listener;
    }

    public AdjustAdapter(List<Adjust> adjustList) {
        mAdjustList = adjustList;
    }

    @Override
    public AdjustAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        mContext = parent.getContext();

        LayoutInflater inflater = LayoutInflater.from(mContext);

        View toolItem = inflater.inflate(R.layout.item_adjust, parent, false);

        return new AdjustAdapter.ViewHolder(toolItem);
    }

    @Override
    public void onBindViewHolder(AdjustAdapter.ViewHolder holder, int position) {
        final Adjust adjust = mAdjustList.get(position);

        holder.adjustTitle.setText(mContext.getText(adjust.getTitle()));

        holder.adjustIcon.setImageDrawable(mContext.getResources().getDrawable(adjust.getIcon()));

        holder.adjustValue.setText(String.valueOf(adjust.getValue()));

        if (adjust.isSelected()) {
            //holder.colorSelected.setVisibility(View.VISIBLE);
        } else {
            //holder.colorSelected.setVisibility(View.GONE);
        }

        holder.adjustItem.setOnClickListener(view -> {

            mAdjustList.get(checkedItem).setSelected(false);
            notifyItemChanged(checkedItem);

            checkedItem = position;
            mAdjustList.get(position).setSelected(true);

            notifyItemChanged(position);

            mAdjustList.get(position).setValue(100);

            mListener.onClick(adjust, position);

        });
    }

    @Override
    public int getItemCount() {
        return mAdjustList.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.adjustItem)
        LinearLayout adjustItem;

        @BindView(R.id.adjustTitle)
        TextView adjustTitle;

        @BindView(R.id.adjustValue)
        TextView adjustValue;

        @BindView(R.id.adjustIcon)
        ImageView adjustIcon;

        ViewHolder(View itemView) {
            super(itemView);

            ButterKnife.bind(this, itemView);
        }
    }
}