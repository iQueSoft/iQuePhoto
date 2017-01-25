package net.iquesoft.iquephoto.ui.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup;

import com.arellomobile.mvp.MvpAppCompatFragment;
import com.arellomobile.mvp.presenter.InjectPresenter;

import net.iquesoft.iquephoto.R;
import net.iquesoft.iquephoto.core.editor.ImageEditorView;
import net.iquesoft.iquephoto.presentation.presenters.fragment.TransformPresenter;
import net.iquesoft.iquephoto.presentation.views.fragment.TransformView;
import net.iquesoft.iquephoto.ui.activities.EditorActivity;
import net.iquesoft.iquephoto.util.ToolbarUtil;

import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

import static net.iquesoft.iquephoto.core.editor.enums.EditorTool.TRANSFORM;
import static net.iquesoft.iquephoto.core.editor.enums.EditorTool.TRANSFORM_HORIZONTAL;
import static net.iquesoft.iquephoto.core.editor.enums.EditorTool.TRANSFORM_STRAIGHTEN;
import static net.iquesoft.iquephoto.core.editor.enums.EditorTool.TRANSFORM_VERTICAL;

public class TransformFragment extends MvpAppCompatFragment implements TransformView {
    @InjectPresenter
    TransformPresenter presenter;

    private Unbinder mUnbinder;

    private EditorActivity mEditorActivity;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
        mEditorActivity = (EditorActivity) getActivity();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_transform, container, false);

        mUnbinder = ButterKnife.bind(this, view);

        return view;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
    }

    @Override
    public void onResume() {
        super.onResume();
        ((ImageEditorView) getActivity().findViewById(R.id.imageEditorView))
                .changeTool(TRANSFORM);
        ToolbarUtil.updateTitle(R.string.transform, getActivity());
        ToolbarUtil.updateSubtitle(null, getActivity());
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        mUnbinder.unbind();
    }

    @Override
    public void onPrepareOptionsMenu(Menu menu) {
        menu.clear();
        super.onPrepareOptionsMenu(menu);
    }

    @OnClick(R.id.transformHorizontalButton)
    void onClickTransformHorizontal() {
        mEditorActivity.setupFragment(
                AdjustmentFragment.newInstance(TRANSFORM_HORIZONTAL)
        );
    }

    @OnClick(R.id.transformStraightenButton)
    void onClickTransformStraighten() {
        mEditorActivity.setupFragment(
                AdjustmentFragment.newInstance(TRANSFORM_STRAIGHTEN)
        );
    }

    @OnClick(R.id.transformVerticalButton)
    void onClickTransformVertical() {
        mEditorActivity.setupFragment(
                AdjustmentFragment.newInstance(TRANSFORM_VERTICAL)
        );
    }
}
