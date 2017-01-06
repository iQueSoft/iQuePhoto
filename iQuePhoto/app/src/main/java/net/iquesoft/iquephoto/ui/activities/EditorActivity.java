package net.iquesoft.iquephoto.ui.activities;

import android.animation.Animator;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.ViewAnimationUtils;
import android.widget.FrameLayout;
import android.widget.Toast;

import com.arellomobile.mvp.MvpAppCompatActivity;
import com.arellomobile.mvp.presenter.InjectPresenter;

import net.iquesoft.iquephoto.DataHolder;
import net.iquesoft.iquephoto.R;
import net.iquesoft.iquephoto.core.editor.ImageEditorView;
import net.iquesoft.iquephoto.core.editor.NewImageEditorView;
import net.iquesoft.iquephoto.mvp.presenters.activity.EditorPresenter;
import net.iquesoft.iquephoto.task.ImageSaveTask;
import net.iquesoft.iquephoto.util.BitmapUtil;
import net.iquesoft.iquephoto.mvp.views.activity.EditorView;
import net.iquesoft.iquephoto.ui.fragments.ToolsFragment;

import java.io.IOException;

import butterknife.BindView;
import butterknife.ButterKnife;

public class EditorActivity extends MvpAppCompatActivity implements EditorView {
    @InjectPresenter
    EditorPresenter presenter;

    @BindView(R.id.toolbar_editor)
    Toolbar toolbar;

    /*@BindView(R.id.undoButton)
    Button undoButton;*/

    @BindView(R.id.imageEditorView)
    NewImageEditorView imageEditorView;

    @BindView(R.id.fragmentContainer)
    FrameLayout fragmentContainer;

    private Bitmap mBitmap;
    private FragmentManager mFragmentManager;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_editor);

        ButterKnife.bind(this);

        setSupportActionBar(toolbar);

        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }

        try {
            mBitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), getIntent().getData());
        } catch (IOException e) {
            e.printStackTrace();
        }

        DataHolder.getInstance().setImageUri(getIntent().getData());

        BitmapUtil.logBitmapInfo("Cropped Bitmap", mBitmap);

        imageEditorView.setImageBitmap(mBitmap);

        /* TODO: Remove this comment.
        imageEditorView.setUndoListener(count -> {
            if (count != 0) {
                //undoButton.setText(String.valueOf(count));
                //undoButton.setVisibility(View.VISIBLE);
            } else {
                //undoButton.setVisibility(View.GONE);
            }
        });*/

        mFragmentManager = getSupportFragmentManager();

        FragmentTransaction fragmentTransaction = mFragmentManager.beginTransaction();
        fragmentTransaction.add(fragmentContainer.getId(), new ToolsFragment())
                .commit();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_editor, menu);

        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_share:
                Intent intent = new Intent(EditorActivity.this, ShareActivity.class);
                // TODO: Remove this comment.
                /*intent.putExtra(Intent.EXTRA_STREAM,
                        BitmapUtil.getUriOfBitmap(this, imageEditorView.getAlteredBitmap()));*/
                startActivity(intent);
                break;
            case R.id.action_apply:
                imageEditorView.applyChanges();
                onBackPressed();
                break;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return super.onSupportNavigateUp();
    }

    @Override
    public void onBackPressed() {
        if (mFragmentManager.getBackStackEntryCount() == 0) {
            // // TODO: Remove this comment.
            // presenter.onBackPressed(mBitmap, imageEditorView.getAlteredBitmap());
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
                    // TODO: Remove this comment.
                    // new ImageSaveTask(this, imageEditorView.getAlteredBitmap()).execute();
                }))
                .setNegativeButton(getString(R.string.cancel), (dialogInterface, i1) -> {
                    dialogInterface.dismiss();
                })
                .show();
    }

    public void setupFragment(Fragment fragment) {
        FragmentTransaction fragmentTransaction = mFragmentManager.beginTransaction();
        fragmentTransaction.replace(fragmentContainer.getId(), fragment)
                .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE)
                .addToBackStack(null)
                .commit();

        int centerX = (fragmentContainer.getLeft() + fragmentContainer.getRight()) / 2;
        int centerY = (fragmentContainer.getTop() + fragmentContainer.getBottom()) / 2;

        int endRadius = Math.max(fragmentContainer.getWidth(), fragmentContainer.getHeight());

        Animator animator = ViewAnimationUtils.createCircularReveal(
                fragmentContainer, centerX, centerY, 0, endRadius);

        animator.start();
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
                // TODO: Remove this comment.
                return;
                // presenter.onBackPressed(mBitmap, imageEditorView.getAlteredBitmap());
            else if (mFragmentManager.getBackStackEntryCount() == 1) {
                super.onBackPressed();
                //editorHeader.setVisibility(View.VISIBLE);
            }
        } else finish();
    }

    /*
    @OnClick(R.id.undoButton)
    void onClickUndo() {
        imageEditorView.undo();
    }
    */
}
