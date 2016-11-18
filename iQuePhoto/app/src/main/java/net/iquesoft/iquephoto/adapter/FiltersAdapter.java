package net.iquesoft.iquephoto.adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.ColorMatrixColorFilter;
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

    private int mCurrentPosition = 0;

    private Bitmap mBitmap;

    private Context mContext;

    private List<Filter> mFiltersList;

    private OnFilterClickListener mOnFilterClickListener;

    public interface OnFilterClickListener {
        void onClick(Filter filter);
    }

    public void setFiltersListener(OnFilterClickListener onFilterClickListener) {
        mOnFilterClickListener = onFilterClickListener;
    }

    public FiltersAdapter(List<Filter> filters, Bitmap bitmap) {
        mFiltersList = filters;
        mBitmap = bitmap;

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

        // TODO: Show images with filters.
        holder.filterIcon.setImageBitmap(mBitmap);

        holder.filterIcon.setColorFilter(new ColorMatrixColorFilter(filter.getColorMatrix()));

        if (mCurrentPosition == position) {
            mOnFilterClickListener.onClick(filter);
            holder.filterChecked.setVisibility(View.VISIBLE);
        } else
            holder.filterChecked.setVisibility(View.GONE);

        holder.filterIcon.setOnClickListener(view -> {
            notifyItemChanged(mCurrentPosition);
            mCurrentPosition = position;
            notifyItemChanged(position);
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

