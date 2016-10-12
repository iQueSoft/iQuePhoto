package net.iquesoft.iquephoto.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import net.iquesoft.iquephoto.R;
import net.iquesoft.iquephoto.model.Tool;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ToolsAdapter extends RecyclerView.Adapter<ToolsAdapter.ViewHolder> {

    private Context mContext;

    private List<Tool> mToolsList;

    private ToolsListener mToolsListener;

    public interface ToolsListener {
        void onClick(Tool tool);
    }

    public void setToolsListener(ToolsListener toolsListener) {
        this.mToolsListener = toolsListener;
    }

    public ToolsAdapter(List<Tool> tools) {
        mToolsList = tools;
    }

    @Override
    public ToolsAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        mContext = parent.getContext();

        LayoutInflater inflater = LayoutInflater.from(mContext);

        View view = inflater.inflate(R.layout.item_tool, parent, false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ToolsAdapter.ViewHolder holder, int position) {
        final Tool tool = mToolsList.get(position);
        holder.toolTitle.setText(mContext.getResources().getString(tool.getTitle()));
        holder.toolIcon.setImageDrawable(mContext.getResources().getDrawable(tool.getIcon()));

        holder.toolIcon.setOnClickListener(view -> mToolsListener.onClick(tool));
    }

    @Override
    public int getItemCount() {
        return mToolsList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.toolTitle)
        TextView toolTitle;

        @BindView(R.id.toolIcon)
        ImageView toolIcon;

        public ViewHolder(View itemView) {
            super(itemView);

            ButterKnife.bind(this, itemView);
        }
    }
}
