package net.iquesoft.iquephoto;

/**
 * Created by Sergey on 8/30/2016.
 */
public class DataHolder {

    private PhotoEditorView photoEditorView;

    private static DataHolder ourInstance = new DataHolder();

    public static DataHolder getInstance() {
        return ourInstance;
    }

    private DataHolder() {
    }

    public PhotoEditorView getPhotoEditorView() {
        return photoEditorView;
    }

    public void setPhotoEditorView(PhotoEditorView photoEditorView) {
        this.photoEditorView = photoEditorView;
    }
}
