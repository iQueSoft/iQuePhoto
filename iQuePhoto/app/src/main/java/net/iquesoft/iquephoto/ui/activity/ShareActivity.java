package net.iquesoft.iquephoto.ui.activity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.annotation.StringRes;
import android.support.design.widget.TabLayout;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.WindowManager;
import android.widget.ImageView;

import net.iquesoft.iquephoto.DataHolder;
import net.iquesoft.iquephoto.R;
import net.iquesoft.iquephoto.common.BaseActivity;
import net.iquesoft.iquephoto.di.HasComponent;
import net.iquesoft.iquephoto.di.components.DaggerShareComponent;
import net.iquesoft.iquephoto.di.components.AppComponent;
import net.iquesoft.iquephoto.di.components.ShareComponent;
import net.iquesoft.iquephoto.di.modules.ShareModule;
import net.iquesoft.iquephoto.presentation.presenter.activity.SharePresenterImpl;
import net.iquesoft.iquephoto.presentation.view.activity.ShareView;
import net.iquesoft.iquephoto.task.ImageSaveTask;
import net.iquesoft.iquephoto.util.BitmapUtil;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

import static net.iquesoft.iquephoto.presentation.presenter.activity.SharePresenterImpl.FACEBOOK_ID;
import static net.iquesoft.iquephoto.presentation.presenter.activity.SharePresenterImpl.INSTAGRAM_ID;

public class ShareActivity extends BaseActivity implements ShareView, HasComponent<ShareComponent> {

    private Bitmap mBitmap;

    @BindView(R.id.shareImageView)
    ImageView imageView;

    @BindView(R.id.imageSizeTabLayout)
    TabLayout tabLayout;

    @Inject
    SharePresenterImpl presenter;

    private ShareComponent mComponent;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);

        setContentView(R.layout.activity_share);

        ButterKnife.bind(this);

        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                Log.i(ShareActivity.class.getSimpleName(), String.valueOf(tab.getPosition()));
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });

        mBitmap = DataHolder.getInstance().getShareBitmap();

        presenter.calculateSizesForCompressing(mBitmap);

        imageView.setImageBitmap(mBitmap);
    }

    @Override
    protected void setupComponent(AppComponent component) {
        mComponent = DaggerShareComponent.builder()
                .appComponent(component)
                .shareModule(new ShareModule(this))
                .build();
        mComponent.inject(this);
    }

    @Override
    public void onBackPressed() {
        finish();
    }

    public ShareComponent getComponent() {
        return mComponent;
    }

    @OnClick(R.id.shareBackButton)
    void onClickBack() {
        super.onBackPressed();
    }

    @OnClick(R.id.saveButton)
    void onClickSave() {
        new ImageSaveTask(this, mBitmap).execute();
    }

    @OnClick(R.id.facebookButton)
    void onClickFacebook() {
        presenter.shareTo(this, FACEBOOK_ID, mBitmap);
    }

    @OnClick(R.id.instagramButton)
    void onClickInstagram() {
        presenter.shareTo(this, INSTAGRAM_ID, mBitmap);
    }

    @OnClick(R.id.moreButton)
    void onClickMore() {
        Intent intent = new Intent(Intent.ACTION_SEND);
        intent.setType("image/*");
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

        intent.putExtra(Intent.EXTRA_STREAM,
                BitmapUtil.getBitmapUri(this, mBitmap));

        startActivity(Intent.createChooser(intent, getString(R.string.share_more)));
    }

    @Override
    public void initImageSizes(String small, String medium, String original) {
        tabLayout.addTab(tabLayout.newTab().setText(small));
        tabLayout.addTab(tabLayout.newTab().setText(medium));
        tabLayout.addTab(tabLayout.newTab().setText(original), true);
    }

    @Override
    public void share(Bitmap bitmap, String applicationId) {
        Intent intent = new Intent(Intent.ACTION_SEND);
        intent.setType("image/*");
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

        intent.putExtra(Intent.EXTRA_STREAM,
                BitmapUtil.getBitmapUri(this, mBitmap));

        intent.setPackage(applicationId);
        startActivity(intent);
    }

    @Override
    public void showAlert(@StringRes int messageBody, String applicationId) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this, R.style.AlertMaterialDialog);
        builder.setTitle(getString(R.string.application_does_not_exist));
        builder.setMessage(getString(messageBody));

        builder.setPositiveButton(getString(R.string.install), (dialogInterface, i) -> {
            try {
                startActivity(new Intent(Intent.ACTION_VIEW,
                        Uri.parse("market://details?id=" + applicationId)));
            } catch (android.content.ActivityNotFoundException e) {
                startActivity(new Intent(Intent.ACTION_VIEW,
                        Uri.parse("https://play.google.com/store/apps/details?id=" + applicationId)));
            }
        });

        builder.setNegativeButton(getString(R.string.dismiss), (dialogInterface, i1) -> {
            dialogInterface.dismiss();
        });
        builder.show();
    }
}
