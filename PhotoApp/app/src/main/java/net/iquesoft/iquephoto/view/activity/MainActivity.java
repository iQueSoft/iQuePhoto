package net.iquesoft.iquephoto.view.activity;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.Window;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.Toast;

import net.iquesoft.iquephoto.DataHolder;
import net.iquesoft.iquephoto.PhotoEditorView;
import net.iquesoft.iquephoto.R;
import net.iquesoft.iquephoto.adapters.ToolsAdapter;
import net.iquesoft.iquephoto.common.BaseActivity;
import net.iquesoft.iquephoto.di.IHasComponent;
import net.iquesoft.iquephoto.di.components.IApplicationComponent;
import net.iquesoft.iquephoto.di.components.DaggerIMainActivityComponent;
import net.iquesoft.iquephoto.di.components.IMainActivityComponent;
import net.iquesoft.iquephoto.di.modules.MainActivityModule;
import net.iquesoft.iquephoto.model.Tool;
import net.iquesoft.iquephoto.presenter.MainActivityPresenterImpl;
import net.iquesoft.iquephoto.view.IMainActivityView;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * @author Sergey Belenkiy
 */
public class MainActivity extends BaseActivity implements IMainActivityView, IHasComponent<IMainActivityComponent> {

    @Inject
    MainActivityPresenterImpl presenter;

    private IMainActivityComponent mainActivityComponent;

    @BindView(R.id.photoEditorView)
    PhotoEditorView photoEditorView;

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
        setContentView(R.layout.activity_main);

        ButterKnife.bind(this);

        presenter.createToolsBox();

        bitmap = BitmapFactory.decodeFile(getIntent().getStringExtra("bitmap_path"));

        photoEditorView.setImageBitmap(bitmap);
        //photoEditorView.setSquareEditorListener(this);

        DataHolder.getInstance().setPhotoEditorView(photoEditorView);
    }

    @Override
    protected void setupComponent(IApplicationComponent component) {
        mainActivityComponent = DaggerIMainActivityComponent.builder()
                .mainActivityModule(new MainActivityModule(this))
                .iApplicationComponent(component)
                .build();
        mainActivityComponent.inject(this);
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
        builder.setPositiveButton("OK", (dialogInterface, i) -> {
            finish();
        });
        builder.setNegativeButton("Cancel", (dialogInterface, i1) -> {
            dialogInterface.dismiss();
        });
        builder.show();
    }

    @Override
    public void showToastMessage(int stringResource) {
        Toast.makeText(getApplicationContext(), getString(stringResource), Toast.LENGTH_SHORT).show();
    }

    @Override
    public IMainActivityComponent getComponent() {
        return mainActivityComponent;
    }

    /*@Override
    public void editText(PhotoEditorText giantSquareText) {

    }*/
}
