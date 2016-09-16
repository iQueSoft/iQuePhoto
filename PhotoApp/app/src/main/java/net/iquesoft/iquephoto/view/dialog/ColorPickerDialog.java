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

/**
 * Created by Sergey on 9/16/2016.
 */
public class ColorPickerDialog extends Dialog {
    private Context context;
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
        recyclerView.setLayoutManager(new GridLayoutManager(context, 4));
        recyclerView.setAdapter(adapter);
    }
}
