package net.iquesoft.iquephoto.adapters;

import android.content.Context;
import android.graphics.ColorMatrixColorFilter;
import android.graphics.drawable.Drawable;
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

    private int mSelectedFilter = 0;

    private Context mContext;

    private Drawable mDrawable;

    private List<Filter> mFiltersList;

    private FiltersListener mFiltersListener;

    public interface FiltersListener {
        void onClick(Filter filter);
    }

    public void setFiltersListener(FiltersListener filtersListener) {
        mFiltersListener = filtersListener;
    }

    public FiltersAdapter(List<Filter> filters, Drawable drawable) {
        mFiltersList = filters;
        mDrawable = drawable;
    }

    @Override
    public FiltersAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        mContext = parent.getContext();

        LayoutInflater inflater = LayoutInflater.from(mContext);

        View view = inflater.inflate(R.layout.item_filter, parent, false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(FiltersAdapter.ViewHolder holder, int position) {
        final Filter filter = mFiltersList.get(position);

        holder.filterTitle.setText(filter.getTitle());

        if (filter.getColorMatrix() != null) {
            mDrawable.setColorFilter(new ColorMatrixColorFilter(filter.getColorMatrix()));
            holder.filterIcon.setImageDrawable(mDrawable);
        } else {
            holder.filterIcon.setImageDrawable(mDrawable);
        }

        if (mSelectedFilter == 0)
            mFiltersList.get(mSelectedFilter).setSelected(true);

        if (filter.isSelected()) {
            mSelectedFilter = position;
            holder.filterChecked.setVisibility(View.VISIBLE);
        } else {
            holder.filterChecked.setVisibility(View.GONE);
        }

        holder.filterIcon.setOnClickListener(view -> {

            mFiltersList.get(mSelectedFilter).setSelected(false);

            mFiltersList.get(position).setSelected(true);

            notifyItemChanged(mSelectedFilter);
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
        TextView filterTitle;

        @BindView(R.id.filterImage)
        CircularImageView filterIcon;

        @BindView(R.id.filterChecked)
        ImageView filterChecked;

        public ViewHolder(View itemView) {
            super(itemView);

            ButterKnife.bind(this, itemView);
        }
    }
}

