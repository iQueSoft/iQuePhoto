package net.iquesoft.iquephoto;

import net.iquesoft.iquephoto.core.EditorView;

/**
 * Created by Sergey on 8/30/2016.
 */
public class DataHolder {

    private EditorView editorView;

    private static DataHolder ourInstance = new DataHolder();

    public static DataHolder getInstance() {
        return ourInstance;
    }

    private DataHolder() {
    }

    public EditorView getEditorView() {
        return editorView;
    }

    public void setEditorView(EditorView editorView) {
        this.editorView = editorView;
    }
}
