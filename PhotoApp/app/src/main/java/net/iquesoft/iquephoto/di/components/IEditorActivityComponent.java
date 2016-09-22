package net.iquesoft.iquephoto.di.components;

import net.iquesoft.iquephoto.di.ActivityScope;
import net.iquesoft.iquephoto.di.modules.EditorActivityModule;
import net.iquesoft.iquephoto.view.fragment.AdjustFragment;
import net.iquesoft.iquephoto.view.fragment.DrawingFragment;
import net.iquesoft.iquephoto.view.fragment.CropFragment;
import net.iquesoft.iquephoto.view.fragment.FiltersFragment;
import net.iquesoft.iquephoto.view.activity.EditorActivity;
import net.iquesoft.iquephoto.view.fragment.MemeFragment;
import net.iquesoft.iquephoto.view.fragment.RotationFragment;
import net.iquesoft.iquephoto.view.fragment.ShowStickersFragment;
import net.iquesoft.iquephoto.view.fragment.StickersFragment;
import net.iquesoft.iquephoto.view.fragment.TextFragment;

import dagger.Component;

@ActivityScope
@Component(dependencies = IApplicationComponent.class,
        modules = EditorActivityModule.class)

public interface IEditorActivityComponent {

    void inject(EditorActivity editorActivity);

    void inject(FiltersFragment filtersFragment);

    void inject(RotationFragment rotationFragment);

    void inject(AdjustFragment adjustFragment);

    void inject(TextFragment textFragment);

    void inject(DrawingFragment drawingFragment);

    void inject(StickersFragment stickersFragment);

    void inject(ShowStickersFragment showStickersFragment);

    void inject(CropFragment cropFragment);

    void inject(MemeFragment memeFragment);
}
