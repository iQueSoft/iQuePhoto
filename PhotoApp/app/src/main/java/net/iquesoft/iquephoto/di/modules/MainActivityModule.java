package net.iquesoft.iquephoto.di.modules;

import net.iquesoft.iquephoto.presenter.BrightnessFragmentPresenterImpl;
import net.iquesoft.iquephoto.presenter.DrawFragmentPresenterImpl;
import net.iquesoft.iquephoto.presenter.CropFragmentPresenterImpl;
import net.iquesoft.iquephoto.presenter.FiltersFragmentPresenterImpl;
import net.iquesoft.iquephoto.presenter.MainActivityPresenterImpl;
import net.iquesoft.iquephoto.presenter.MemeFragmentPresenterImpl;
import net.iquesoft.iquephoto.presenter.RotationFragmentPresenterImpl;
import net.iquesoft.iquephoto.presenter.ShowStickersFragmentPresenterImpl;
import net.iquesoft.iquephoto.presenter.StickersFragmentPresenterImpl;
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
    public DrawFragmentPresenterImpl provideBrushFragmentPresenterImpl() {
        return new DrawFragmentPresenterImpl();
    }

    @Provides
    public StickersFragmentPresenterImpl provideStickersFragmentPresenterImpl() {
        return new StickersFragmentPresenterImpl();
    }

    @Provides
    public ShowStickersFragmentPresenterImpl provideShowStickersFragmentPresenterImpl() {
        return new ShowStickersFragmentPresenterImpl();
    }

    @Provides
    public CropFragmentPresenterImpl provideCropFragmentPresenterImpl() {
        return new CropFragmentPresenterImpl();
    }

    @Provides
    public MemeFragmentPresenterImpl provideMemeFragmentPresenterImpl() {
        return new MemeFragmentPresenterImpl();
    }
}
