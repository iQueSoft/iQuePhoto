package net.iquesoft.iquephoto.view.dialog;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Window;

import net.iquesoft.iquephoto.R;
import net.iquesoft.iquephoto.adapters.ColorAdapter;
import net.iquesoft.iquephoto.model.EditorColor;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by Sergey on 9/16/2016.
 */
public class ColorPickerDialog extends Dialog {
    private final static String TAG = ColorPickerDialog.class.getSimpleName();

    private int color;
    private Context context;
    private ColorListener listener;

    public interface ColorListener {
        void onClick(int color);
    }

    private ColorAdapter adapter;

    @BindView(R.id.colorRecyclerView)
    RecyclerView recyclerView;

    public ColorPickerDialog(Context context) {
        super(context);
        this.context = context;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.dialog_color_picker);

        ButterKnife.bind(this);

        adapter = new ColorAdapter(EditorColor.getColorsList());

        adapter.setColorListener(editorColor -> {
            this.color = editorColor.getColor();
        });

        recyclerView.setLayoutManager(new GridLayoutManager(context, 4));
        recyclerView.setAdapter(adapter);
    }

    @OnClick(R.id.applyColor)
    void onClickApply() {
        listener.onClick(color);
        dismiss();
    }

    @OnClick(R.id.cancelColor)
    void onClickCancel() {
        dismiss();
    }

    public void setListener(ColorListener listener) {
        this.listener = listener;
    }
}
