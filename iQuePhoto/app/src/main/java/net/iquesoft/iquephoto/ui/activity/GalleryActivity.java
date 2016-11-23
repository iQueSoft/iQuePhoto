package net.iquesoft.iquephoto.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.WindowManager;
import android.widget.LinearLayout;
import android.widget.TextView;

import net.iquesoft.iquephoto.R;
import net.iquesoft.iquephoto.adapter.ImageAlbumsAdapter;
import net.iquesoft.iquephoto.common.BaseActivity;
import net.iquesoft.iquephoto.di.HasComponent;
import net.iquesoft.iquephoto.di.components.DaggerIGalleryActivityComponent;
import net.iquesoft.iquephoto.di.components.IApplicationComponent;
import net.iquesoft.iquephoto.di.components.IGalleryActivityComponent;
import net.iquesoft.iquephoto.di.modules.GalleryActivityModule;
import net.iquesoft.iquephoto.model.Image;
import net.iquesoft.iquephoto.model.ImageAlbum;
import net.iquesoft.iquephoto.presentation.presenter.fragment.GalleryAlbumsPresenterImpl;
import net.iquesoft.iquephoto.presentation.presenter.fragment.GalleryImagesPresenterImpl;
import net.iquesoft.iquephoto.presentation.view.activity.GalleryView;
import net.iquesoft.iquephoto.presentation.presenter.activity.GalleryPresenterImpl;
import net.iquesoft.iquephoto.ui.fragment.GalleryAlbumsFragment;
import net.iquesoft.iquephoto.ui.fragment.GalleryImagesFragment;

import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

// TODO: Change album images showing.
// TODO: Check image orientation.
public class GalleryActivity extends BaseActivity implements GalleryView, HasComponent<IGalleryActivityComponent> {
    @BindView(R.id.galleryHeaderTextView)
    TextView headerTextView;

    @BindView(R.id.galleryNoImagesLinearLayout)
    LinearLayout noImagesLinearLayout;

    @Inject
    GalleryPresenterImpl presenter;

    @Inject
    GalleryImagesFragment galleryImagesFragment;

    @Inject
    GalleryAlbumsFragment galleryAlbumsFragment;

    private IGalleryActivityComponent mComponent;

    private FragmentManager mFragmentManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);

        setContentView(R.layout.activity_gallery);

        ButterKnife.bind(this);

        mFragmentManager = getSupportFragmentManager();

        FragmentTransaction fragmentTransaction = mFragmentManager.beginTransaction();
        fragmentTransaction.add(R.id.galleryFragmentFrameLayout, galleryAlbumsFragment)
                .commit();
    }

    @Override
    public void onBackPressed() {
        if (mFragmentManager.getBackStackEntryCount() == 1) {
            super.onBackPressed();
        } else {
            finish();
        }
    }

    @Override
    protected void setupComponent(IApplicationComponent component) {
        mComponent = DaggerIGalleryActivityComponent.builder()
                .iApplicationComponent(component)
                .galleryActivityModule(new GalleryActivityModule(this))
                .build();
        mComponent.inject(this);
    }

    @OnClick(R.id.buttonGalleryBack)
    void onClickBack() {
        onBackPressed();
    }

    @Override
    public IGalleryActivityComponent getComponent() {
        return mComponent;
    }

    @Override
    public void showImages(String albumName) {
        headerTextView.setText(albumName);

        FragmentTransaction fragmentTransaction = mFragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.galleryFragmentFrameLayout, galleryImagesFragment)
                .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE)
                .addToBackStack(null)
                .commit();
    }

    @Override
    public void setAlbumImages(List<Image> images) {
        galleryImagesFragment.setImages(images);
    }

    @Override
    public void showHaveNoImages() {
        noImagesLinearLayout.setVisibility(View.VISIBLE);
    }

    @Override
    public void editImage(String imagePath) {
        Intent intent = new Intent(GalleryActivity.this, PreviewActivity.class);
        intent.putExtra("Image", imagePath);
        startActivity(intent);
        finish();
    }

    // TODO: Maybe change startActivity() to startActivityForResult().
    @OnClick(R.id.takePhotoButton)
    void onClickTakePhotoButton() {
        Intent intent = new Intent(GalleryActivity.this, CameraActivity.class);
        startActivity(intent);
    }
}
