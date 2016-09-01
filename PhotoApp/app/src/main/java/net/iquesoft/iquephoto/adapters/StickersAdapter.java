package net.iquesoft.iquephoto.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import net.iquesoft.iquephoto.R;
import net.iquesoft.iquephoto.model.Sticker;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * @author Sergey Belenkiy
 *         Adapter for {@link net.iquesoft.iquephoto.model.Sticker} models.
 */
public class StickersAdapter extends RecyclerView.Adapter<StickersAdapter.ViewHolder> {

    private Context context;

    private List<Sticker> stickers;

    private StickersListener stickersListener;

    public interface StickersListener {
        void onClick(Sticker sticker);
    }

    public void setStickersListener(StickersListener stickersListener) {
        this.stickersListener = stickersListener;
    }

    public StickersAdapter(List<Sticker> stickers) {
        this.stickers = stickers;
    }

    @Override
    public StickersAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);

        View toolItem = inflater.inflate(R.layout.item_sticker, parent, false);

        return new ViewHolder(toolItem);
    }

    @Override
    public void onBindViewHolder(StickersAdapter.ViewHolder holder, int position) {
        final Sticker sticker = stickers.get(position);
        holder.stickerTitle.setText(context.getResources().getString(sticker.getTitle()));
        holder.stickerImage.setImageDrawable(context.getResources().getDrawable(sticker.getImage()));

        holder.stickerImage.setOnClickListener(view -> stickersListener.onClick(sticker));
    }


    @Override
    public int getItemCount() {
        return stickers.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.stickerTitleTextView)
        public TextView stickerTitle;

        @BindView(R.id.stickerImageView)
        public ImageView stickerImage;

        public ViewHolder(View itemView) {
            super(itemView);

            ButterKnife.bind(this, itemView);
        }
    }
}
