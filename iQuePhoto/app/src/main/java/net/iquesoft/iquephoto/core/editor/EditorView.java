package net.iquesoft.iquephoto.core.editor;

import android.graphics.Bitmap;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.RectF;

import com.arellomobile.mvp.MvpView;

import net.iquesoft.iquephoto.core.editor.enums.EditorTool;
import net.iquesoft.iquephoto.core.editor.model.Drawing;
import net.iquesoft.iquephoto.core.editor.model.EditorSticker;
import net.iquesoft.iquephoto.core.editor.model.EditorText;
import net.iquesoft.iquephoto.core.editor.model.EditorVignette;

import java.util.List;

interface EditorView extends MvpView {
    void setupImage(Bitmap bitmap, Matrix imageMatrix, RectF imageRect);

    void showOriginalImage(boolean display);

    void toolChanged(EditorTool tool);

    void imageAdjusted(Paint paint);

    void overlayChanged(Bitmap bitmap, Matrix matrix, Paint paint);

    void filterChanged(Paint paint);

    void frameChanged(Bitmap bitmap, Matrix matrix);

    void textAdded(List<EditorText> texts);

    void stickerAdded(List<EditorSticker> stickers);

    void updateVignette(EditorVignette vignette);

    void updateDrawing(Paint paint, Path path);

    void updateDrawing(List<Drawing> drawings);

    void imageChanged(Bitmap bitmap);

    void updateView();
}