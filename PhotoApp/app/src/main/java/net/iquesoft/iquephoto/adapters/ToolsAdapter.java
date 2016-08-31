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

/**
 * @author Sergey Belenkiy
 *         Adapter for Tools Box.
 */
public class ToolsAdapter extends RecyclerView.Adapter<ToolsAdapter.ViewHolder> {

    private Context context;

    private List<Tool> tools;

    private ToolsListener toolsListener;

    public interface ToolsListener {
        void onClick(Tool tool);
    }

    public void setToolsListener(ToolsListener toolsListener) {
        this.toolsListener = toolsListener;
    }

    public ToolsAdapter(List<Tool> tools) {
        this.tools = tools;
    }

    @Override
    public ToolsAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);

        View toolItem = inflater.inflate(R.layout.item_tool, parent, false);

        return new ViewHolder(toolItem);
    }

    @Override
    public void onBindViewHolder(ToolsAdapter.ViewHolder holder, int position) {
        final Tool tool = tools.get(position);
        holder.toolTitle.setText(context.getResources().getString(tool.getTitle()));
        holder.toolIcon.setImageDrawable(context.getResources().getDrawable(tool.getImage()));

        holder.toolIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                toolsListener.onClick(tool);
            }
        });
    }


    @Override
    public int getItemCount() {
        return tools.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.toolTitle)
        public TextView toolTitle;

        @BindView(R.id.toolIcon)
        public ImageView toolIcon;

        public ViewHolder(View itemView) {
            super(itemView);

            ButterKnife.bind(this, itemView);
        }
    }
}
