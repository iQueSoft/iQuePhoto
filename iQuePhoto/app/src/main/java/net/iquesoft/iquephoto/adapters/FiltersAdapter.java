package net.iquesoft.iquephoto.adapters;

import android.content.Context;
import android.graphics.ColorMatrixColorFilter;
import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import net.iquesoft.iquephoto.R;
import net.iquesoft.iquephoto.models.Filter;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class FiltersAdapter extends RecyclerView.Adapter<FiltersAdapter.ViewHolder> {
    private int mCurrentPosition = 0;

    private Context mContext;

    private Uri mImageUri;
    private List<Filter> mFiltersList;

    private OnFilterClickListener mOnFilterClickListener;

    public interface OnFilterClickListener {
        void onFilterClicked(Filter filter);

        void onIntensityClicked();
    }
    
    public void setFiltersListener(OnFilterClickListener onFilterClickListener) {
        mOnFilterClickListener = onFilterClickListener;
    }

    public FiltersAdapter(Uri uri, List<Filter> filters) {
        mImageUri = uri;
        mFiltersList = filters;
    }

    @Override
    public FiltersAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        mContext = parent.getContext();

        View view = LayoutInflater.from(mContext).inflate(R.layout.item_filter, parent, false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(FiltersAdapter.ViewHolder holder, int position) {
        final Filter filter = mFiltersList.get(position);

        holder.filterTitle.setText(filter.getTitle());

        Picasso.with(mContext)
                .load(mImageUri)
                .fit()
                .centerCrop()
                .noPlaceholder()
                .into(holder.filterImageView);

        holder.filterImageView.setColorFilter(new ColorMatrixColorFilter(filter.getColorMatrix()));

        if (mCurrentPosition == position) {
            mOnFilterClickListener.onFilterClicked(filter);
            holder.filterChecked.setVisibility(View.VISIBLE);
        } else
            holder.filterChecked.setVisibility(View.GONE);

        holder.filterImageView.setOnClickListener(view -> {
            notifyItemChanged(mCurrentPosition);
            if (mCurrentPosition != position) {
                mCurrentPosition = position;
                notifyItemChanged(position);
            } else {
                mOnFilterClickListener.onIntensityClicked();
            }
        });
    }

    @Override
    public int getItemCount() {
        return mFiltersList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.text_view_filter_title)
        TextView filterTitle;

        @BindView(R.id.image_view_filter)
        ImageView filterImageView;

        @BindView(R.id.image_view_filter_checked)
        ImageView filterChecked;

        public ViewHolder(View itemView) {
            super(itemView);

            ButterKnife.bind(this, itemView);
        }
    }
}