package net.iquesoft.iquephoto.core.editor;

import android.graphics.Path;

import com.arellomobile.mvp.MvpView;

import net.iquesoft.iquephoto.core.editor.model.Drawing;

import java.util.List;

interface ImageEditorViewView extends MvpView {
    void brushDown(Path path);

    void brushMove(Path path);

    void brushUp(List<Drawing> drawings);
}