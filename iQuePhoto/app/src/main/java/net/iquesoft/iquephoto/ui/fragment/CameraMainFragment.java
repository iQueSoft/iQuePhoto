package net.iquesoft.iquephoto.ui.fragment;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import net.iquesoft.iquephoto.R;
import net.iquesoft.iquephoto.common.BaseFragment;
import net.iquesoft.iquephoto.di.components.ICameraActivityComponent;
import net.iquesoft.iquephoto.presentation.presenter.fragment.CameraMainPresenterImpl;
import net.iquesoft.iquephoto.presentation.view.fragment.CameraMainView;

import javax.inject.Inject;

import butterknife.ButterKnife;
import butterknife.Unbinder;

public class CameraMainFragment extends BaseFragment implements CameraMainView {

    private Context mContext;

    private Unbinder mUnbinder;

    @Inject
    CameraMainPresenterImpl presenter;

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        getComponent(ICameraActivityComponent.class).inject(this);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_camera_main, container, false);

        mUnbinder = ButterKnife.bind(this, view);

        mContext = view.getContext();

        return view;
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
}