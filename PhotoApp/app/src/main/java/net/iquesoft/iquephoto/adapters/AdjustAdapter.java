package net.iquesoft.iquephoto.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import net.iquesoft.iquephoto.R;
import net.iquesoft.iquephoto.model.Adjust;
import net.iquesoft.iquephoto.model.EditorColor;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class AdjustAdapter extends RecyclerView.Adapter<AdjustAdapter.ViewHolder> {

    private int checkedItem;

    private Context context;

    private List<Adjust> adjustList;

    private AdjustListener adjustListener;

    interface AdjustListener {
        void onClick(Adjust adjust);
    }

    public void setColorListener(AdjustListener adjustListener) {
        this.adjustListener = adjustListener;
    }

    public AdjustAdapter(List<Adjust> adjustList) {
        this.adjustList = adjustList;
    }

    @Override
    public AdjustAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        context = parent.getContext();

        LayoutInflater inflater = LayoutInflater.from(context);

        View toolItem = inflater.inflate(R.layout.item_adjust, parent, false);

        return new AdjustAdapter.ViewHolder(toolItem);
    }

    @Override
    public void onBindViewHolder(AdjustAdapter.ViewHolder holder, int position) {
        final Adjust adjust = adjustList.get(position);

        holder.adjustIcon.setImageDrawable(context.getResources().getDrawable(adjust.getIcon()));

        if (adjust.isSelected()) {
            //holder.colorSelected.setVisibility(View.VISIBLE);
        } else {
            //holder.colorSelected.setVisibility(View.GONE);
        }

        holder.adjustValue.setOnClickListener(view -> {

            adjustList.get(checkedItem).setSelected(false);
            notifyItemChanged(checkedItem);

            checkedItem = position;
            adjustList.get(position).setSelected(true);

            notifyItemChanged(position);

            adjustListener.onClick(adjust);

        });
    }

    @Override
    public int getItemCount() {
        return adjustList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.adjustValue)
        TextView adjustValue;

        @BindView(R.id.adjustIcon)
        ImageView adjustIcon;

        public ViewHolder(View itemView) {
            super(itemView);

            ButterKnife.bind(this, itemView);
        }
    }
}