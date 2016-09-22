package net.iquesoft.iquephoto.view.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;

import net.iquesoft.iquephoto.common.BaseActivity;
import net.iquesoft.iquephoto.di.IHasComponent;
import net.iquesoft.iquephoto.di.components.DaggerICropActivityComponent;
import net.iquesoft.iquephoto.di.components.DaggerIShareActivityComponent;
import net.iquesoft.iquephoto.di.components.IApplicationComponent;
import net.iquesoft.iquephoto.di.components.ICropActivityComponent;
import net.iquesoft.iquephoto.di.modules.CropActivityModule;
import net.iquesoft.iquephoto.di.modules.ShareActivityModule;
import net.iquesoft.iquephoto.presenter.CropActivityPresenterImpl;
import net.iquesoft.iquephoto.view.ICropActivityView;

import javax.inject.Inject;

public class CropActivity extends BaseActivity implements ICropActivityView, IHasComponent<ICropActivityComponent> {

    private ICropActivityComponent cropActivityComponent;

    @Inject
    CropActivityPresenterImpl presenter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    protected void setupComponent(IApplicationComponent component) {
        cropActivityComponent = DaggerICropActivityComponent.builder()
                .iApplicationComponent(component)
                .cropActivityModule(new CropActivityModule(this))
                .build();
        cropActivityComponent.inject(this);
    }


    @Override
    public ICropActivityComponent getComponent() {
        return cropActivityComponent;
    }
}
