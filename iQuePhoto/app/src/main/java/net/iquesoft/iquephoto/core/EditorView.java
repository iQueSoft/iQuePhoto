package net.iquesoft.iquephoto.core;

import android.graphics.Bitmap;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.RectF;

import com.arellomobile.mvp.MvpView;

import net.iquesoft.iquephoto.core.enums.EditorTool;
import net.iquesoft.iquephoto.core.model.Drawing;
import net.iquesoft.iquephoto.core.model.EditorLinearTiltShift;
import net.iquesoft.iquephoto.core.model.EditorSticker;
import net.iquesoft.iquephoto.core.model.EditorText;
import net.iquesoft.iquephoto.core.model.EditorRadialTiltShift;
import net.iquesoft.iquephoto.core.model.EditorVignette;

import java.util.List;

interface EditorView extends MvpView {
    void setupImage(Bitmap bitmap, Matrix imageMatrix, RectF imageRect);

    void showOriginalImage(boolean display);

    void onToolChanged(EditorTool tool);

    void onImageAdjusted(Paint paint);

    void onOverlayChanged(Bitmap bitmap, Matrix matrix, Paint paint);

    void onFilterChanged(Paint paint);

    void onFrameChanged(Bitmap bitmap, Matrix matrix);

    void onTextAdded(List<EditorText> texts);

    void onStickerAdded(List<EditorSticker> stickers);

    void onVignetteUpdated(EditorVignette vignette);

    void onRadialTiltShiftUpdated(EditorRadialTiltShift radialTiltShift);

    void onLinearTiltShiftUpdated(EditorLinearTiltShift linearTiltShift);

    void onStraightenTransformChanged(Matrix transformMatrix);

    void updateDrawing(Paint paint, Path path);

    void updateDrawing(List<Drawing> drawings);

    void updateView();

    void onApplyChanges();

    void enableDrawingCache();
}