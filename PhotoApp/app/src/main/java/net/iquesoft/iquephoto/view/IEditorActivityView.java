package net.iquesoft.iquephoto.view;

import net.iquesoft.iquephoto.adapters.ToolsAdapter;
import net.iquesoft.iquephoto.model.Tool;

public interface IEditorActivityView {

    /**
     * @param toolsAdapter is used for set Fragment into tool settings container.
     */
    void initTools(ToolsAdapter toolsAdapter);

    /**
     * @param tool is used for set Fragment into tool settings container.
     */
    void changeTool(Tool tool);

    void showAlertDialog();

    /**
     * @param stringResource - string resource from res/value/string.xml.
     */
    void showToastMessage(int stringResource);

    void goBack();
}
