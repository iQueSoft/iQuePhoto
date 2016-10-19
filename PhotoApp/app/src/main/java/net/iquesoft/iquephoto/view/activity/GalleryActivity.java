package net.iquesoft.iquephoto.view.activity;

import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.WindowManager;

import net.iquesoft.iquephoto.R;
import net.iquesoft.iquephoto.adapters.GalleryImageAdapter;
import net.iquesoft.iquephoto.common.BaseActivity;
import net.iquesoft.iquephoto.di.IHasComponent;
import net.iquesoft.iquephoto.di.components.DaggerIGalleryActivityComponent;
import net.iquesoft.iquephoto.di.components.IApplicationComponent;
import net.iquesoft.iquephoto.di.components.IGalleryActivityComponent;
import net.iquesoft.iquephoto.di.modules.GalleryActivityModule;
import net.iquesoft.iquephoto.presenter.GalleryActivityPresenterImpl;
import net.iquesoft.iquephoto.utils.GalleryImageLoader;
import net.iquesoft.iquephoto.view.IGalleryActivityView;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;

public class GalleryActivity extends BaseActivity implements IGalleryActivityView, IHasComponent<IGalleryActivityComponent> {

    @Inject
    GalleryActivityPresenterImpl presenter;

    private IGalleryActivityComponent mComponent;

    private GalleryImageAdapter mAdapter;

    @BindView(R.id.galleryRecyclerView)
    RecyclerView mRecyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);

        setContentView(R.layout.activity_gallery);

        GalleryImageLoader galleryImageLoader = new GalleryImageLoader(this);
        galleryImageLoader.execute();

        galleryImageLoader.setListener(imageGalleryList -> {
            mAdapter = new GalleryImageAdapter(imageGalleryList);
            mRecyclerView.setLayoutManager(new GridLayoutManager(this, 4));
            mRecyclerView.setAdapter(mAdapter);
        });

        ButterKnife.bind(this);
    }

    @Override
    protected void setupComponent(IApplicationComponent component) {
        mComponent = DaggerIGalleryActivityComponent.builder()
                .iApplicationComponent(component)
                .galleryActivityModule(new GalleryActivityModule(this))
                .build();
        mComponent.inject(this);
    }

    @Override
    public IGalleryActivityComponent getComponent() {
        return mComponent;
    }
}
