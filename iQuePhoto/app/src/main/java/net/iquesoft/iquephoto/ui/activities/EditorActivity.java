package net.iquesoft.iquephoto.ui.activities;

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

import com.arellomobile.mvp.MvpAppCompatActivity;
import com.arellomobile.mvp.presenter.InjectPresenter;

import net.iquesoft.iquephoto.DataHolder;
import net.iquesoft.iquephoto.R;
import net.iquesoft.iquephoto.core.editor.enums.EditorCommand;
import net.iquesoft.iquephoto.core.editor.ImageEditorView;
import net.iquesoft.iquephoto.mvp.models.Text;
import net.iquesoft.iquephoto.mvp.presenters.activity.EditorPresenter;
import net.iquesoft.iquephoto.task.ImageSaveTask;
import net.iquesoft.iquephoto.ui.fragments.AddTextFragment;
import net.iquesoft.iquephoto.ui.fragments.AdjustFragment;
import net.iquesoft.iquephoto.ui.fragments.DrawingFragment;
import net.iquesoft.iquephoto.ui.fragments.FiltersFragment;
import net.iquesoft.iquephoto.ui.fragments.FramesFragment;
import net.iquesoft.iquephoto.ui.fragments.OverlaysFragment;
import net.iquesoft.iquephoto.ui.fragments.SliderControlFragment;
import net.iquesoft.iquephoto.ui.fragments.StickersToolFragment;
import net.iquesoft.iquephoto.ui.fragments.TiltShiftFragment;
import net.iquesoft.iquephoto.ui.fragments.TransformFragment;
import net.iquesoft.iquephoto.util.BitmapUtil;
import net.iquesoft.iquephoto.mvp.views.activity.EditorView;
import net.iquesoft.iquephoto.ui.fragments.ToolsFragment;

import java.io.IOException;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

import static net.iquesoft.iquephoto.core.editor.enums.EditorCommand.VIGNETTE;

public class EditorActivity extends MvpAppCompatActivity implements EditorView {

    @InjectPresenter
    EditorPresenter presenter;

    @Inject
    ToolsFragment toolsFragment;

    @Inject
    FiltersFragment filtersFragment;

    @Inject
    AdjustFragment adjustFragment;

    @Inject
    OverlaysFragment overlayFragment;

    @Inject
    StickersToolFragment stickersToolFragment;

    @Inject
    FramesFragment framesFragment;

    @Inject
    TransformFragment transformFragment;

    @Inject
    TiltShiftFragment tiltShiftFragment;

    @Inject
    DrawingFragment drawingFragment;

    @Inject
    AddTextFragment addTextFragment;

    @Inject
    SliderControlFragment sliderControlFragment;

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
                fragment = sliderControlFragment;
                break;
            case TRANSFORM_STRAIGHTEN:
                fragment = sliderControlFragment;
                break;
            case TRANSFORM_VERTICAL:
                fragment = sliderControlFragment;
                break;
            case VIGNETTE:
                sliderControlFragment.setCommand(VIGNETTE);
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

        // TODO: Make it without DataHolder.
        DataHolder.getInstance().setShareBitmap(imageEditorView.getAlteredBitmap());
        startActivity(intent);
    }

    @OnClick(R.id.undoButton)
    void onClickUndo() {
        imageEditorView.undo();
    }
}
