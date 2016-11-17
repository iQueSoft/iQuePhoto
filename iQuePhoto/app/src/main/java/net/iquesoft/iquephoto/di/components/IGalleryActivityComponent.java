package net.iquesoft.iquephoto.di.components;

import net.iquesoft.iquephoto.di.ActivityScope;
import net.iquesoft.iquephoto.di.modules.GalleryActivityModule;
import net.iquesoft.iquephoto.ui.activity.GalleryActivity;

import dagger.Component;

@ActivityScope
@Component(dependencies = IApplicationComponent.class,
        modules = GalleryActivityModule.class)

public interface IGalleryActivityComponent {
    void inject(GalleryActivity galleryActivity);
}
