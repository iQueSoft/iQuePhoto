package net.iquesoft.iquephoto.ui.activity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AlertDialog;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.RelativeLayout;
import android.widget.Toast;

import net.iquesoft.iquephoto.DataHolder;
import net.iquesoft.iquephoto.R;
import net.iquesoft.iquephoto.common.BaseActivity;
import net.iquesoft.iquephoto.core.editor.enums.EditorCommand;
import net.iquesoft.iquephoto.core.editor.ImageEditorView;
import net.iquesoft.iquephoto.di.HasComponent;
import net.iquesoft.iquephoto.di.components.AppComponent;
import net.iquesoft.iquephoto.di.components.DaggerEditorComponent;
import net.iquesoft.iquephoto.di.components.EditorComponent;
import net.iquesoft.iquephoto.di.modules.EditorModule;
import net.iquesoft.iquephoto.model.Text;
import net.iquesoft.iquephoto.presentation.presenter.activity.EditorPresenterImpl;
import net.iquesoft.iquephoto.presentation.presenter.fragment.SliderControlFragmentPresenterImpl;
import net.iquesoft.iquephoto.task.ImageSaveTask;
import net.iquesoft.iquephoto.ui.fragment.AddTextFragment;
import net.iquesoft.iquephoto.ui.fragment.AdjustFragment;
import net.iquesoft.iquephoto.ui.fragment.DrawingFragment;
import net.iquesoft.iquephoto.ui.fragment.FiltersFragment;
import net.iquesoft.iquephoto.ui.fragment.FramesFragment;
import net.iquesoft.iquephoto.ui.fragment.OverlayFragment;
import net.iquesoft.iquephoto.ui.fragment.SliderControlFragment;
import net.iquesoft.iquephoto.ui.fragment.StickersToolFragment;
import net.iquesoft.iquephoto.ui.fragment.TiltShiftFragment;
import net.iquesoft.iquephoto.ui.fragment.TransformFragment;
import net.iquesoft.iquephoto.ui.fragment.TransformHorizontalFragment;
import net.iquesoft.iquephoto.ui.fragment.TransformStraightenFragment;
import net.iquesoft.iquephoto.ui.fragment.TransformVerticalFragment;
import net.iquesoft.iquephoto.util.BitmapUtil;
import net.iquesoft.iquephoto.presentation.view.activity.EditorView;
import net.iquesoft.iquephoto.ui.fragment.ToolsFragment;

