package net.iquesoft.iquephoto.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.WindowManager;
import android.widget.LinearLayout;
import android.widget.TextView;

import net.iquesoft.iquephoto.R;
import net.iquesoft.iquephoto.adapter.ImageAlbumsAdapter;
import net.iquesoft.iquephoto.adapter.ImagesAdapter;
import net.iquesoft.iquephoto.common.BaseActivity;
import net.iquesoft.iquephoto.di.IHasComponent;
import net.iquesoft.iquephoto.di.components.DaggerIGalleryActivityComponent;
import net.iquesoft.iquephoto.di.components.IApplicationComponent;
import net.iquesoft.iquephoto.di.components.IGalleryActivityComponent;
import net.iquesoft.iquephoto.di.modules.GalleryActivityModule;
import net.iquesoft.iquephoto.model.Image;
import net.iquesoft.iquephoto.model.ImageAlbum;
import net.iquesoft.iquephoto.presentation.view.activity.GalleryView;
import net.iquesoft.iquephoto.presentation.presenter.activity.GalleryActivityPresenterImpl;
import net.iquesoft.iquephoto.ui.fragment.GalleryAlbumsFragment;
import net.iquesoft.iquephoto.ui.fragment.GalleryImagesFragment;

import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

// TODO: Check image orientation.
public class GalleryActivity extends BaseActivity implements GalleryView, IHasComponent<IGalleryActivityComponent> {

    private IGalleryActivityComponent mComponent;

    private ImageAlbumsAdapter mImageAlbumsAdapter;

    @BindView(R.id.galleryHeaderTextView)
    TextView headerTextView;

    @BindView(R.id.galleryRecyclerView)
    RecyclerView recyclerView;

    @BindView(R.id.galleryNoImagesLinearLayout)
    LinearLayout noImagesLinearLayout;

    @Inject
    GalleryActivityPresenterImpl presenter;

    /*@Inject
    GalleryImagesFragment galleryImagesFragment;

    @Inject
    GalleryAlbumsFragment galleryAlbumsFragment;*/

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);

        setContentView(R.layout.activity_gallery);

        ButterKnife.bind(this);

        recyclerView.setLayoutManager(new GridLayoutManager(this, 3));

        presenter.fetchImages(this);
    }

    @Override
    public void onBackPressed() {
        presenter.onBackPressed(recyclerView);
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
    public void setupFoldersAdapter(List<ImageAlbum> imageAlba) {
        mImageAlbumsAdapter = new ImageAlbumsAdapter(imageAlba);
        mImageAlbumsAdapter.setOnAlbumClickListener(imageFolder -> {
            presenter.folderClicked(imageFolder);
        });

        recyclerView.setAdapter(mImageAlbumsAdapter);
    }

    @Override
    public void showImages(String folderName, List<Image> images) {
        ImagesAdapter imagesAdapter = new ImagesAdapter(images);

        imagesAdapter.setOnImageClickListener(image -> {
            Intent intent = new Intent(GalleryActivity.this, PreviewActivity.class);
            intent.putExtra("Image", image.getPath());
            startActivity(intent);
            finish();
        });

        recyclerView.setAdapter(imagesAdapter);

        headerTextView.setText(folderName);
    }

    @Override
    public void showHaveNoImages() {
        recyclerView.setVisibility(View.GONE);
        noImagesLinearLayout.setVisibility(View.VISIBLE);
    }

    @Override
    public void showFolders() {
        headerTextView.setText(R.string.gallery);
        recyclerView.setAdapter(mImageAlbumsAdapter);
    }

    @Override
    public void navigateBack() {
        super.onBackPressed();
    }

    @OnClick(R.id.takePhotoButton)
    void OnClickTakePhotoButton() {
        // TODO: Take photo from application custom camera.
    }
}
