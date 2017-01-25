package net.iquesoft.iquephoto.ui.activities;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
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
import com.arellomobile.mvp.presenter.ProvidePresenter;

import net.iquesoft.iquephoto.R;
import net.iquesoft.iquephoto.core.editor.ImageEditorView;
import net.iquesoft.iquephoto.presentation.presenters.activity.EditorActivityPresenter;
import net.iquesoft.iquephoto.task.ImageSaveTask;
import net.iquesoft.iquephoto.util.BitmapUtil;
import net.iquesoft.iquephoto.presentation.views.activity.EditorActivityView;
import net.iquesoft.iquephoto.ui.fragments.ToolsFragment;
import net.iquesoft.iquephoto.util.ToolbarUtil;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class EditorActivity extends MvpAppCompatActivity implements EditorActivityView {
    @InjectPresenter
    EditorActivityPresenter mPresenter;
    
    @ProvidePresenter
    EditorActivityPresenter provideEditorPresenter() {
        return new EditorActivityPresenter(this, getIntent());
    }

    @BindView(R.id.toolbar_editor)
    Toolbar mToolbar;

    @BindView(R.id.undoButton)
    Button mUndoButton;

    @BindView(R.id.imageEditorView)
    ImageEditorView mImageEditorView;

    @BindView(R.id.fragmentContainer)
    FrameLayout mFragmentContainer;

    private FragmentManager mFragmentManager;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_editor);

        ButterKnife.bind(this);

        setSupportActionBar(mToolbar);

        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            mToolbar.setNavigationIcon(R.drawable.ic_close);
        }

        mImageEditorView.init(getMvpDelegate());

        mImageEditorView.setUndoListener(count -> {
            if (count != 0) {
                mUndoButton.setText(String.valueOf(count));
                mUndoButton.setVisibility(View.VISIBLE);
            } else {
                mUndoButton.setVisibility(View.GONE);
            }
        });

        ToolbarUtil.showTitle(false, this);

        mFragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = mFragmentManager.beginTransaction();
        fragmentTransaction.add(mFragmentContainer.getId(), new ToolsFragment())
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
                        BitmapUtil.getUriOfBitmap(this, mImageEditorView.getAlteredImageBitmap()));
                startActivity(intent);
                /*PopupMenu popupMenu = new PopupMenu(this, mUndoButton);
                popupMenu.inflate(R.menu.menu_share);
                popupMenu.show();*/
                break;
            case R.id.action_apply:
                mImageEditorView.applyChanges();
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
            mPresenter.onBackPressed(mImageEditorView.getAlteredImageBitmap());
        } else if (mFragmentManager.getBackStackEntryCount() == 1) {
            mToolbar.setNavigationIcon(R.drawable.ic_close);
            ToolbarUtil.showTitle(false, this);
            navigateBack(true);
            if (mImageEditorView.hasChanges()) {
                mUndoButton.setVisibility(View.VISIBLE);
            }
        } else if (mFragmentManager.getBackStackEntryCount() > 1) {
            ToolbarUtil.updateSubtitle(null, this);
            navigateBack(true);
        }
    }

    @Override
    public void startEditing(Bitmap bitmap) {
        BitmapUtil.logBitmapInfo("Cropped Bitmap", bitmap);

        mImageEditorView.setImageBitmap(bitmap);
    }

    @Override
    public void showAlertDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this, R.style.AlertDialog);
        builder.setMessage(getString(R.string.on_back_alert))
                .setPositiveButton(getString(R.string.ok), (dialogInterface, i) -> finish())
                .setNeutralButton(getString(R.string.save), ((dialogInterface1, i) ->
                        new ImageSaveTask(this, mImageEditorView.getAlteredImageBitmap()).execute())
                )
                .setNegativeButton(getString(R.string.cancel), (dialogInterface, i1) -> dialogInterface.dismiss())
                .show();
    }

    public void setupFragment(Fragment fragment) {
        FragmentTransaction fragmentTransaction = mFragmentManager.beginTransaction();
        fragmentTransaction.replace(mFragmentContainer.getId(), fragment)
                .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE)
                .addToBackStack(null)
                .commit();

        mToolbar.setNavigationIcon(R.drawable.ic_arrow_back_white_24dp);
        ToolbarUtil.showTitle(true, this);
        mUndoButton.setVisibility(View.GONE);

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
                mPresenter.onBackPressed(mImageEditorView.getAlteredImageBitmap());
            else if (mFragmentManager.getBackStackEntryCount() == 1) {
                super.onBackPressed();
            }
        } else finish();
    }

    @OnClick(R.id.undoButton)
    void onClickUndo() {
        mImageEditorView.undo();
    }
}