import java.io.IOException;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class EditorActivity extends BaseActivity implements EditorView, HasComponent<EditorComponent> {

    @Inject
    EditorPresenterImpl presenter;

    @Inject
    ToolsFragment toolsFragment;

    @Inject
    FiltersFragment filtersFragment;

    @Inject
    AdjustFragment adjustFragment;

    @Inject
    OverlayFragment overlayFragment;

    @Inject
    StickersToolFragment stickersToolFragment;

    @Inject
    FramesFragment framesFragment;

    @Inject
    TransformFragment transformFragment;

    @Inject
    TransformHorizontalFragment transformHorizontalFragment;

    @Inject
    TransformStraightenFragment transformStraightenFragment;

    @Inject
    TransformVerticalFragment transformVerticalFragment;

    @Inject
    TiltShiftFragment tiltShiftFragment;

    @Inject
    DrawingFragment drawingFragment;

    @Inject
    AddTextFragment addTextFragment;

    @Inject
    SliderControlFragment sliderControlFragment;

    @Inject
    SliderControlFragmentPresenterImpl sliderControlFragmentPresenter;

    @BindView(R.id.undoButton)
    Button undoButton;

    @BindView(R.id.editorHeader)
    RelativeLayout editorHeader;

    @BindView(R.id.editorImageView)
    ImageEditorView imageEditorView;

    @BindView(R.id.fragmentContainer)
    FrameLayout fragmentContainer;

    private Bitmap mBitmap;
    private FragmentManager mFragmentManager;
    private EditorComponent mComponent;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);

        setContentView(R.layout.activity_editor);

        ButterKnife.bind(this);

        try {
            mBitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), getIntent().getData());
        } catch (IOException e) {
            e.printStackTrace();
        }

        DataHolder.getInstance().setImageUri(getIntent().getData());

        BitmapUtil.logBitmapInfo("Cropped Bitmap", mBitmap);

        imageEditorView.setImageBitmap(mBitmap);

        imageEditorView.setUndoListener(count -> {
            if (count != 0) {
                undoButton.setText(String.valueOf(count));
                undoButton.setVisibility(View.VISIBLE);
            } else {
                undoButton.setVisibility(View.GONE);
            }
        });

        DataHolder.getInstance().setEditorView(imageEditorView);

        mFragmentManager = getSupportFragmentManager();

        FragmentTransaction fragmentTransaction = mFragmentManager.beginTransaction();
        fragmentTransaction.add(fragmentContainer.getId(), toolsFragment)
                .commit();
    }

    @Override
    protected void setupComponent(AppComponent component) {
        mComponent = DaggerEditorComponent.builder()
                .appComponent(component)
                .editorModule(new EditorModule(this))
                .build();
        mComponent.inject(this);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    @Override
    public void onBackPressed() {
        if (mFragmentManager.getBackStackEntryCount() == 0) {
            presenter.onBackPressed(mBitmap, imageEditorView.getAlteredBitmap());
        } else if (mFragmentManager.getBackStackEntryCount() != 0) {
            navigateBack(true);
        }
    }

    @Override
    public void showAlertDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this, R.style.AlertMaterialDialog);
        builder.setMessage(getString(R.string.on_back_alert))
                .setPositiveButton(getString(R.string.ok), (dialogInterface, i) -> {
                    finish();
                })
                .setNeutralButton(getString(R.string.save), ((dialogInterface1, i) -> {
                    new ImageSaveTask(this, imageEditorView.getAlteredBitmap()).execute();
                }))
                .setNegativeButton(getString(R.string.cancel), (dialogInterface, i1) -> {
                    dialogInterface.dismiss();
                })
                .show();
    }

    @Override
    public void setupToolFragment(EditorCommand editorCommand) {
        Fragment fragment = null;

        switch (editorCommand) {
            case FILTERS:
                fragment = filtersFragment;
                break;
            case ADJUST:
                fragment = adjustFragment;
                break;
            case OVERLAY:
                fragment = overlayFragment;
                break;
            case STICKERS:
                fragment = stickersToolFragment;
                break;
            case FRAMES:
                fragment = framesFragment;
                break;
            case TRANSFORM:
                fragment = transformFragment;
                break;
            case TRANSFORM_HORIZONTAL:
                fragment = transformHorizontalFragment;
                break;
            case TRANSFORM_STRAIGHTEN:
                fragment = transformStraightenFragment;
                break;
            case TRANSFORM_VERTICAL:
                fragment = transformVerticalFragment;
                break;
            case VIGNETTE:
                sliderControlFragmentPresenter.setCommand(editorCommand);
                fragment = sliderControlFragment;
                break;
            case TILT_SHIFT_RADIAL:
                fragment = tiltShiftFragment;
                break;
            case DRAWING:
                fragment = drawingFragment;
                break;
            case TEXT:
                fragment = addTextFragment;
                break;
            default:
                fragment = sliderControlFragment;
        }

        FragmentTransaction fragmentTransaction = mFragmentManager.beginTransaction();
        fragmentTransaction.replace(fragmentContainer.getId(), fragment)
                .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE)
                .addToBackStack(null)
                .commit();

        editorHeader.setVisibility(View.INVISIBLE);
    }

    @Override
    public void showToastMessage(int stringResource) {
        Toast.makeText(getApplicationContext(), getString(stringResource), Toast.LENGTH_SHORT).show();
    }

    @Override
    public void navigateBack(boolean isFragment) {
        if (isFragment) {
            if (mFragmentManager.getBackStackEntryCount() > 1)
                super.onBackPressed();
            else if (mFragmentManager.getBackStackEntryCount() == 0)
                presenter.onBackPressed(mBitmap, imageEditorView.getAlteredBitmap());
            else if (mFragmentManager.getBackStackEntryCount() == 1) {
                super.onBackPressed();
                editorHeader.setVisibility(View.VISIBLE);
            }
        } else
            finish();
    }

    @Override
    public void addTextToEditor(Text text) {
        imageEditorView.addText(text);
    }

    @Override
    public void applyCommand(EditorCommand editorCommand) {
        imageEditorView.apply(editorCommand);
    }

    @Override
    public void setEditorCommand(EditorCommand editorCommand) {
        imageEditorView.setCommand(editorCommand);
    }

    @Override
    public ImageEditorView getImageEditorView() {
        return imageEditorView;
    }

    @OnClick(R.id.editorBackButton)
    void onClickBack() {
        presenter.onBackPressed(mBitmap, imageEditorView.getAlteredBitmap());
    }

    @OnClick(R.id.shareButton)
    void onClickShare() {
        Intent intent = new Intent(EditorActivity.this, ShareActivity.class);
        imageEditorView.makeImage(intent);
    }

    @OnClick(R.id.undoButton)
    void onClickUndo() {
        imageEditorView.undo();
    }

    public EditorComponent getComponent() {
        return mComponent;
    }
}
