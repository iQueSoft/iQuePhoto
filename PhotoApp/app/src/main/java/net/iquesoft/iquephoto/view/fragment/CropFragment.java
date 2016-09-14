package net.iquesoft.iquephoto.view.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import net.iquesoft.iquephoto.DataHolder;
import net.iquesoft.iquephoto.EditorView;
import net.iquesoft.iquephoto.R;
import net.iquesoft.iquephoto.common.BaseFragment;
import net.iquesoft.iquephoto.di.components.IMainActivityComponent;
import net.iquesoft.iquephoto.model.CropBox;
import net.iquesoft.iquephoto.presenter.CropFragmentPresenterImpl;
import net.iquesoft.iquephoto.view.ICropFragmentView;

import javax.inject.Inject;

import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * Created by Sergey on 9/12/2016.
 */
public class CropFragment extends BaseFragment implements ICropFragmentView {

    private Unbinder unbinder;

    private EditorView editorView;

    @Inject
    CropFragmentPresenterImpl presenter;

    public static CropFragment newInstance() {
        /*Bundle b = new Bundle();
        b.putString("msg", text);
        b.putString("color", color);
        f.setArguments(b);*/
        return new CropFragment();
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        this.getComponent(IMainActivityComponent.class).inject(this);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_crop, container, false);
        v.setAlpha(0.8f);

        editorView = DataHolder.getInstance().getEditorView();
        editorView.setCropBox(new CropBox(100, 100, 200, 200));
        editorView.setCropActivated(true);

        unbinder = ButterKnife.bind(this, v);

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
