package net.iquesoft.iquephoto.common;

import android.support.v4.app.Fragment;

import net.iquesoft.iquephoto.di.IHasComponent;

public class BaseFragment extends Fragment {

    @SuppressWarnings("unchecked")
    protected <T> T getComponent(Class<T> componentType) {
        return componentType.cast(((IHasComponent<T>) getActivity()).getComponent());
    }
}
