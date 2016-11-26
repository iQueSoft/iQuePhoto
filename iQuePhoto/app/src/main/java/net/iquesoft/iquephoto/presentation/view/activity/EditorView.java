package net.iquesoft.iquephoto.presentation.view.activity;

import net.iquesoft.iquephoto.core.editor.enums.EditorCommand;
import net.iquesoft.iquephoto.core.editor.ImageEditorView;
import net.iquesoft.iquephoto.model.Text;

public interface EditorView {

    void showAlertDialog();

    void setupToolFragment(EditorCommand editorCommand);

    void showToastMessage(int stringResource);

    void navigateBack(boolean isFragment);

    void addTextToEditor(Text text);

    void applyCommand(EditorCommand editorCommand);

    void setEditorCommand(EditorCommand editorCommand);

    ImageEditorView getImageEditorView();
}
