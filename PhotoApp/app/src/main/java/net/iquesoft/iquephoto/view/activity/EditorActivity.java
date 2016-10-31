package net.iquesoft.iquephoto.view.activity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.FrameLayout;
import android.widget.RelativeLayout;
import android.widget.Toast;

import net.iquesoft.iquephoto.DataHolder;
import net.iquesoft.iquephoto.R;
import net.iquesoft.iquephoto.common.BaseActivity;
import net.iquesoft.iquephoto.core.ImageEditorView;
import net.iquesoft.iquephoto.di.IHasComponent;
import net.iquesoft.iquephoto.di.components.IApplicationComponent;
import net.iquesoft.iquephoto.di.components.DaggerIEditorActivityComponent;
import net.iquesoft.iquephoto.di.components.IEditorActivityComponent;
import net.iquesoft.iquephoto.di.modules.EditorActivityModule;
import net.iquesoft.iquephoto.presenter.EditorActivityPresenterImpl;
import net.iquesoft.iquephoto.utils.ImageHelper;
import net.iquesoft.iquephoto.view.IEditorActivityView;
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

        DataHolder.getInstance().setBitmap(mBitmap);

        Log.i(EditorActivity.class.getSimpleName(), "Height " + mBitmap.getHeight() + "\nWidth " + mBitmap.getWidth());

        imageEditorView.setImageBitmap(mBitmap);

        DataHolder.getInstance().setEditorView(imageEditorView);

        mFragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = mFragmentManager.beginTransaction();
        fragmentTransaction.replace(fragmentContainer.getId(), toolsFragment).commit();
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
        presenter.onBackPressed(mBitmap, mBitmap);
    }

    @Override
    public void showAlertDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this, R.style.MyAlertDialogStyle);
        builder.setMessage(getString(R.string.on_back_alert));

        builder.setPositiveButton(getString(R.string.ok), (dialogInterface, i) -> {
            finish();
        });

        // Todo: Make save button in AlertDialog
        builder.setNeutralButton(getString(R.string.save), ((dialogInterface1, i) -> {
            String path = ImageHelper.getPath(getString(R.string.app_name));
            /*try {
                photoEditorView.saveImages(path);
            } catch (IOException e) {
                e.printStackTrace();
            }*/

        }));

        builder.setNegativeButton(getString(R.string.cancel), (dialogInterface, i1) -> {
            dialogInterface.dismiss();
        });
        builder.show();
    }

    @Override
    public void setupFragment(Fragment fragment) {
        FragmentTransaction fragmentTransaction = mFragmentManager.beginTransaction();
        fragmentTransaction.replace(fragmentContainer.getId(), fragment);
        fragmentTransaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commit();
        // TODO: Hide header.
        //editorHeader.setVisibility(View.GONE);
    }

    @Override
    public void showToastMessage(int stringResource) {
        Toast.makeText(getApplicationContext(), getString(stringResource), Toast.LENGTH_SHORT).show();
    }

    @Override
    public void goBack() {
        super.onBackPressed();
    }

    @OnClick(R.id.buttonShare)
    void onClickShare() {

        Log.i(EditorActivity.class.getSimpleName(), "Height " + mBitmap.getHeight() + "\nWidth " + mBitmap.getWidth());
        //DataHolder.getInstance().setShareBitmap(mEditorImageView.getAlteredBitmap());

        Intent intent = new Intent(EditorActivity.this, ShareActivity.class);
        imageEditorView.makeImage(intent);
    }


    public IEditorActivityComponent getComponent() {
        return mComponent;
    }
}
