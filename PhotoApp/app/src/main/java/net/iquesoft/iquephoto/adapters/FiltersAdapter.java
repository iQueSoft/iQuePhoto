package net.iquesoft.iquephoto.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.mikhaellopez.circularimageview.CircularImageView;

import net.iquesoft.iquephoto.R;
import net.iquesoft.iquephoto.model.Filter;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class FiltersAdapter extends RecyclerView.Adapter<FiltersAdapter.ViewHolder> {

    private int checkedItem = 0;

    private Context mContext;

    private List<Filter> mFiltersList;

    private FiltersListener mFiltersListener;

    public interface FiltersListener {
        void onClick(Filter filter);
    }

    public void setFiltersListener(FiltersListener filtersListener) {
        mFiltersListener = filtersListener;
    }

    public FiltersAdapter(List<Filter> filters) {
        mFiltersList = filters;
    }

    @Override
    public FiltersAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        mContext = parent.getContext();

        LayoutInflater inflater = LayoutInflater.from(mContext);

        View toolItem = inflater.inflate(R.layout.item_filter, parent, false);

        return new ViewHolder(toolItem);
    }

    @Override
    public void onBindViewHolder(FiltersAdapter.ViewHolder holder, int position) {
        final Filter filter = mFiltersList.get(position);
        holder.filterTitle.setText(mContext.getResources().getString(filter.getTitle()));
        holder.filterIcon.setImageDrawable(mContext.getResources().getDrawable(filter.getImage()));

        if (checkedItem == 0)
            mFiltersList.get(checkedItem).setSelected(true);

        if (filter.isSelected()) {
            checkedItem = position;
            holder.filterChecked.setVisibility(View.VISIBLE);
        } else {
            holder.filterChecked.setVisibility(View.GONE);
        }

        holder.filterIcon.setOnClickListener(view -> {

            mFiltersList.get(checkedItem).setSelected(false);

            mFiltersList.get(position).setSelected(true);

            notifyItemChanged(checkedItem);
            notifyItemChanged(position);

            mFiltersListener.onClick(filter);

        });
    }

    @Override
    public int getItemCount() {
        return mFiltersList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.filterTitle)
        public TextView filterTitle;

        @BindView(R.id.filterImage)
        public CircularImageView filterIcon;

        @BindView(R.id.filterChecked)
        public ImageView filterChecked;

        public ViewHolder(View itemView) {
            super(itemView);

            ButterKnife.bind(this, itemView);
        }
    }
}

