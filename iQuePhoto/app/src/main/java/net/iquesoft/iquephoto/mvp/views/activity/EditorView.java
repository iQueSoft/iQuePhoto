package net.iquesoft.iquephoto.mvp.views.activity;

import com.arellomobile.mvp.MvpView;

import net.iquesoft.iquephoto.core.editor.enums.EditorCommand;
import net.iquesoft.iquephoto.core.editor.ImageEditorView;
import net.iquesoft.iquephoto.mvp.models.Text;

public interface EditorView extends MvpView {

    void showAlertDialog();

    void setupToolFragment(EditorCommand editorCommand);

    void showToastMessage(int stringResource);

    void navigateBack(boolean isFragment);

    void addTextToEditor(Text text);

    void applyCommand(EditorCommand editorCommand);

    void setEditorCommand(EditorCommand editorCommand);

    ImageEditorView getImageEditorView();
}
