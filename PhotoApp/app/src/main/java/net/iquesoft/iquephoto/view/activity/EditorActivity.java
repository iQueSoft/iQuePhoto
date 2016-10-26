package net.iquesoft.iquephoto.view.activity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.Toast;

import net.iquesoft.iquephoto.DataHolder;
import net.iquesoft.iquephoto.R;
import net.iquesoft.iquephoto.adapters.ToolsAdapter;
import net.iquesoft.iquephoto.common.BaseActivity;
import net.iquesoft.iquephoto.core.ImageEditorView;
import net.iquesoft.iquephoto.di.IHasComponent;
import net.iquesoft.iquephoto.di.components.IApplicationComponent;
import net.iquesoft.iquephoto.di.components.DaggerIEditorActivityComponent;
import net.iquesoft.iquephoto.di.components.IEditorActivityComponent;
import net.iquesoft.iquephoto.di.modules.EditorActivityModule;
import net.iquesoft.iquephoto.model.Tool;
import net.iquesoft.iquephoto.presenter.EditorActivityPresenterImpl;
import net.iquesoft.iquephoto.utils.ImageHelper;
import net.iquesoft.iquephoto.view.IEditorActivityView;


import java.io.IOException;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class EditorActivity extends BaseActivity implements IEditorActivityView, IHasComponent<IEditorActivityComponent> {

    @Inject
    EditorActivityPresenterImpl presenter;

    private IEditorActivityComponent mComponent;

    @BindView(R.id.editorImageView)
    ImageEditorView imageEditorView;

    @BindView(R.id.toolsView)
    RecyclerView tools;

    @BindView(R.id.toolSettingsContainer)
    FrameLayout toolSettingsContainer;

    private Bitmap mBitmap;

    private Tool currentTool;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);

        setContentView(R.layout.activity_editor);

        ButterKnife.bind(this);

        presenter.createToolsBox();

        try {
            mBitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), getIntent().getData());
        } catch (IOException e) {
            e.printStackTrace();
        }

        DataHolder.getInstance().setDrawable(getScaledDrawable());

        Log.i(EditorActivity.class.getSimpleName(), "Height " + mBitmap.getHeight() + "\nWidth " + mBitmap.getWidth());

        imageEditorView.setImageBitmap(mBitmap);

        DataHolder.getInstance().setEditorView(imageEditorView);
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
//        mExecutor.shutdown();
        super.onDestroy();
    }

    @Override
    public void onBackPressed() {
        presenter.onBackPressed(mBitmap, mBitmap);
    }

    private Drawable getScaledDrawable() {
        Bitmap bitmap = Bitmap.createScaledBitmap(mBitmap, 640, 640, false);

        return new BitmapDrawable(getResources(), bitmap);
    }

    @Override
    public void initTools(ToolsAdapter toolsAdapter) {
        toolsAdapter.setToolsListener(tool -> {
            if (tool != currentTool) {
                try {
                    presenter.changeTool(tool);
                    switch (tool.getTitle()) {
                        case R.string.stickers:
                            imageEditorView.setStickersActivated(true);
                            break;
                        case R.string.text:
                            imageEditorView.setTextActivated(true);
                            break;
                        case R.string.drawing:
                            imageEditorView.setDrawingActivated(true);
                            break;
                        default:
                            imageEditorView.setStickersActivated(false);
                            imageEditorView.setTextActivated(false);
                            imageEditorView.setDrawingActivated(false);
                            break;
                    }
                } catch (NullPointerException e) {
                    e.printStackTrace();

                    Toast.makeText(getBaseContext(), getString(tool.getTitle()) + " coming soon!", Toast.LENGTH_SHORT).show();
                }
            } else {
                toolSettingsContainer.setVisibility(View.GONE);
                currentTool = null;
            }
        });
        tools.setLayoutManager(new LinearLayoutManager(this, LinearLayout.HORIZONTAL, false));
        tools.setAdapter(toolsAdapter);
    }

    @Override
    public void changeTool(Tool tool) {
        toolSettingsContainer.setVisibility(View.VISIBLE);
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();

        //fragmentTransaction.setCustomAnimations(R.anim.slide_up, R.anim.slide_down);
        fragmentTransaction.replace(toolSettingsContainer.getId(), tool.getFragment()).commit();

        currentTool = tool;
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

        startActivity(intent);
    }


    public IEditorActivityComponent getComponent() {
        return mComponent;
    }
}
