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

    private Context mContext;

    private String mText;

    private List<Font> mFontsList;

    private FontsListener mListener;

    public interface FontsListener {
        void onClick(Font font);
    }

    public void setFontsListener(FontsListener listener) {
        mListener = listener;
    }

    public FontsAdapter(List<Font> fonts) {
        mFontsList = fonts;
    }

    @Override
    public FontsAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        mContext = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(mContext);

        View toolItem = inflater.inflate(R.layout.item_font, parent, false);

        return new ViewHolder(toolItem);
    }

    @Override
    public void onBindViewHolder(FontsAdapter.ViewHolder holder, int position) {
        final Font font = mFontsList.get(position);

        holder.fontTitle.setText(font.getTitle());

        holder.previewText.setTypeface(getTypeface(font.getTypeface()));

        holder.fontTitle.setOnClickListener(view -> mListener.onClick(font));
    }

    @Override
    public int getItemCount() {
        return mFontsList.size();
    }

    private Typeface getTypeface(String path) {
        return Typeface.createFromAsset(mContext.getAssets(), path);
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.previewText)
        TextView previewText;

        @BindView(R.id.fontTitle)
        TextView fontTitle;

        public ViewHolder(View itemView) {
            super(itemView);

            ButterKnife.bind(this, itemView);
        }
    }
}
