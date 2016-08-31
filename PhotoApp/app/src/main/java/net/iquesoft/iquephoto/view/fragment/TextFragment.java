package net.iquesoft.iquephoto.view.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import net.iquesoft.iquephoto.DataHolder;
import net.iquesoft.iquephoto.PhotoEditorView;
import net.iquesoft.iquephoto.R;
import net.iquesoft.iquephoto.common.BaseFragment;
import net.iquesoft.iquephoto.di.components.IMainActivityComponent;
import net.iquesoft.iquephoto.dialogs.ColorPickerDialog;
import net.iquesoft.iquephoto.dialogs.TextDialog;
import net.iquesoft.iquephoto.presenter.TextFragmentPresenterImpl;
import net.iquesoft.iquephoto.view.ITextFragmentView;

import javax.inject.Inject;

import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

public class TextFragment extends BaseFragment implements ITextFragmentView {

    private Unbinder unbinder;

    private PhotoEditorView photoEditorView;

    private TextDialog textDialog;
    private ColorPickerDialog colorPickerDialog;

    @Inject
    TextFragmentPresenterImpl presenter;

    @OnClick(R.id.textColorButton)
    public void onClickTextColorButton(View view) {
        colorPickerDialog = new ColorPickerDialog(view.getContext());
        colorPickerDialog.show();
    }

    @OnClick(R.id.textButton)
    public void onClickTextButton(View view) {
        textDialog = new TextDialog(view.getContext()); // ???
        textDialog.show();
    }

    public static TextFragment newInstance() {
        return new TextFragment();
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        this.getComponent(IMainActivityComponent.class).inject(this);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.text_layout, container, false);

        unbinder = ButterKnife.bind(this, v);

        photoEditorView = DataHolder.getInstance().getPhotoEditorView();

        return v;
    }

    @Override
    public void onResume() {
        super.onResume();
        presenter.init(this);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }
}