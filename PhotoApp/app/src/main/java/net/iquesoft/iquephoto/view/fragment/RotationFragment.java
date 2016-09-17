package net.iquesoft.iquephoto.view.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;

import net.iquesoft.iquephoto.DataHolder;
import net.iquesoft.iquephoto.core.EditorView;
import net.iquesoft.iquephoto.R;
import net.iquesoft.iquephoto.common.BaseFragment;
import net.iquesoft.iquephoto.di.components.IMainActivityComponent;
import net.iquesoft.iquephoto.presenter.RotationFragmentPresenterImpl;
import net.iquesoft.iquephoto.view.IRotationFragmentView;

import org.adw.library.widgets.discreteseekbar.DiscreteSeekBar;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

/**
 * @author Sergey
 */
public class RotationFragment extends BaseFragment implements IRotationFragmentView {

    private boolean isHide = false;

    private Unbinder unbinder;

    private EditorView editorView;

    @Inject
    RotationFragmentPresenterImpl presenter;

    @BindView(R.id.angleSeekBar)
    DiscreteSeekBar angleSeekBar;

    @BindView(R.id.hideRotationButton)
    ImageView hideButton;

    @BindView(R.id.rotationController)
    LinearLayout controller;

    @OnClick(R.id.rotateRightButton)
    public void onClickRotateRight(View view) {
        editorView.rotateImage(90);
    }

    @OnClick(R.id.rotateLeftButton)
    public void onClickRotateLeft(View view) {
        editorView.rotateImage(-90);
    }

    @OnClick(R.id.hFlipButton)
    public void onClickHorizontalFlip(View view) {
        editorView.horizontalFlip();
    }

    @OnClick(R.id.vFlipButton)
    public void onClickVerticalFlip(View view) {
        editorView.verticalFlip();
    }

    public static RotationFragment newInstance() {
        /*Bundle b = new Bundle();
        b.putString("msg", text);
        b.putString("color", color);
        f.setArguments(b);*/
        return new RotationFragment();
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        this.getComponent(IMainActivityComponent.class).inject(this);
    }

    @Override
    public void onResume() {
        super.onResume();
        presenter.init(this);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_rotation, container, false);
        v.setAlpha(0.8f);

        unbinder = ButterKnife.bind(this, v);

        editorView = DataHolder.getInstance().getEditorView();

        angleSeekBar.setOnProgressChangeListener(new DiscreteSeekBar.OnProgressChangeListener() {
            @Override
            public void onProgressChanged(DiscreteSeekBar seekBar, int value, boolean fromUser) {
                // Todo: Fix this bad rotation
                editorView.rotateImage(value);
            }

            @Override
            public void onStartTrackingTouch(DiscreteSeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(DiscreteSeekBar seekBar) {

            }
        });

        return v;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @OnClick(R.id.hideRotationButton)
    public void onClickHideButton() {
        if (!isHide) {
            hideButton.setImageDrawable(getResources().getDrawable(R.drawable.ic_expand_less));
            controller.setVisibility(View.GONE);
            isHide = true;
        } else {
            hideButton.setImageDrawable(getResources().getDrawable(R.drawable.ic_expand));
            controller.setVisibility(View.VISIBLE);
            isHide = false;
        }
    }
}
