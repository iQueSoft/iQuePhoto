package net.iquesoft.iquephoto.presentation.view.activity;

import net.iquesoft.iquephoto.core.EditorCommand;
import net.iquesoft.iquephoto.core.ImageEditorView;
import net.iquesoft.iquephoto.model.Text;

public interface EditorView {

    void showAlertDialog();

    void setupToolFragment(EditorCommand editorCommand);

    void showToastMessage(int stringResource);

    void navigateBack(boolean isFragment);

    void addTextToEditor(Text text);

    void setEditorCommand(EditorCommand editorCommand);

    ImageEditorView getImageEditorView();
}
