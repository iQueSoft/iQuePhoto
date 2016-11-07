package net.iquesoft.iquephoto.core;

import android.graphics.Bitmap;

class EditorImage {
    private EditorCommand mCommand;
    private Bitmap mBitmap;

    EditorImage(EditorCommand command, Bitmap bitmap) {
        mCommand = command;
        mBitmap = bitmap;
    }

    EditorCommand getCommand() {
        return mCommand;
    }

    Bitmap getBitmap() {
        return mBitmap;
    }

}
