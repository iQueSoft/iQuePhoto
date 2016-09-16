package net.iquesoft.iquephoto.adapters;

import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.mikhaellopez.circularimageview.CircularImageView;

import net.iquesoft.iquephoto.R;
import net.iquesoft.iquephoto.core.ColorCircleDrawable;
import net.iquesoft.iquephoto.model.EditorColor;
import net.iquesoft.iquephoto.model.Filter;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Sergey on 9/16/2016.
 */
public class ColorAdapter extends RecyclerView.Adapter<ColorAdapter.ViewHolder> {

    private int checkedItem;

    private Context context;

    private List<EditorColor> editorColors;

    private ColorListener colorListener;

    public interface ColorListener {
        void onClick(EditorColor editorColor);
    }

    public void setColorListener(ColorListener colorListener) {
        this.colorListener = colorListener;
    }

    public ColorAdapter(List<EditorColor> editorColors) {
        this.editorColors = editorColors;
    }

    @Override
    public ColorAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        context = parent.getContext();

        LayoutInflater inflater = LayoutInflater.from(context);

        View toolItem = inflater.inflate(R.layout.item_color, parent, false);

        return new ViewHolder(toolItem);
    }

    @Override
    public void onBindViewHolder(ColorAdapter.ViewHolder holder, int position) {
        final EditorColor color = editorColors.get(position);

        holder.colorView.setImageDrawable(new ColorCircleDrawable(context.getResources().getColor(color.getColor())));
        //holder.colorSelected.setImageDrawable(new ColorCircleDrawable(context.getResources().getColor(color.getColor())));
        /*holder.colorView.setOnClickListener(view -> {

            *//*editorColors.get(checkedItem)(false);

            filters.get(position).setSelected(true);*//*

            colorListener.onClick(color);

        });*/
    }


    @Override
    public int getItemCount() {
        return editorColors.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.colorView)
        public ImageView colorView;

        @BindView(R.id.colorSelected)
        public ImageView colorSelected;

        public ViewHolder(View itemView) {
            super(itemView);

            ButterKnife.bind(this, itemView);
        }
    }
}

