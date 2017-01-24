package net.iquesoft.iquephoto.ui.activities;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.Toast;

import com.arellomobile.mvp.MvpAppCompatActivity;
import com.arellomobile.mvp.presenter.InjectPresenter;

import net.iquesoft.iquephoto.DataHolder;
import net.iquesoft.iquephoto.R;
import net.iquesoft.iquephoto.core.editor.NewImageEditorView;
import net.iquesoft.iquephoto.presentation.presenters.activity.EditorPresenter;
import net.iquesoft.iquephoto.task.ImageSaveTask;
import net.iquesoft.iquephoto.util.BitmapUtil;
import net.iquesoft.iquephoto.presentation.views.activity.EditorView;
import net.iquesoft.iquephoto.ui.fragments.ToolsFragment;
import net.iquesoft.iquephoto.util.ToolbarUtil;

import java.io.IOException;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class EditorActivity extends MvpAppCompatActivity implements EditorView {
    @InjectPresenter
    EditorPresenter presenter;

    @BindView(R.id.toolbar_editor)
    Toolbar toolbar;

    @BindView(R.id.undoButton)
    Button undoButton;

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
            toolbar.setNavigationIcon(R.drawable.ic_close);
        }

        try {
            mBitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), getIntent().getData());
        } catch (IOException e) {
            e.printStackTrace();
        }

        DataHolder.getInstance().setImageUri(getIntent().getData());

        BitmapUtil.logBitmapInfo("Cropped Bitmap", mBitmap);

        imageEditorView.init(getMvpDelegate());
        imageEditorView.setImageBitmap(mBitmap);

        imageEditorView.setUndoListener(count -> {
            if (count != 0) {
                undoButton.setText(String.valueOf(count));
                undoButton.setVisibility(View.VISIBLE);
            } else {
                undoButton.setVisibility(View.GONE);
            }
        });

        ToolbarUtil.showTitle(false, this);

        mFragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = mFragmentManager.beginTransaction();
        fragmentTransaction.add(fragmentContainer.getId(), new ToolsFragment())
                .commit();

        Log.i("Backstack", String.valueOf(mFragmentManager.getBackStackEntryCount()));
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
                intent.putExtra(Intent.EXTRA_STREAM,
                        BitmapUtil.getUriOfBitmap(this, imageEditorView.getAlteredBitmap()));
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
            presenter.onBackPressed(mBitmap, imageEditorView.getAlteredBitmap());
        } else if (mFragmentManager.getBackStackEntryCount() == 1) {
            toolbar.setNavigationIcon(R.drawable.ic_close);
            ToolbarUtil.showTitle(false, this);
            navigateBack(true);
            if (imageEditorView.hasChanged()) {
                undoButton.setVisibility(View.VISIBLE);
            }
        } else if (mFragmentManager.getBackStackEntryCount() > 1) {
            ToolbarUtil.updateSubtitle(null, this);
            navigateBack(true);
        }
    }

    @Override
    public void showAlertDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this, R.style.AlertDialog);
        builder.setMessage(getString(R.string.on_back_alert))
                .setPositiveButton(getString(R.string.ok), (dialogInterface, i) -> finish())
                .setNeutralButton(getString(R.string.save), ((dialogInterface1, i) ->
                        new ImageSaveTask(this, imageEditorView.getAlteredBitmap()).execute())
                )
                .setNegativeButton(getString(R.string.cancel), (dialogInterface, i1) -> dialogInterface.dismiss())
                .show();
    }

    public void setupFragment(Fragment fragment) {
        FragmentTransaction fragmentTransaction = mFragmentManager.beginTransaction();
        fragmentTransaction.replace(fragmentContainer.getId(), fragment)
                .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE)
                .addToBackStack(null)
                .commit();

        toolbar.setNavigationIcon(R.drawable.ic_arrow_back_white_24dp);
        ToolbarUtil.showTitle(true, this);
        undoButton.setVisibility(View.GONE);

        Log.i("BackStack", String.valueOf(mFragmentManager.getBackStackEntryCount()));
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
            }
        } else finish();
    }

    @OnClick(R.id.undoButton)
    void onClickUndo() {
        imageEditorView.undo();
    }
}
