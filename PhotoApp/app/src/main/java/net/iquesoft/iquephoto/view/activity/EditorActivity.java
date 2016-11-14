package net.iquesoft.iquephoto.view.activity;

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
import net.iquesoft.iquephoto.core.EditorCommand;
import net.iquesoft.iquephoto.core.ImageEditorView;
import net.iquesoft.iquephoto.di.IHasComponent;
import net.iquesoft.iquephoto.di.components.IApplicationComponent;
import net.iquesoft.iquephoto.di.components.DaggerIEditorActivityComponent;
import net.iquesoft.iquephoto.di.components.IEditorActivityComponent;
import net.iquesoft.iquephoto.di.modules.EditorActivityModule;
import net.iquesoft.iquephoto.presenter.activity.EditorActivityPresenterImpl;
import net.iquesoft.iquephoto.tasks.ImageSaveTask;
import net.iquesoft.iquephoto.utils.BitmapUtil;
import net.iquesoft.iquephoto.view.activity.interfaces.IEditorActivityView;
import net.iquesoft.iquephoto.view.fragment.ToolsFragment;

import java.io.IOException;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class EditorActivity extends BaseActivity implements IEditorActivityView, IHasComponent<IEditorActivityComponent> {

    @Inject
    EditorActivityPresenterImpl presenter;

    private IEditorActivityComponent mComponent;

    private FragmentManager mFragmentManager;

    @Inject
    ToolsFragment toolsFragment;

    @BindView(R.id.buttonUndo)
    Button undoButton;

    @BindView(R.id.editorHeader)
    RelativeLayout editorHeader;

    @BindView(R.id.editorImageView)
    ImageEditorView imageEditorView;

    @BindView(R.id.toolSettingsContainer)
    FrameLayout fragmentContainer;

    private Bitmap mBitmap;

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

        DataHolder.getInstance().setBitmap(Bitmap.createScaledBitmap(mBitmap, 120, 120, false));

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
    protected void setupComponent(IApplicationComponent component) {
        mComponent = DaggerIEditorActivityComponent.builder()
                .editorActivityModule(new EditorActivityModule(this))
                .iApplicationComponent(component)
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
    public void setupFragment(Fragment fragment) {
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
            if (mFragmentManager.getBackStackEntryCount() > 1) {
                super.onBackPressed();
            } else if (mFragmentManager.getBackStackEntryCount() == 1) {
                super.onBackPressed();
                imageEditorView.setCommand(EditorCommand.NONE);
                editorHeader.setVisibility(View.VISIBLE);
            } else if (mFragmentManager.getBackStackEntryCount() == 0) {
                presenter.onBackPressed(mBitmap, imageEditorView.getAlteredBitmap());
            }
        } else
            finish();
    }

    @Override
    public ImageEditorView getImageEditorView() {
        return imageEditorView;
    }

    @OnClick(R.id.buttonEditorBack)
    void onClickBack() {
        presenter.onBackPressed(mBitmap, imageEditorView.getAlteredBitmap());
    }

    @OnClick(R.id.buttonShare)
    void onClickShare() {
        Intent intent = new Intent(EditorActivity.this, ShareActivity.class);
        imageEditorView.makeImage(intent);
    }

    @OnClick(R.id.buttonUndo)
    void onClickUndo() {
        imageEditorView.undo();
    }

    public IEditorActivityComponent getComponent() {
        return mComponent;
    }
}
