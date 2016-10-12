package net.iquesoft.iquephoto.adapters;

import android.content.Context;
import android.graphics.Typeface;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import net.iquesoft.iquephoto.R;
import net.iquesoft.iquephoto.model.Font;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class FontsAdapter extends RecyclerView.Adapter<FontsAdapter.ViewHolder> {

    private Context context;

    private List<Font> fonts;

    private FontsListener fontsListener;

    public interface FontsListener {
        void onClick(Font font);
    }

    public void setFontsListener(FontsListener fontsListener) {
        this.fontsListener = fontsListener;
    }

    public FontsAdapter(List<Font> fonts) {
        this.fonts = fonts;
    }

    @Override
    public FontsAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);

        View toolItem = inflater.inflate(R.layout.item_font, parent, false);

        return new ViewHolder(toolItem);
    }

    @Override
    public void onBindViewHolder(FontsAdapter.ViewHolder holder, int position) {
        final Font font = fonts.get(position);
        holder.font.setText(font.getTitle());
        holder.font.setTypeface(getTypeface(font.getTypeface()));

        holder.font.setOnClickListener(view -> fontsListener.onClick(font));
    }

    @Override
    public int getItemCount() {
        return fonts.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.fontTextView)
        TextView font;

        public ViewHolder(View itemView) {
            super(itemView);

            ButterKnife.bind(this, itemView);
        }
    }

    private Typeface getTypeface(String path) {
        return Typeface.createFromAsset(context.getAssets(), path);
    }
}
