package net.iquesoft.iquephoto.core;

import android.graphics.Bitmap;

class EditorImage {
    private int mCommand;
    private Bitmap mBitmap;

    EditorImage(int command, Bitmap bitmap) {
        mCommand = command;
        mBitmap = bitmap;
    }

    int getCommand() {
        return mCommand;
    }

    Bitmap getBitmap() {
        return mBitmap;
    }

}
