package net.iquesoft.iquephoto.ui.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.arellomobile.mvp.presenter.InjectPresenter;

import net.iquesoft.iquephoto.R;
import net.iquesoft.iquephoto.core.editor.ImageEditorView;
import net.iquesoft.iquephoto.core.editor.enums.EditorTool;
import net.iquesoft.iquephoto.mvp.common.BaseToolFragment;
import net.iquesoft.iquephoto.mvp.presenters.fragment.TiltShiftFragmentPresenter;
import net.iquesoft.iquephoto.mvp.views.fragment.TiltShiftView;
import net.iquesoft.iquephoto.util.ActivityUtil;

import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

import static net.iquesoft.iquephoto.core.editor.enums.EditorTool.TILT_SHIFT_LINEAR;
import static net.iquesoft.iquephoto.core.editor.enums.EditorTool.TILT_SHIFT_RADIAL;

public class TiltShiftFragment extends BaseToolFragment implements TiltShiftView {
    @InjectPresenter
    TiltShiftFragmentPresenter presenter;

    private Unbinder mUnbinder;

    private ImageEditorView mImageEditorView;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mImageEditorView =
                (ImageEditorView) getActivity().findViewById(R.id.imageEditorView);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_tilt_shift, container, false);

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
        mImageEditorView.setCommand(TILT_SHIFT_RADIAL);
        ActivityUtil.updateToolbarTitle(R.string.tilt_shift, getActivity());
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        mUnbinder.unbind();
    }

    @Override
    public void onTiltShiftChanged(EditorTool command) {
        mImageEditorView.setCommand(command);
    }

    @Override
    public void applyTiltShift(EditorTool command) {
        mImageEditorView.applyChanges();
        //onClickBack();
    }

    @OnClick(R.id.tiltShiftLinearButton)
    void onClickLinear() {
        presenter.changeTiltShift(TILT_SHIFT_LINEAR);
    }

    @OnClick(R.id.tiltShiftRadialButton)
    void onClickRadial() {
        presenter.changeTiltShift(TILT_SHIFT_RADIAL);
    }


}
