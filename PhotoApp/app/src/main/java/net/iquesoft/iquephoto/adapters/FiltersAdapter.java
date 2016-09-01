package net.iquesoft.iquephoto.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import net.iquesoft.iquephoto.R;
import net.iquesoft.iquephoto.model.Filter;
import net.iquesoft.iquephoto.view.fragment.FiltersFragment;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * @author Sergey.
 *         Adapter for {@link FiltersFragment}.
 */
public class FiltersAdapter extends RecyclerView.Adapter<FiltersAdapter.ViewHolder> {

    private Context context;

    private List<Filter> filters;

    private FiltersListener filtersListener;

    public interface FiltersListener {
        void onClick(Filter filter);
    }

    public void setFiltersListener(FiltersListener filtersListener) {
        this.filtersListener = filtersListener;
    }

    public FiltersAdapter(List<Filter> filters) {
        this.filters = filters;
    }

    @Override
    public FiltersAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        context = parent.getContext();

        LayoutInflater inflater = LayoutInflater.from(context);

        View toolItem = inflater.inflate(R.layout.item_filter, parent, false);

        return new ViewHolder(toolItem);
    }

    @Override
    public void onBindViewHolder(FiltersAdapter.ViewHolder holder, int position) {
        final Filter filter = filters.get(position);
        holder.filterTitle.setText(context.getResources().getString(filter.getTitle()));
        holder.filterIcon.setImageDrawable(context.getResources().getDrawable(filter.getImage()));

        // Todo: Check current filter
        if (position == 0) {
            holder.filterChecked.setVisibility(View.VISIBLE);
        }

        holder.filterIcon.setOnClickListener(view -> filtersListener.onClick(filter));
    }


    @Override
    public int getItemCount() {
        return filters.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.filterTitle)
        public TextView filterTitle;

        @BindView(R.id.filterImage)
        public ImageView filterIcon;

        @BindView(R.id.filterChecked)
        public ImageView filterChecked;

        public ViewHolder(View itemView) {
            super(itemView);

            ButterKnife.bind(this, itemView);
        }
    }
}

