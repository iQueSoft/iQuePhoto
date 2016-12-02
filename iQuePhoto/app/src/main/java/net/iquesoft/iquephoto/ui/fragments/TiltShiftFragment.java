package net.iquesoft.iquephoto.ui.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.arellomobile.mvp.MvpAppCompatFragment;
import com.arellomobile.mvp.presenter.InjectPresenter;

import net.iquesoft.iquephoto.R;
import net.iquesoft.iquephoto.core.editor.ImageEditorView;
import net.iquesoft.iquephoto.core.editor.enums.EditorCommand;
import net.iquesoft.iquephoto.mvp.presenters.fragment.TiltShiftFragmentPresenter;
import net.iquesoft.iquephoto.mvp.views.fragment.TiltShiftView;
import net.iquesoft.iquephoto.ui.activities.EditorActivity;

import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

import static net.iquesoft.iquephoto.core.editor.enums.EditorCommand.TILT_SHIFT_LINEAR;
import static net.iquesoft.iquephoto.core.editor.enums.EditorCommand.TILT_SHIFT_RADIAL;

public class TiltShiftFragment extends MvpAppCompatFragment implements TiltShiftView {
    @InjectPresenter
    TiltShiftFragmentPresenter presenter;

    private Unbinder mUnbinder;

    private ImageEditorView mImageEditorView;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mImageEditorView =
                (ImageEditorView) getActivity().findViewById(R.id.editorImageView);
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
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        mUnbinder.unbind();
    }

    @Override
    public void onTiltShiftChanged(EditorCommand command) {
        mImageEditorView.setCommand(command);
    }

    @Override
    public void applyTiltShift(EditorCommand command) {
        mImageEditorView.apply(command);
        onClickBack();
    }

    @OnClick(R.id.tiltShiftCancelButton)
    void onClickBack() {
        ((EditorActivity) getActivity()).navigateBack(true);
    }

    @OnClick(R.id.tiltShiftApplyButton)
    void onClickApply() {
        presenter.applyChanges();
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
