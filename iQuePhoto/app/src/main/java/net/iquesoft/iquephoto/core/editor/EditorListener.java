package net.iquesoft.iquephoto.core.editor;

import android.graphics.Paint;

public interface EditorListener {
    void imageProcessingStarted();

    void onTransparencyHandleButtonClicked(Paint paint);

    void hasChanges(int count);

    void imageProcessingFinished();
}