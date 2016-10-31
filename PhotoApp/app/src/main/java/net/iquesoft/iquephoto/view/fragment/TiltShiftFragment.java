package net.iquesoft.iquephoto.view.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import net.iquesoft.iquephoto.DataHolder;
import net.iquesoft.iquephoto.R;
import net.iquesoft.iquephoto.common.BaseFragment;
import net.iquesoft.iquephoto.core.ImageEditorView;
import net.iquesoft.iquephoto.di.components.IEditorActivityComponent;
import net.iquesoft.iquephoto.presenter.TiltShiftFragmentPresenterImpl;
import net.iquesoft.iquephoto.view.ITiltShiftFragmentView;

import javax.inject.Inject;

import butterknife.ButterKnife;
import butterknife.Unbinder;

public class TiltShiftFragment extends BaseFragment implements ITiltShiftFragmentView {

    private boolean mIsHide;

    private Unbinder mUnbinder;

    private ImageEditorView mImageEditorView;

    @Inject
    TiltShiftFragmentPresenterImpl presenter;

    public static TiltShiftFragment newInstance() {
        /*Bundle b = new Bundle();
        b.putString("msg", text);
        b.putString("color", color);
        f.setArguments(b);*/
        return new TiltShiftFragment();
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        this.getComponent(IEditorActivityComponent.class).inject(this);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_tilt_shift, container, false);

        mImageEditorView = DataHolder.getInstance().getEditorView();

        mUnbinder = ButterKnife.bind(this, v);

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
        mUnbinder.unbind();
    }

    /*@OnClick(R.id.hideAdjust)
    void onClickHide(View view) {
        if (!mIsHide) {
            mIsHide = true;
            hideAdjust.setImageDrawable(getResources().getDrawable(R.drawable.ic_expand_less));
            recyclerView.setVisibility(View.GONE);
        } else {
            mIsHide = false;
            hideAdjust.setImageDrawable(getResources().getDrawable(R.drawable.ic_expand));
            recyclerView.setVisibility(View.VISIBLE);
        }
    }*/
}
