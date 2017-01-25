package net.iquesoft.iquephoto.di;

import android.content.Context;

import net.iquesoft.iquephoto.core.editor.model.EditorSticker;
import net.iquesoft.iquephoto.core.editor.model.EditorText;
import net.iquesoft.iquephoto.di.modules.AppModule;
import net.iquesoft.iquephoto.di.modules.EditorModule;
import net.iquesoft.iquephoto.presentation.presenters.fragment.AdjustPresenter;
import net.iquesoft.iquephoto.presentation.presenters.fragment.ColorsPresenter;
import net.iquesoft.iquephoto.presentation.presenters.fragment.DrawingPresenter;
import net.iquesoft.iquephoto.presentation.presenters.fragment.FiltersPresenter;
import net.iquesoft.iquephoto.presentation.presenters.fragment.FontsPresenter;
import net.iquesoft.iquephoto.presentation.presenters.fragment.FramesPresenter;
import net.iquesoft.iquephoto.presentation.presenters.fragment.OverlaysPresenter;
import net.iquesoft.iquephoto.presentation.presenters.fragment.StickersPresenter;
import net.iquesoft.iquephoto.presentation.presenters.fragment.StickersSetPresenter;
import net.iquesoft.iquephoto.presentation.presenters.fragment.ToolsPresenter;
import net.iquesoft.iquephoto.ui.dialogs.FontPickerDialog;

import javax.inject.Singleton;

import dagger.Component;

@Singleton
@Component(modules = {AppModule.class, EditorModule.class})
public interface AppComponent {
    Context getContext();

    void inject(ToolsPresenter toolsPresenter);

    void inject(AdjustPresenter adjustPresenter);

    void inject(FiltersPresenter filtersPresenter);

    void inject(OverlaysPresenter overlaysPresenter);

    void inject(FramesPresenter framesPresenter);

    void inject(StickersSetPresenter stickersSetPresenter);

    void inject(StickersPresenter stickersPresenter);

    void inject(ColorsPresenter colorsPresenter);

    void inject(DrawingPresenter drawingPresenter);

    void inject(FontsPresenter fontsPresenter);

    void inject(FontPickerDialog fontPickerDialog);

    void inject(EditorSticker editorSticker);

    void inject(EditorText editorText);
}
