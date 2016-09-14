package net.iquesoft.iquephoto.view;

import net.iquesoft.iquephoto.model.Text;

public interface ITextFragmentView {
    void onAddTextComplete(Text text);

    void onDeleteTextComplete(Text text);
}
