package net.iquesoft.iquephoto.core.editor.model;

import android.graphics.Bitmap;

import net.iquesoft.iquephoto.core.editor.enums.EditorCommand;

public class EditorImage {
    private EditorCommand mCommand;
    private Bitmap mBitmap;

    public EditorImage(EditorCommand command, Bitmap bitmap) {
        mCommand = command;
        mBitmap = bitmap;
    }

    public EditorCommand getCommand() {
        return mCommand;
    }

    public Bitmap getBitmap() {
        return mBitmap;
    }

}
