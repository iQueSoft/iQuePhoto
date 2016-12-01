package net.iquesoft.iquephoto.ui.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.View;
import android.view.WindowManager;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.arellomobile.mvp.presenter.InjectPresenter;

import net.iquesoft.iquephoto.R;
import net.iquesoft.iquephoto.mvp.common.BaseActivity;
import net.iquesoft.iquephoto.mvp.models.ImageAlbum;
import net.iquesoft.iquephoto.mvp.views.activity.GalleryView;
import net.iquesoft.iquephoto.mvp.presenters.activity.GalleryPresenter;
import net.iquesoft.iquephoto.ui.fragments.GalleryAlbumsFragment;
import net.iquesoft.iquephoto.ui.fragments.GalleryImagesFragment;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

// TODO: Change album images showing.
// TODO: Check image orientation.
public class GalleryActivity extends BaseActivity implements GalleryView {
    @InjectPresenter
    GalleryPresenter presenter;

    @BindView(R.id.galleryHeaderTextView)
    TextView headerTextView;

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
        fragmentTransaction.add(R.id.galleryFragmentFrameLayout, new GalleryAlbumsFragment())
                .commit();
    }

    @Override
    public void onBackPressed() {
        if (mFragmentManager.getBackStackEntryCount() == 1) {
            super.onBackPressed();
            headerTextView.setText(R.string.gallery);
        } else {
            finish();
        }
    }

    @OnClick(R.id.buttonGalleryBack)
    void onClickBack() {
        onBackPressed();
    }

    @Override
    public void showImages(ImageAlbum imageAlbum) {
        headerTextView.setText(imageAlbum.getName());

        FragmentTransaction fragmentTransaction = mFragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.galleryFragmentFrameLayout, GalleryImagesFragment.newInstance(imageAlbum.getImages()))
                .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE)
                .addToBackStack(null)
                .commit();
    }

    @Override
    public void editImage(String imagePath) {

    }

}
