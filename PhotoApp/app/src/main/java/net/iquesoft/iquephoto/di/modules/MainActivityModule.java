package net.iquesoft.iquephoto.di.modules;

import net.iquesoft.iquephoto.presenter.BrightnessFragmentPresenterImpl;
import net.iquesoft.iquephoto.presenter.BrushFragmentPresenterImpl;
import net.iquesoft.iquephoto.presenter.FiltersFragmentPresenterImpl;
import net.iquesoft.iquephoto.presenter.MainActivityPresenterImpl;
import net.iquesoft.iquephoto.presenter.RotationFragmentPresenterImpl;
import net.iquesoft.iquephoto.presenter.TextFragmentPresenterImpl;
import net.iquesoft.iquephoto.view.IMainActivityView;

import dagger.Module;
import dagger.Provides;

@Module
public class MainActivityModule {

    private IMainActivityView view;

    public MainActivityModule(IMainActivityView view) {
        this.view = view;
    }

    @Provides
    public MainActivityPresenterImpl provideMainActivityPresenterImpl(IMainActivityView view) {
        return new MainActivityPresenterImpl(view);
    }

    @Provides
    public IMainActivityView provideView() {
        return view;
    }

    @Provides
    public FiltersFragmentPresenterImpl provideFiltersFragmentPresenterImpl() {
        return new FiltersFragmentPresenterImpl();
    }

    @Provides
    public RotationFragmentPresenterImpl provideRotationFragmentPresenterImpl() {
        return new RotationFragmentPresenterImpl();
    }

    @Provides
    public BrightnessFragmentPresenterImpl provideBrightnessFragmentPresenterImpl() {
        return new BrightnessFragmentPresenterImpl();
    }

    @Provides
    public TextFragmentPresenterImpl provideTextFragmentPresenterImpl() {
        return new TextFragmentPresenterImpl();
    }

    @Provides
    public BrushFragmentPresenterImpl provideBrushFragmentPresenterImpl() {
        return new BrushFragmentPresenterImpl();
    }
}
