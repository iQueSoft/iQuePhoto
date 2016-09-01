package net.iquesoft.iquephoto.di.components;

import net.iquesoft.iquephoto.di.ActivityScope;
import net.iquesoft.iquephoto.di.modules.MainActivityModule;
import net.iquesoft.iquephoto.view.fragment.BrightnessFragment;
import net.iquesoft.iquephoto.view.fragment.BrushFragment;
import net.iquesoft.iquephoto.view.fragment.FiltersFragment;
import net.iquesoft.iquephoto.view.activity.MainActivity;
import net.iquesoft.iquephoto.view.fragment.RotationFragment;
import net.iquesoft.iquephoto.view.fragment.ShowStickersFragment;
import net.iquesoft.iquephoto.view.fragment.StickersFragment;
import net.iquesoft.iquephoto.view.fragment.TextFragment;

import dagger.Component;

@ActivityScope
@Component(dependencies = IApplicationComponent.class,
        modules = MainActivityModule.class)

public interface IMainActivityComponent {

    void inject(MainActivity mainActivity);

    void inject(FiltersFragment filtersFragment);

    void inject(RotationFragment rotationFragment);

    void inject(BrightnessFragment brightnessFragment);

    void inject(TextFragment textFragment);

    void inject(BrushFragment brushFragment);

    void inject(StickersFragment stickersFragment);

    void inject(ShowStickersFragment showStickersFragment);
}
