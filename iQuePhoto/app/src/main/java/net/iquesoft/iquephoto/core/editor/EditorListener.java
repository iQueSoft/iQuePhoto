package net.iquesoft.iquephoto.core.editor;

public interface EditorListener {
    void imageProcessingStarted();

    void hasChanges(int count);

    void imageProcessingFinished();
}