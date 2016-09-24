package net.iquesoft.iquephoto.view.activity;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.provider.MediaStore;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.DisplayMetrics;
import android.view.Display;
import android.view.View;
import android.view.Window;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.isseiaoki.simplecropview.util.Utils;

import net.iquesoft.iquephoto.DataHolder;
import net.iquesoft.iquephoto.core.EditorImageView;
import net.iquesoft.iquephoto.core.EditorView;
import net.iquesoft.iquephoto.R;
import net.iquesoft.iquephoto.adapters.ToolsAdapter;
import net.iquesoft.iquephoto.common.BaseActivity;
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
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * @author Sergey Belenkiy
 */
public class EditorActivity extends BaseActivity implements IEditorActivityView, IHasComponent<IEditorActivityComponent> {

    private ExecutorService mExecutor;

    @Inject
    EditorActivityPresenterImpl presenter;

    private IEditorActivityComponent editorActivityComponent;

    /*@BindView(R.id.photoEditorView)
    EditorView editorView;*/

    @BindView(R.id.editorImageView)
    EditorImageView editorView;

    @BindView(R.id.toolsView)
    RecyclerView tools;

    @BindView(R.id.toolSettingsLayout)
    FrameLayout toolSettingsContainer;

    private Bitmap bitmap;

    private Tool currentTool;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_editor);

        ButterKnife.bind(this);

        presenter.createToolsBox();

        /*try {
            bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), getIntent().getData());
        } catch (IOException e) {
            e.printStackTrace();
        }*/
        mExecutor = Executors.newSingleThreadExecutor();

        final Uri uri = getIntent().getData();

        mExecutor.submit(new LoadScaledImageTask(this, uri, editorView, calcImageSize()));

        //editorView.setImageBitmap(bitmap);
        //photoEditorView.setFreeTransform(true);
        //photoEditorView.setSquareEditorListener(this);

        DataHolder.getInstance().setEditorView(editorView);
    }

    @Override
    protected void setupComponent(IApplicationComponent component) {
        editorActivityComponent = DaggerIEditorActivityComponent.builder()
                .editorActivityModule(new EditorActivityModule(this))
                .iApplicationComponent(component)
                .build();
        editorActivityComponent.inject(this);
    }

    @Override
    protected void onDestroy() {
        mExecutor.shutdown();
        super.onDestroy();
    }

    @Override
    public void onBackPressed() {
        presenter.onBackPressed();
    }

    @Override
    public void initTools(ToolsAdapter toolsAdapter) {
        toolsAdapter.setToolsListener(tool -> {
            if (tool != currentTool) {
                try {
                    presenter.changeTool(tool);
                    switch (tool.getTitle()) {
                        case R.string.text:
                            //editorView.setTextActivated(true);
                            break;
                        case R.string.drawing:
                            //editorView.setDrawingActivated(true);
                            break;
                        default:
                            //editorView.setTextActivated(false);
                            //editorView.setDrawingActivated(false);
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
    public IEditorActivityComponent getComponent() {
        return editorActivityComponent;
    }

    public int calcImageSize() {
        DisplayMetrics metrics = new DisplayMetrics();
        Display display = getWindowManager().getDefaultDisplay();
        display.getMetrics(metrics);
        return Math.min(Math.max(metrics.widthPixels, metrics.heightPixels), 2048);
    }

    public static class LoadScaledImageTask implements Runnable {
        private Handler mHandler = new Handler(Looper.getMainLooper());
        Context context;
        Uri uri;
        EditorImageView editorView;
        int width;

        public LoadScaledImageTask(Context context, Uri uri, EditorImageView editorView, int width) {
            this.context = context;
            this.uri = uri;
            this.editorView = editorView;
            this.width = width;
        }

        @Override
        public void run() {
            int maxSize = Utils.getMaxSize();
            int requestSize = Math.min(width, maxSize);
            try {
                final Bitmap bitmapFromUri = Utils.decodeSampledBitmapFromUri(context, uri, requestSize);
                mHandler.post(() -> editorView.setImageBitmap(bitmapFromUri));
            } catch (OutOfMemoryError | Exception e) {
                e.printStackTrace();
            }
        }
    }
}
