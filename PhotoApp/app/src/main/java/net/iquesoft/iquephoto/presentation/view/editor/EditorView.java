package net.iquesoft.iquephoto.presentation.view.editor;

import android.support.v4.app.Fragment;

import net.iquesoft.iquephoto.core.ImageEditorView;
import net.iquesoft.iquephoto.model.Text;

public interface EditorView {

    void showAlertDialog();

    void setupFragment(Fragment fragment);

    void showToastMessage(int stringResource);

    void navigateBack(boolean isFragment);

    void addTextToEditor(Text text);

    ImageEditorView getImageEditorView();
}
