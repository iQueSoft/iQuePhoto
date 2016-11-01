package net.iquesoft.iquephoto.view.activity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.annotation.StringRes;
import android.support.design.widget.TabLayout;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;

import net.iquesoft.iquephoto.DataHolder;
import net.iquesoft.iquephoto.R;
import net.iquesoft.iquephoto.common.BaseActivity;
import net.iquesoft.iquephoto.di.IHasComponent;
import net.iquesoft.iquephoto.di.components.DaggerIShareActivityComponent;
import net.iquesoft.iquephoto.di.components.IApplicationComponent;
import net.iquesoft.iquephoto.di.components.IShareActivityComponent;
import net.iquesoft.iquephoto.di.modules.ShareActivityModule;
import net.iquesoft.iquephoto.presenter.ShareActivityPresenterImpl;
import net.iquesoft.iquephoto.utils.BitmapUtil;
import net.iquesoft.iquephoto.utils.ImageHelper;
import net.iquesoft.iquephoto.view.IShareActivityView;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class ShareActivity extends BaseActivity implements IShareActivityView, IHasComponent<IShareActivityComponent> {

    private Bitmap mBitmap;

    @BindView(R.id.shareImage)
    ImageView imageView;

    @BindView(R.id.imageSizeTabLayout)
    TabLayout tabLayout;

    @Inject
    ShareActivityPresenterImpl presenter;

    private IShareActivityComponent mComponent;

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
    protected void setupComponent(IApplicationComponent component) {
        mComponent = DaggerIShareActivityComponent.builder()
                .iApplicationComponent(component)
                .shareActivityModule(new ShareActivityModule(this))
                .build();
        mComponent.inject(this);
    }

    @Override
    public void onBackPressed() {
        finish();
    }

    public IShareActivityComponent getComponent() {
        return mComponent;
    }

    @OnClick(R.id.shareBack)
    void onClickBack() {
        super.onBackPressed();
    }

    @OnClick(R.id.saveButton)
    void onClickSave() {
        BitmapUtil.saveBitmap(mBitmap, this);
    }

    @OnClick(R.id.facebookButton)
    void onClickFacebook() {
        // Todo: Publish to Facebook.
    }

    @OnClick(R.id.instagramButton)
    void onClickInstagram() {
        presenter.shareToInstagram(this, mBitmap);

    }

    @OnClick(R.id.moreButton)
    void onClickMore() {
        // Todo: Share more social network.
    }

    @Override
    public void initImageSizes(String small, String medium, String original) {
        tabLayout.addTab(tabLayout.newTab().setText(small));
        tabLayout.addTab(tabLayout.newTab().setText(medium));
        tabLayout.addTab(tabLayout.newTab().setText(original), true);
    }

    @Override
    public void shareToInstagram(Bitmap bitmap) {
        /*Intent intent = new Intent(Intent.ACTION_SEND);
        intent.setType("image*//*");

        //File media = new File(mediaPath);
        Uri uri;// = Uri.fromFile(media);

        //intent.putExtra(Intent.EXTRA_STREAM, uri);
        startActivity(Intent.createChooser(intent, "Share to"));*/
    }

    @Override
    public void shareToFacebook(Bitmap bitmap) {

    }

    @Override
    public void showAlert(@StringRes int messageBody, String applicationId) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this, R.style.MyAlertDialogStyle);
        builder.setTitle(getString(R.string.application_does_not_exist));
        builder.setMessage(getString(messageBody));

        builder.setPositiveButton(getString(R.string.install), (dialogInterface, i) -> {
            try {
                startActivity(new Intent(Intent.ACTION_VIEW,
                        Uri.parse("market://details?id=" + applicationId)));
            } catch (android.content.ActivityNotFoundException anfe) {
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
