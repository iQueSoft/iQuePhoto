package net.iquesoft.iquephoto.view.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import net.iquesoft.iquephoto.R;
import net.iquesoft.iquephoto.common.BaseFragment;
import net.iquesoft.iquephoto.di.components.IEditorActivityComponent;
import net.iquesoft.iquephoto.presenter.fragment.TransformFragmentPresenterImpl;
import net.iquesoft.iquephoto.view.activity.interfaces.IEditorActivityView;
import net.iquesoft.iquephoto.view.fragment.interfaces.ITransformFragmentView;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

import static net.iquesoft.iquephoto.core.EditorCommand.TRANSFORM_HORIZONTAL;
import static net.iquesoft.iquephoto.core.EditorCommand.TRANSFORM_STRAIGHTEN;
import static net.iquesoft.iquephoto.core.EditorCommand.TRANSFORM_VERTICAL;
import static net.iquesoft.iquephoto.core.EditorCommand.VIGNETTE;

public class TransformFragment extends BaseFragment implements ITransformFragmentView {

    private Unbinder mUnbinder;

    @Inject
    IEditorActivityView editorActivityView;

    @Inject
    TransformFragmentPresenterImpl presenter;

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        this.getComponent(IEditorActivityComponent.class).inject(this);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_transform, container, false);

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

    @OnClick(R.id.transformBackButton)
    void onClickBack() {
        editorActivityView.navigateBack(true);
    }

    @OnClick(R.id.transformHorizontalButton)
    void onClickTransformHorizontal() {
        editorActivityView.setupFragment(SliderControlFragment.newInstance(TRANSFORM_HORIZONTAL));
    }

    @OnClick(R.id.transformStraightenButton)
    void onClickTransformStraighten() {
        editorActivityView.setupFragment(SliderControlFragment.newInstance(TRANSFORM_STRAIGHTEN));
    }

    @OnClick(R.id.transformVerticalButton)
    void onClickTransformVertical() {
        editorActivityView.setupFragment(SliderControlFragment.newInstance(TRANSFORM_VERTICAL));
    }
}
